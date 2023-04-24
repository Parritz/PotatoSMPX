package perhaps.potatosmpx.api.onBreakSpeed.listeners.enchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;

import static perhaps.potatosmpx.api.config.ConfigCommon.MOMENTUM_MAX_CONSECUTIVE_BLOCKS;
import static perhaps.potatosmpx.api.config.ConfigCommon.MOMENTUM_TAG;

public class MomentumSpeed {
    public static void momentumEnchantment(PlayerEvent.BreakSpeed event, int level, ItemStack heldItem, BlockState state, Block block, ServerLevel serverWorld, Level playerWorld, BlockPos pos, Player player) {
        int minedBlocks = player.getPersistentData().getInt(MOMENTUM_TAG);

        double miningSpeedBonus = (0.05f * level) * (Math.min(minedBlocks, MOMENTUM_MAX_CONSECUTIVE_BLOCKS));
        event.setNewSpeed((float) (event.getNewSpeed() + miningSpeedBonus));
    }
}
