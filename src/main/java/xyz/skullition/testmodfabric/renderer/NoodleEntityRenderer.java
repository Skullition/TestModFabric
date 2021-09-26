package xyz.skullition.testmodfabric.renderer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import xyz.skullition.testmodfabric.TestModFabric;
import xyz.skullition.testmodfabric.TestModFabricClient;
import xyz.skullition.testmodfabric.entity.NoodleEntity;

public class NoodleEntityRenderer extends MobEntityRenderer<NoodleEntity, NoodleEntityModel> {
    public NoodleEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new NoodleEntityModel(context.getPart(TestModFabricClient.MODEL_NOODLE_LAYER)), 0.5f);
        this.shadowRadius = 0.1f;
    }

    @Override
    public Identifier getTexture(NoodleEntity entity) {
        return new Identifier(TestModFabric.MODID, "textures/entity/noodle/noodle.png");
    }
}
