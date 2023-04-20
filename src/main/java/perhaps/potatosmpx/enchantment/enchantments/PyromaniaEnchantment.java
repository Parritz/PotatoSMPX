package perhaps.potatosmpx.enchantment.enchantments;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import perhaps.potatosmpx.enchantment.EnchantmentRarity;
import perhaps.potatosmpx.enchantment.EnchantmentUtils;
import perhaps.potatosmpx.enchantment.ModEnchantments;

public class PyromaniaEnchantment extends Enchantment {
    public PyromaniaEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int level) { return EnchantmentRarity.RARE.getMinCost(level); }
    @Override
    public int getMaxCost(int level) { return EnchantmentRarity.RARE.getMaxCost(level); }

    @Override
    protected boolean checkCompatibility(Enchantment other) {
        return super.checkCompatibility(other) && EnchantmentUtils.isEnchantmentAllowed(other, Enchantments.FIRE_ASPECT);
    }

    @Override
    public void doPostAttack(LivingEntity attacker, Entity target, int level) {
        if (!attacker.level.isClientSide() && target instanceof LivingEntity livingTarget && target.isOnFire()) {
            float damage = 2.0f * level;
            DamageSource damageSource = DamageSource.mobAttack(attacker);
            livingTarget.hurt(damageSource, damage);
        }

        super.doPostAttack(attacker, target, level);
    }
}
