package noah.minecraft.runaway.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.registry.GameRegistry;
import noah.minecraft.runaway.Noah;
import noah.minecraft.runaway.packet.SpongePacket;

public class SpongeCommand implements ICommand {

	private StringBuilder buffer = new StringBuilder();
	
	private static final String Name = "������ ��ɾ�";
	private static final String Command = "sponge"; //"������";
	private static final String[] SubCommand = new String[] { "start", "stop", "criminal" };// new String[] { "����", "����", "����" };
	private static ArrayList<String> Aliase = new ArrayList<String>();
	
	static {
		Aliase.add(Name);
		Aliase.add(Command);
	}
	
	@Override
	public int compareTo(ICommand o) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return Name;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/" + Command;
	}

	@Override
	public List<String> getCommandAliases() {
		return Aliase;
	}
	
	public static void giveBlockToPlayer(EntityPlayerMP target, int index, int amount, Block type, String displayName) {
		if (target == null || amount == 0 || type == null||index < 0)
			throw new NullPointerException("target||amount||type");
		
		target.inventoryContainer.inventorySlots.get(index).putStack(new ItemStack(type, amount).setStackDisplayName(displayName));
	}
	
	private void sendChatToAll(String msg) {
		buffer.append(msg + "\n");
	}
	
	private void flush() {
		MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(buffer.toString()));
		buffer.delete(0, buffer.length());
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (args.length == 1) {
			String sub = args[0];
			
			if (SubCommand[0].equals(sub)) {
				if (!Noah.Proxy.isGameStarted) {
				sendChatToAll("[" + EnumChatFormatting.YELLOW + "������" +
			                  EnumChatFormatting.WHITE + "�� ����" + 
			                  EnumChatFormatting.AQUA + "Ƣ���!" +
			                  EnumChatFormatting.WHITE + "]");
				
				Noah.Proxy.isGameStarted = true;
				sendChatToAll("����: " + EnumChatFormatting.AQUA + "���۵�");
				
				List<EntityPlayerMP> players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
				sendChatToAll("�÷��̾� ���:");
				
				int crim = 0;
				if (players.size() > 1) {
					Random a = new Random();
					crim = a.nextInt(players.size() - 1);
				}
				
				for (int i=0; i < players.size(); i++) {
					EntityPlayerMP p = players.get(i);
					p.inventory.clear();
					
					sendChatToAll(EnumChatFormatting.RED + p.getName() + EnumChatFormatting.WHITE + " (" + EnumChatFormatting.YELLOW + "UUID"
							      + EnumChatFormatting.WHITE + "=" + p.getUniqueID().toString() + ")");
					
					if (i == crim) {
						giveBlockToPlayer(p, 44, 1, Blocks.sponge, EnumChatFormatting.GOLD + "��¥ ������");
						Noah.Proxy.Criminal = p;
					}
					else
						giveBlockToPlayer(p, 44, 1, Blocks.sponge, "��¥ ������");
					

				}
				Noah.Proxy.HINSTANCE.sendToAll(new SpongePacket(SpongePacket.Type.START, Noah.Proxy.Criminal.getUniqueID()));
				
				sendChatToAll(EnumChatFormatting.RED + "����" + EnumChatFormatting.WHITE + "�� " + EnumChatFormatting.AQUA + "��÷" + EnumChatFormatting.WHITE + "�Ǿ����ϴ�!");			
				sendChatToAll(EnumChatFormatting.YELLOW + "������" + EnumChatFormatting.WHITE + " ���� " + EnumChatFormatting.AQUA + "�Ϸ�" + EnumChatFormatting.WHITE + "!");	
				flush();
				
				MinecraftServer.getServer().worldServerForDimension(0).getGameRules().setOrCreateGameRule("keepInventory", "true");
				}
				else
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "������ �̹� �������Դϴ�!"));
			}
			else if (SubCommand[1].equals(sub)) {
				
				if (!Noah.Proxy.isGameStarted)
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "���� �������� �ƴմϴ�!"));
				else {
					sendChatToAll("[" + EnumChatFormatting.YELLOW + "������" + EnumChatFormatting.WHITE + "�� ����" + EnumChatFormatting.AQUA + "Ƣ���!" + EnumChatFormatting.WHITE + "]");
					Noah.Proxy.isGameStarted = false;
					sendChatToAll("����: " + EnumChatFormatting.RED + "�����");
					sendChatToAll("[" + EnumChatFormatting.RED + "����" + EnumChatFormatting.WHITE + "]: " + Noah.Proxy.Criminal.getName());
					Noah.Proxy.Criminal = null;
					Noah.Proxy.HINSTANCE.sendToAll(new SpongePacket(SpongePacket.Type.END, UUID.randomUUID()));
					flush();
					
					MinecraftServer.getServer().worldServerForDimension(0).getGameRules().setOrCreateGameRule("keepInventory", "false");
					}
			}
			
			else if (SubCommand[2].equals(sub)) {
				if (Noah.Proxy.isGameStarted)
					sender.addChatMessage(new ChatComponentText("[" + EnumChatFormatting.RED + "����" + EnumChatFormatting.WHITE + "]: " + Noah.Proxy.Criminal.getName()));
				else
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "���� �������� �ƴմϴ�!"));
			}
			
			else
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "�ùٸ� ��ɾ �ƴմϴ�."));
		}
		
		else
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "�ùٸ� ��ɾ �ƴմϴ�."));
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		boolean isOp = false;
		String s = sender.getName();
		String[] ops = MinecraftServer.getServer().getConfigurationManager().getOppedPlayerNames();
		
		for (String name : ops) {
			if (name.equals(s)) {
				isOp = true;
				break;
			}
		}
		
		return isOp;
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
