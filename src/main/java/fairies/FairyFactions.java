package fairies;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import fairies.entity.EntityFairy;
import fairies.event.FairyEventListener;
import fairies.proxy.CommonProxy;
import net.minecraft.entity.EnumCreatureType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = Version.MOD_ID, version = Version.VERSION)
public class FairyFactions {

	@SidedProxy(clientSide = Version.PROXY_CLIENT, serverSide = Version.PROXY_COMMON)
	public static CommonProxy	proxy;

	static final Logger			LOGGER	= LogManager.getFormatterLogger(Version.MOD_ID);
	// static final Boolean DEV = Boolean.parseBoolean(
	// System.getProperty("development", "false") );

	static File					BaseDir;
	static Configuration		Config;

	private Spawner				fairySpawner;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		BaseDir = new File(event.getModConfigurationDirectory(), Version.MOD_ID);
		Config = new Configuration(event.getSuggestedConfigurationFile());

		if (!BaseDir.exists())
			BaseDir.mkdir();

		proxy.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new FairyEventListener());
		FMLCommonHandler.instance().bus().register(this);
		LOGGER.debug("Registered events");

		proxy.initChannel();
		LOGGER.debug("Registered channel");

		proxy.initEntities();
		LOGGER.debug("Registered entities");

		proxy.initGUI();
		LOGGER.debug("Registered GUI");

		LOGGER.info("Loaded version %s", Version.VERSION);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		fairySpawner = new Spawner();
		final int maxNum = 18;
		final int freqNum = 8;
		fairySpawner.setMaxAnimals(maxNum);
		fairySpawner.AddCustomSpawn(EntityFairy.class, freqNum, EnumCreatureType.creature);
		// TODO: register egg
		// TODO: register entity localization
		LOGGER.debug("Spawner is a modified version of CustomSpawner, created by DrZhark.");

		proxy.postInit();
	}
}
