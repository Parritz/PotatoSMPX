package perhaps.potatosmpx.enchantment.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.MinecraftForge;
import perhaps.potatosmpx.enchantment.EnchantmentRarity;

public class GreenThumbEnchantment extends Enchantment {
    public GreenThumbEnchantment(Enchantment.Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public int getMinCost(int level) { return EnchantmentRarity.GODLY.getMinCost(level); }
    @Override
    public int getMaxCost(int level) { return EnchantmentRarity.GODLY.getMaxCost(level); }
}
