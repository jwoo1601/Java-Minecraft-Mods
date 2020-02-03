package noah.teleport.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import noah.teleport.Main;
import noah.teleport.place.Place;
import noah.teleport.util.Common;

public class ShowPlaceListCommand implements ICommand {
	
	private final String NAME = "Show Registered Places Command";
	private final String USAGE = "showplace";
	private ArrayList<String> aliase;
	
	public ShowPlaceListCommand() {
		this.aliase = new ArrayList<String>();
		this.aliase.add(NAME);
		this.aliase.add(USAGE);
	}
	
	@Override
	public int compareTo(ICommand o) {
		return o.getCommandName() == this.NAME ? 0 : -1;
	}

	@Override
	public String getCommandName() {
		return this.NAME;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/" + this.USAGE;
	}

	@Override
	public List<String> getCommandAliases() {
		return this.aliase;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		sender.addChatMessage(new ChatComponentText("<" + EnumChatFormatting.YELLOW + "Registered Place List" + EnumChatFormatting.WHITE + ">"));
		
		int id = sender.getEntityWorld().provider.getDimensionId();
		int n;
		
		switch (id) {
		case Common.OVERWORLD_ID:
			n = 0;
			break;
		case Common.NETHER_ID:
			n = 1;
			break;
		case Common.THEEND_ID:
			n = 2;
			break;
			default:
				n = id;
				break;
		}
		
		if (Main.Proxy.PlaceReg[n].isEmpty()) {
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "The Place Registry is Empty!"));
			return;
		}
		
		ArrayList<Place> arr = new ArrayList<Place> ();
		arr.addAll(Main.Proxy.PlaceReg[n].getAllPlaces());
		Iterator<Place> iter = arr.iterator();
		while(iter.hasNext()) {
			System.out.println("ID= " + iter.next().getID() + " Name= " + iter.next().getName() + " Pos= " + iter.next().getBlockPos().toString());
			sender.addChatMessage(new ChatComponentText("ID= " + iter.next().getID() + " Name= " + iter.next().getName() + " Pos= " + iter.next().getBlockPos().toString()));
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}

}
