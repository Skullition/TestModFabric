package xyz.skullition.testmodfabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import xyz.skullition.testmodfabric.registry.Setup;
import xyz.skullition.testmodfabric.renderer.ThingEntityModel;
import xyz.skullition.testmodfabric.renderer.ThingEntityRenderer;

public class TestModFabricClient implements ClientModInitializer {
    public static final EntityModelLayer MODEL_THING_LAYER = new EntityModelLayer(new Identifier(TestModFabric.MODID, "thing_entity"), "main");
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(Setup.PURPLE_GLASS_BLOCK, RenderLayer.getCutout());

        ScreenRegistry.register(Setup.BOX_SCREEN_HANDLER, BoxScreen::new);
        // thing entity
        EntityRendererRegistry.register(Setup.THING_ENTITY, ctx -> new ThingEntityRenderer(ctx));
        EntityModelLayerRegistry.registerModelLayer(MODEL_THING_LAYER, ThingEntityModel::getTexturedModelData);
    }
}
