package perhaps.potatosmpx.enchantment.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.MinecraftForge;
import perhaps.potatosmpx.enchantment.EnchantmentRarity;
import perhaps.potatosmpx.enchantment.ItemUtils;

public class ComposterEnchantment extends Enchantment {
    public ComposterEnchantment(Enchantment.Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinCost(int level) { return EnchantmentRarity.EPIC.getMinCost(level); }
    @Override
    public int getMaxCost(int level) { return EnchantmentRarity.EPIC.getMaxCost(level); }
}
