package perhaps.potatosmpx.enchantment.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import perhaps.potatosmpx.enchantment.EnchantmentRarity;

import java.util.UUID;

public class QuickStrikeEnchantment extends Enchantment {
    public QuickStrikeEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int level) { return EnchantmentRarity.UNCOMMON.getMinCost(level); }
    @Override
    public int getMaxCost(int level) { return EnchantmentRarity.UNCOMMON.getMaxCost(level); }

    private static final UUID QUICK_STRIKE_ATTACK_SPEED_MODIFIER = UUID.fromString("3f58b2d8-239d-4e19-8584-4f33f4b4d4b4");

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        ItemStack mainHandItem = player.getItemBySlot(EquipmentSlot.MAINHAND);
        int quickStrikeLevel = EnchantmentHelper.getItemEnchantmentLevel(this, mainHandItem);

        if (quickStrikeLevel > 0) {
            double attackSpeedBonus = 0.1f * quickStrikeLevel;

            if (player.getAttribute(Attributes.ATTACK_SPEED).getModifier(QUICK_STRIKE_ATTACK_SPEED_MODIFIER) == null) {
                player.getAttribute(Attributes.ATTACK_SPEED).addPermanentModifier(
                        new AttributeModifier(QUICK_STRIKE_ATTACK_SPEED_MODIFIER, "Quick strike attack speed bonus", attackSpeedBonus, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        } else {
            if (player.getAttribute(Attributes.ATTACK_SPEED).getModifier(QUICK_STRIKE_ATTACK_SPEED_MODIFIER) != null) {
                player.getAttribute(Attributes.ATTACK_SPEED).removeModifier(QUICK_STRIKE_ATTACK_SPEED_MODIFIER);
            }
        }
    }
}
