package xyz.skullition.testmodfabric.registry;


import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.skullition.testmodfabric.TestModFabric;
import xyz.skullition.testmodfabric.blocks.UnsafeBlock;
import xyz.skullition.testmodfabric.items.RealPhone;

public class Setup {

    public static final RealPhone REAL_PHONE = new RealPhone(new FabricItemSettings().maxCount(16).group(TestModFabric.MY_ITEM_GROUP).fireproof());
    public static final UnsafeBlock UNSAFE_BLOCK = new UnsafeBlock(FabricBlockSettings.of(Material.GLASS).breakByHand(true).drops(new Identifier(TestModFabric.MODID, "unsafeblock")));

    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(TestModFabric.MODID, "realphone"), REAL_PHONE);
        Registry.register(Registry.BLOCK, new Identifier(TestModFabric.MODID, "unsafeblock"), UNSAFE_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(TestModFabric.MODID, "unsafeblock"), new BlockItem(UNSAFE_BLOCK, new FabricItemSettings().group(TestModFabric.MY_ITEM_GROUP)));
    }
}
