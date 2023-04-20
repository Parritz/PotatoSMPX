package perhaps.potatosmpx.enchantment.custom;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.world.BlockEvent;
import perhaps.potatosmpx.enchantment.ModEnchantments;
public enum onBlockBreak {;
    private void replenishEnchantment(BlockEvent.BreakEvent event, int level, ItemStack heldItem) {
        int greenThumbLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.GREEN_THUMB.get(), heldItem);

    }
    public void listenBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Level world = player.level;
        ItemStack heldItem = player.getMainHandItem();
        if (heldItem.isEmpty()) return;

        int replenishLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.REPLENISH.get(), heldItem);
        int autoSmeltLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.AUTO_SMELT.get(), heldItem);
        int blastMasteryLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.BLAST_MASTERY.get(), heldItem);
        int smokeMasteryLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.SMOKE_MASTERY.get(), heldItem);
        if (replenishLevel >= 1) replenishEnchantment(event, replenishLevel, heldItem);
    }
}
