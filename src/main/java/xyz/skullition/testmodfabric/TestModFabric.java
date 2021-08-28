package xyz.skullition.testmodfabric;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.skullition.testmodfabric.registry.Setup;

public class TestModFabric implements ModInitializer {

	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "testmodfabric";

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Setup.registerItems();

		System.out.println("Hello Fabric world!");


	}
}
