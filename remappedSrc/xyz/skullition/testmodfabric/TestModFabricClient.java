package xyz.skullition.testmodfabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;
import xyz.skullition.testmodfabric.registry.Setup;

public class TestModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(Setup.PURPLE_GLASS_BLOCK, RenderLayer.getCutout());

        ScreenRegistry.register(Setup.BOX_SCREEN_HANDLER, BoxScreen::new);
    }
}
