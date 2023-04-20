package perhaps.potatosmpx.enchantment.enchantments;

import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import perhaps.potatosmpx.enchantment.EnchantmentRarity;

public class BlazingAuraEnchantment extends Enchantment {
    public BlazingAuraEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int level) { return EnchantmentRarity.EPIC.getMinCost(level); }
    @Override
    public int getMaxCost(int level) { return EnchantmentRarity.EPIC.getMaxCost(level); }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        LivingEntity target = event.getEntityLiving();
        ItemStack helmet = target.getItemBySlot(EquipmentSlot.HEAD);
        int level = EnchantmentHelper.getItemEnchantmentLevel(this, helmet);

        if (level > 0 && target.level.random.nextFloat() < (1f * level)) {
            double range = 4.0;
            target.level.getEntities(target, target.getBoundingBox().inflate(range, range, range), entity -> entity instanceof LivingEntity)
                    .forEach(entity -> {
                        entity.setSecondsOnFire(5 * level);
                    });
        }
    }
}
