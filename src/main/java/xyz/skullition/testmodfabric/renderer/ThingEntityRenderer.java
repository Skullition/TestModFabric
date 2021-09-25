package xyz.skullition.testmodfabric.renderer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import xyz.skullition.testmodfabric.TestModFabric;
import xyz.skullition.testmodfabric.TestModFabricClient;
import xyz.skullition.testmodfabric.entity.ThingEntity;

public class ThingEntityRenderer extends MobEntityRenderer<ThingEntity, ThingEntityModel> {
    public ThingEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ThingEntityModel(context.getPart(TestModFabricClient.MODEL_THING_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(ThingEntity entity) {
        return new Identifier(TestModFabric.MODID, "textures/entity/thing/thing.png");
    }
}
