package perhaps.potatosmpx.enchantment.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import perhaps.potatosmpx.enchantment.EnchantmentRarity;

import java.util.UUID;

public class TankEnchantment extends Enchantment {
    public TankEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
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
        if (event.getSource().isBypassArmor() || event.getSource().isBypassMagic() || event.getSource().isBypassInvul()) {
            LivingEntity target = event.getEntityLiving();
            int tankLevel = EnchantmentHelper.getItemEnchantmentLevel(this, target.getItemBySlot(EquipmentSlot.CHEST));

            if (tankLevel > 0) {
                float protectionFactor = 1.0F - (tankLevel * 0.1F);
                event.setAmount(event.getAmount() * protectionFactor);
            }
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof Player player) {
            int tankLevel = EnchantmentHelper.getItemEnchantmentLevel(this, player.getItemBySlot(EquipmentSlot.CHEST));

            if (tankLevel > 0) {
                int extraHearts = tankLevel * 4;
                if (player.getAttribute(Attributes.MAX_HEALTH).getModifier(TANK_HEALTH_MODIFIER) == null) {
                    player.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(
                            new AttributeModifier(TANK_HEALTH_MODIFIER, "Tank extra health", extraHearts, AttributeModifier.Operation.ADDITION));
                }
            } else if (player.getAttribute(Attributes.MAX_HEALTH).getModifier(TANK_HEALTH_MODIFIER) != null) {
                player.getAttribute(Attributes.MAX_HEALTH).removeModifier(TANK_HEALTH_MODIFIER);
                player.setHealth(Math.min(player.getHealth(), player.getMaxHealth()));
            }
        }
    }

    private static final UUID TANK_HEALTH_MODIFIER = UUID.fromString("d5a952f5-8a78-48c1-82b5-5b2a8e33d96d");
}
