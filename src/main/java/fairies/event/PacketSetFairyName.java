package fairies.event;

import java.io.IOException;

import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import fairies.FairyFactions;
import fairies.entity.EntityFairy;
import fairies.event.FairyEventListener.IFairyPacket;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;

public class PacketSetFairyName implements IFairyPacket {
	
	public static final int MIN_NAME_LENGTH = 3;
	public static final int MAX_NAME_LENGTH = 16;

	private int fairyID;
	private String name;
	
	public PacketSetFairyName(final EntityFairy fairy, final String name) {
		this.fairyID = fairy.getEntityId();
		this.name = name;
	}
	
	@Override
	public void init(PacketBuffer buf) {
		fairyID = buf.readInt();
		try {
			name = buf.readStringFromBuffer(MAX_NAME_LENGTH).trim();
			if( name.length() < MIN_NAME_LENGTH ) {
				name = "";
			}
		} catch (IOException e) {
			name = "";
		}
	}

	@Override
	public void handle(NetworkManager origin) {
		final EntityPlayerMP player = ((NetHandlerPlayServer)origin.getNetHandler()).playerEntity;
		if( player != null ) {
			final EntityFairy fairy = FairyFactions.getFairy(this.fairyID);
			if( fairy == null ) {
				// TODO: ERROR, unable to find fairy in question
				return;
			}
			
			final String username = player.getGameProfile().getName();
			if( fairy.nameEnabled() && fairy.rulerName().equals(username) ) {
				fairy.setCustomName(this.name);
			} else {
				// WARN: invalid access
			}
			fairy.setNameEnabled(false);
		}
	}

}
