package perhaps.potatosmpx.enchantment.enchantments;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import perhaps.potatosmpx.enchantment.EnchantmentRarity;

import java.util.Random;

public class SwiftStrikeEnchantment extends Enchantment {
    public SwiftStrikeEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int level) { return EnchantmentRarity.LEGENDARY.getMinCost(level); }
    @Override
    public int getMaxCost(int level) { return EnchantmentRarity.LEGENDARY.getMaxCost(level); }

    @SubscribeEvent
    public void onAttackEntity(LivingAttackEvent event) {
        DamageSource source = event.getSource();
        Entity attackerEntity = source.getDirectEntity();
        LivingEntity target = event.getEntityLiving();

        if (attackerEntity instanceof Player player) {
            ItemStack heldItem = player.getMainHandItem();

            if (heldItem.isEmpty()) {
                return;
            }

            int swiftStrikeLevel = EnchantmentHelper.getItemEnchantmentLevel(this, heldItem);

            if (swiftStrikeLevel > 0) {
                Random random = new Random();
                float chance = 0.05f * swiftStrikeLevel;

                if (random.nextFloat() <= chance) {
                    double weaponDamage = heldItem.getItem().getDamage(heldItem);
                    target.hurt(DamageSource.playerAttack(player), (float) weaponDamage);
                }
            }
        }
    }
}
