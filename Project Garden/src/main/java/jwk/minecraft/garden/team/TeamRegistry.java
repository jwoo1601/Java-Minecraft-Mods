package jwk.minecraft.garden.team;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import jwk.minecraft.garden.ModInfo;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.currency.CurrencyYD;
import jwk.minecraft.garden.exception.EntryAlreadyExistException;
import jwk.minecraft.garden.util.IManaged;
import net.minecraft.entity.player.EntityPlayer;

public class TeamRegistry {

	public static final String TEAM_METADATA_FILE_NAME = ".metadata";
	public static final String META_VERSION = "1.0.0";
	
	private static final Set<ITeam> OBJECTS = Sets.newConcurrentHashSet();
	
	public static int size() { return OBJECTS.size(); }
	
	public static boolean isEmpty() { return OBJECTS.isEmpty(); }
	
	public static Iterator<ITeam> iterator() { return OBJECTS.iterator(); }
	
	public static boolean register(@Nonnull ITeam team) {
		boolean added = OBJECTS.add(checkNotNull(team));
		
		if (!added)
			return false;
		
		CurrencyYD.INSTANCE.getManager().register(team);
		return true;
	}
	
	public static boolean unregister(@Nonnull ITeam team) {
		boolean removed = OBJECTS.remove(checkNotNull(team));
		
		if (!removed)
			return false;
		
		CurrencyYD.INSTANCE.getManager().onDataChanged(team, 0L);
		CurrencyYD.INSTANCE.getManager().unregister(team);
		return true;
	}
	
	public static boolean unregister(@Nonnull String teamName) {
		checkNotNull(teamName);
		
		for (ITeam team : OBJECTS) {
			
			if (team.getTeamName().equals(teamName))
				return unregister(team);
		}
		
		return false;
	}
	
	public static ITeam get(@Nonnull String teamName) {
		checkNotNull(teamName);
		ITeam target = null;
		
		for (ITeam team : OBJECTS) {
			
			if (team.getTeamName().equals(teamName)) {
				target = team;
				break;
			}
		}
		
		return target;
	}
	
	public static boolean contains(@Nonnull ITeam team) {
		return OBJECTS.contains(team);
	}
	
	public static boolean contains(@Nonnull String teamName) {
		return get(teamName) != null;
	}
	
	public static void removeAll() {
		
		for (ITeam team : OBJECTS) {
			CurrencyYD.INSTANCE.getManager().onDataChanged(team, 0L);
			CurrencyYD.INSTANCE.getManager().unregister(team);
		}
		
		OBJECTS.clear();
	}
	
	public static ITeam getTeamOf(@Nonnull EntityPlayer player) {
		checkNotNull(player);
		
		if (OBJECTS.isEmpty())
			return null;
		
		ITeam target = null;
		for (ITeam team : OBJECTS) {
			
			if (team.getManager().isDataExist(player)) {
				target = team;
				break;
			}
		}
		
		return target;
	}
	
	public static boolean isLeader(@Nonnull EntityPlayer player) {
		ITeam team = getTeamOf(player);
		
		if (team == null)
			return false;
		
		return team.getManager().isLeader(player);
	}
	
	public static void onSave() {
		if (OBJECTS.isEmpty())
			return;
		
		saveMetadata();
		
		for (ITeam team : OBJECTS)
			team.getManager().onSave();
		
		ProjectGarden.logger.info("team data has been successfully saved");
	}
	
	public static void onLoad() {
		List<String> teamList = loadMetadata();
		
		if (teamList == null)
			return;
		
		ProjectGarden.logger.info(teamList.size() + " team data detected");
		
		for (String name : teamList) {
			ITeam obj = new SimpleTeam(name);
			
			obj.getManager().onLoad();
			
			if (!register(obj))
				throw new EntryAlreadyExistException(obj.getTeamName());
		}
		
		ProjectGarden.logger.info(teamList.size() + " team data has been successfully loaded");
	}
	
	public static void onUnload() {
		
		for (ITeam team : OBJECTS)
			team.getManager().onUnload();
		
		OBJECTS.clear();
	}
	
	private static final void saveMetadata() {
		JSONObject rootObj = new JSONObject();
		rootObj.put("version", META_VERSION);
		
		JSONArray teamArray = new JSONArray();
		for (ITeam team : OBJECTS)
			teamArray.add(team.getTeamName());
		
		rootObj.put("teams", teamArray);
		
		String path = TeamManager.TEAM_DEFAULT_PATH + "\\" + TEAM_METADATA_FILE_NAME;
		
		File file = new File(path);
		
		try {
			File file2 = null;
			if (file.exists())
				file2 = new File(path + "_tmp");
			
			else {
				file2 = file;
				file2.getParentFile().mkdirs();
			}
			
			file2.createNewFile();
			file2.setWritable(true);
			
			ProjectGarden.logger.info("start saving team data");
			
			FileWriter writer = new FileWriter(file2);
			writer.write(rootObj.toJSONString());
			writer.flush();
			writer.close();
			
			if (file != file2) {
				file.delete();
			
				if (file.exists())
					throw new IOException("failed to delete previous metadata");
				
				file2.renameTo(file);
			}			
		}
		
		catch (IOException e) {
			ProjectGarden.logger.error("An error occured during saving file: " + path);
			e.printStackTrace();
		}
	}
	
	private static final List<String> loadMetadata() {
		JSONParser parser = new JSONParser();
		
		String path = TeamManager.TEAM_DEFAULT_PATH + "\\" + TEAM_METADATA_FILE_NAME;
		try {
			Object obj = parser.parse(new FileReader(path));
			JSONObject rootObj = (JSONObject) obj;
			
			ProjectGarden.logger.info("previous metadata detected");
			
			String version = ((String) rootObj.get("version"));
			
			ProjectGarden.logger.info("metadata version= " + version);
			
			int fileVersion = Integer.valueOf(version.replace(".", ""));
			int currentVersion = Integer.valueOf(META_VERSION.replace(".", ""));
			
			if (fileVersion < currentVersion || fileVersion > currentVersion) {
				throw new RuntimeException("the version of " + path + " is not compatible for ProjectGarden:" + ModInfo.VERSION);
			}
			
			JSONArray teamArray = (JSONArray) rootObj.get("teams");
			List<String> list = Lists.newArrayList(teamArray);
			
			return list;
		}
		
		catch (FileNotFoundException e) {
			ProjectGarden.logger.info("no previous .metadata detected");
			ProjectGarden.logger.info("skip loading team data");
			return null;
		}
		
		catch (Exception e) {
			throw new RuntimeException("An error occured during loading file: " + path, e);
		}
	}
	
}
