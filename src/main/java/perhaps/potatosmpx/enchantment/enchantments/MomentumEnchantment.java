package perhaps.potatosmpx.enchantment.enchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import perhaps.potatosmpx.enchantment.EnchantmentRarity;

public class MomentumEnchantment extends Enchantment {
    public MomentumEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinCost(int level) { return EnchantmentRarity.UNCOMMON.getMinCost(level); }
    @Override
    public int getMaxCost(int level) { return EnchantmentRarity.UNCOMMON.getMaxCost(level); }

    private static final String MOMENTUM_TAG = "momentum_mined_blocks";
    private static final int MAX_CONSECUTIVE_BLOCKS = 5;

    @SubscribeEvent
    public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getMainHandItem();
        int momentumLevel = EnchantmentHelper.getItemEnchantmentLevel(this, heldItem);

        if (momentumLevel > 0) {
            int minedBlocks = player.getPersistentData().getInt(MOMENTUM_TAG);
            float miningSpeedBonus = 0.05f * momentumLevel * Math.min(minedBlocks, MAX_CONSECUTIVE_BLOCKS);
            event.setNewSpeed(event.getNewSpeed() * (1 + miningSpeedBonus));
        }
    }

    @SubscribeEvent
    public void onBlockBreak(PlayerEvent.BreakSpeed event) {
        Player player = event.getPlayer();
        BlockPos pos = event.getPos();
        ItemStack heldItem = player.getMainHandItem();
        int momentumLevel = EnchantmentHelper.getItemEnchantmentLevel(this, heldItem);

        if (momentumLevel > 0) {
            int minedBlocks = player.getPersistentData().getInt(MOMENTUM_TAG);

            if (player.getPersistentData().contains("last_mined_block_pos")) {
                long lastPosLong = player.getPersistentData().getLong("last_mined_block_pos");
                BlockPos lastPos = BlockPos.of(lastPosLong);

                if (pos.closerThan(lastPos, 2)) {
                    minedBlocks++;
                } else {
                    minedBlocks = 0;
                }
            }

            player.getPersistentData().putInt(MOMENTUM_TAG, minedBlocks);
            player.getPersistentData().putLong("last_mined_block_pos", pos.asLong());
        }
    }
}
