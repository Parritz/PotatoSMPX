package perhaps.potatosmpx.enchantment.enchantments;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.MinecraftForge;
import perhaps.potatosmpx.enchantment.EnchantmentRarity;

public class VenomousStrikeEnchantment extends Enchantment {
    public VenomousStrikeEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinCost(int level) { return EnchantmentRarity.EPIC.getMinCost(level); }
    @Override
    public int getMaxCost(int level) { return EnchantmentRarity.EPIC.getMaxCost(level); }

    @Override
    public void doPostAttack(LivingEntity attacker, Entity target, int level) {
        if (!attacker.level.isClientSide() && target instanceof LivingEntity livingTarget) {
            int duration = 40 * level; // Modify this value to change the poison duration (20 ticks = 1 second)
            int amplifier = level - 1; // Modify this value to change the poison damage
            MobEffectInstance poisonEffect = new MobEffectInstance(MobEffects.POISON, duration, amplifier);
            livingTarget.addEffect(poisonEffect);
        }

        super.doPostAttack(attacker, target, level);
    }
}
