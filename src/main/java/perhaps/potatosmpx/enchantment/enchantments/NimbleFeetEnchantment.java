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

public class NimbleFeetEnchantment extends Enchantment {
    public NimbleFeetEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
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

    private static final UUID NIMBLE_FEET_MOVEMENT_SPEED_MODIFIER = UUID.fromString("e9c53012-6f9c-4a98-84e6-3f76e31469f3");

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        int nimbleFeetLevel = EnchantmentHelper.getItemEnchantmentLevel(this, boots);

        if (nimbleFeetLevel > 0) {
            double movementSpeedBonus = 2.5 * nimbleFeetLevel;

            if (player.getAttribute(Attributes.MOVEMENT_SPEED).getModifier(NIMBLE_FEET_MOVEMENT_SPEED_MODIFIER) == null) {
                player.getAttribute(Attributes.MOVEMENT_SPEED).addPermanentModifier(
                        new AttributeModifier(NIMBLE_FEET_MOVEMENT_SPEED_MODIFIER, "Nimble feet movement speed bonus", movementSpeedBonus, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        } else {
            if (player.getAttribute(Attributes.MOVEMENT_SPEED).getModifier(NIMBLE_FEET_MOVEMENT_SPEED_MODIFIER) != null) {
                player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(NIMBLE_FEET_MOVEMENT_SPEED_MODIFIER);
            }
        }
    }
}
