package xyz.skullition.testmodfabric.registry;


import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.skullition.testmodfabric.TestModFabric;
import xyz.skullition.testmodfabric.blocks.BoxBlock;
import xyz.skullition.testmodfabric.blocks.MugBlock;
import xyz.skullition.testmodfabric.blocks.PurpleGlassBlock;
import xyz.skullition.testmodfabric.blocks.UnsafeBlock;
import xyz.skullition.testmodfabric.blocks.blockentities.BoxBlockEntity;
import xyz.skullition.testmodfabric.blocks.blockentities.BoxScreenHandler;
import xyz.skullition.testmodfabric.blocks.blockentities.UnsafeBlockEntity;
import xyz.skullition.testmodfabric.entity.NoodleEntity;
import xyz.skullition.testmodfabric.entity.ThingEntity;
import xyz.skullition.testmodfabric.items.ExtendedPickaxe;
import xyz.skullition.testmodfabric.items.LightningStaff;
import xyz.skullition.testmodfabric.items.RealPhone;

public class Setup {

    public static final RealPhone REAL_PHONE = new RealPhone(new FabricItemSettings().maxCount(16).group(TestModFabric.MY_ITEM_GROUP).fireproof());
    public static final ExtendedPickaxe EXTENDED_PICKAXE = new ExtendedPickaxe(new FabricItemSettings().maxCount(1).group(TestModFabric.MY_ITEM_GROUP));
    public static final LightningStaff LIGHTNING_STAFF = new LightningStaff(new FabricItemSettings().maxCount(1).group(TestModFabric.MY_ITEM_GROUP));
    public static final UnsafeBlock UNSAFE_BLOCK = new UnsafeBlock(FabricBlockSettings.of(Material.GLASS).breakByHand(true).drops(new Identifier(TestModFabric.MODID, "unsafeblock")));
    public static final BoxBlock BOX_BLOCK = new BoxBlock(FabricBlockSettings.of(Material.WOOD).breakByHand(true).drops(new Identifier(TestModFabric.MODID, "boxblock")));
    public static final MugBlock MUG_BLOCK = new MugBlock(FabricBlockSettings.of(Material.GLASS).nonOpaque());
    public static final PurpleGlassBlock PURPLE_GLASS_BLOCK = new PurpleGlassBlock(FabricBlockSettings.of(Material.GLASS).nonOpaque().breakByHand(true).drops(new Identifier(TestModFabric.MODID, "purpleglassblock")));
    public static final ScreenHandlerType<BoxScreenHandler> BOX_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(new Identifier(TestModFabric.MODID, "boxblock"), BoxScreenHandler::new);
    public static final EntityType<ThingEntity> THING_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ThingEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build();
    public static final EntityType<NoodleEntity> NOODLE_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, NoodleEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build();
    public static final BlockEntityType<UnsafeBlockEntity> UNSAFE_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(UnsafeBlockEntity::new, UNSAFE_BLOCK).build();
    public static final BlockEntityType<BoxBlockEntity> BOX_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(BoxBlockEntity::new, BOX_BLOCK).build();

    public static void registerAll() {
        // items
        Registry.register(Registry.ITEM, new Identifier(TestModFabric.MODID, "realphone"), REAL_PHONE);
        Registry.register(Registry.ITEM, new Identifier(TestModFabric.MODID, "extendedpickaxe"), EXTENDED_PICKAXE);
        Registry.register(Registry.ITEM, new Identifier(TestModFabric.MODID, "lightningstaff"), LIGHTNING_STAFF);
        // blocks
        Registry.register(Registry.BLOCK, new Identifier(TestModFabric.MODID, "unsafeblock"), UNSAFE_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(TestModFabric.MODID, "purpleglassblock"), PURPLE_GLASS_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(TestModFabric.MODID, "boxblock"), BOX_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(TestModFabric.MODID, "mugblock"), MUG_BLOCK);
        // block items
        Registry.register(Registry.ITEM, new Identifier(TestModFabric.MODID, "unsafeblock"), new BlockItem(UNSAFE_BLOCK, new FabricItemSettings().group(TestModFabric.MY_ITEM_GROUP)));
        Registry.register(Registry.ITEM, new Identifier(TestModFabric.MODID, "purpleglassblock"), new BlockItem(PURPLE_GLASS_BLOCK, new FabricItemSettings().group(TestModFabric.MY_ITEM_GROUP)));
        Registry.register(Registry.ITEM, new Identifier(TestModFabric.MODID, "boxblock"), new BlockItem(BOX_BLOCK, new FabricItemSettings().group(TestModFabric.MY_ITEM_GROUP)));
        Registry.register(Registry.ITEM, new Identifier(TestModFabric.MODID, "mugblock"), new BlockItem(MUG_BLOCK, new FabricItemSettings().group(TestModFabric.MY_ITEM_GROUP)));
        // block entities
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(TestModFabric.MODID, "unsafeblockentity"), UNSAFE_BLOCK_ENTITY);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(TestModFabric.MODID, "boxblockentity"), BOX_BLOCK_ENTITY);
        // entities
        Registry.register(Registry.ENTITY_TYPE, new Identifier(TestModFabric.MODID, "thing_entity"), THING_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(TestModFabric.MODID, "noodle_entity"), NOODLE_ENTITY);
        // entity attribute
        FabricDefaultAttributeRegistry.register(THING_ENTITY, ThingEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(NOODLE_ENTITY, NoodleEntity.createMobAttributes());
    }
}
