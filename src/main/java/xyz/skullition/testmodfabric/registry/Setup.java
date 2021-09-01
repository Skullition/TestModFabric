package xyz.skullition.testmodfabric.registry;


import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.skullition.testmodfabric.TestModFabric;
import xyz.skullition.testmodfabric.blocks.UnsafeBlock;
import xyz.skullition.testmodfabric.blocks.blockentities.UnsafeBlockEntity;
import xyz.skullition.testmodfabric.items.RealPhone;

public class Setup {

    public static final RealPhone REAL_PHONE = new RealPhone(new FabricItemSettings().maxCount(16).group(TestModFabric.MY_ITEM_GROUP).fireproof());
    public static final UnsafeBlock UNSAFE_BLOCK = new UnsafeBlock(FabricBlockSettings.of(Material.GLASS).breakByHand(true).drops(new Identifier(TestModFabric.MODID, "unsafeblock")));
    public static BlockEntityType<UnsafeBlockEntity> UNSAFE_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(UnsafeBlockEntity::new, UNSAFE_BLOCK).build();

    public static void registerAll() {
        // items
        Registry.register(Registry.ITEM, new Identifier(TestModFabric.MODID, "realphone"), REAL_PHONE);
        // blocks
        Registry.register(Registry.BLOCK, new Identifier(TestModFabric.MODID, "unsafeblock"), UNSAFE_BLOCK);
        // block items
        Registry.register(Registry.ITEM, new Identifier(TestModFabric.MODID, "unsafeblock"), new BlockItem(UNSAFE_BLOCK, new FabricItemSettings().group(TestModFabric.MY_ITEM_GROUP)));
        // block entities
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(TestModFabric.MODID, "unsafeblockentity"), UNSAFE_BLOCK_ENTITY);
    }
}
