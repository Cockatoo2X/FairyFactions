package fairies.proxy;

import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import fairies.FairyFactions;
import fairies.Version;
import fairies.entity.EntityFairy;
import fairies.entity.FairyEntityFishHook;
import fairies.event.FairyEventListener;
import net.minecraft.entity.player.EntityPlayerMP;

public class CommonProxy {
	
	protected FairyEventListener eventListener;
	protected FMLEventChannel eventChannel;

	public void preInit() {
		this.eventChannel =  NetworkRegistry.INSTANCE.newEventDrivenChannel( Version.CHANNEL );
	}

	public void initChannel(FairyEventListener listener) {
		this.eventListener = listener;
		this.eventChannel.register(this.eventListener);
	}

	public void initEntities() {
		EntityRegistry.registerGlobalEntityID( EntityFairy.class, "Fairy", EntityRegistry.findGlobalUniqueEntityId() );	
		EntityRegistry.registerModEntity( FairyEntityFishHook.class, "FairyFishhook", EntityRegistry.findGlobalUniqueEntityId(), FairyFactions.INSTANCE, 64, 4, true );
	}

	public void initGUI() {
		// TODO Auto-generated method stub
	}

	public void postInit() {
		// TODO Auto-generated method stub
	}
	
	////////// packet handling
	
	public void sendToClient(FMLProxyPacket packet, EntityPlayerMP player) {
		eventChannel.sendTo( packet, player );
	}
	public void sendToServer(FMLProxyPacket packet) {
		eventChannel.sendToServer( packet );
	}

	public void sendFairyRename(String s1) {
		// TODO Auto-generated method stub
	}

}
