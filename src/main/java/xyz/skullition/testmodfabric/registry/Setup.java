package xyz.skullition.testmodfabric.registry;


import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.skullition.testmodfabric.TestModFabric;
import xyz.skullition.testmodfabric.items.RealPhone;

public class Setup {

    public static final RealPhone REAL_PHONE = new RealPhone(new FabricItemSettings().maxCount(16).group(ItemGroup.COMBAT).fireproof());

    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(TestModFabric.MODID, "realphone"), REAL_PHONE);
    }
}
