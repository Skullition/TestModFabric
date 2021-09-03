package xyz.skullition.testmodfabric.items;

import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;

public class ExtendedPickaxe extends PickaxeItem {
    public ExtendedPickaxe(Settings settings) {
        super(ToolMaterials.DIAMOND, 2, -2.8F, settings);
    }


    protected ExtendedPickaxe(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
}
