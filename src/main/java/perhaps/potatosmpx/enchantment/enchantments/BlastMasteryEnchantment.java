package perhaps.potatosmpx.enchantment.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;
import perhaps.potatosmpx.enchantment.EnchantmentRarity;
import perhaps.potatosmpx.enchantment.EnchantmentUtils;
import perhaps.potatosmpx.enchantment.ModEnchantments;

import java.util.*;

public class BlastMasteryEnchantment extends Enchantment {
    public BlastMasteryEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinCost(int level) { return EnchantmentRarity.LEGENDARY.getMinCost(level); }
    @Override
    public int getMaxCost(int level) { return EnchantmentRarity.LEGENDARY.getMaxCost(level); }

    private final Map<Item, ItemStack> recipeCache = new HashMap<>();

    @Override
    protected boolean checkCompatibility(@NotNull Enchantment other) {
        return super.checkCompatibility(other) && EnchantmentUtils.isEnchantmentAllowed(other, Enchantments.SILK_TOUCH, ModEnchantments.AUTO_SMELT.get(), ModEnchantments.SMOKE_MASTERY.get());
    }
}
