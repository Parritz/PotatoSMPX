package perhaps.potatosmpx.api.onBlockBreak.listeners.perks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;

public class MinersInstinct {
	public static void minersInstinctPerk(BlockEvent.BreakEvent event, int level, ItemStack heldItem, BlockState state, Block block, ServerLevel serverWorld, Level playerWorld, BlockPos pos, Player player) {
        List<ItemStack> blockDrops = Block.getDrops(state, serverWorld, pos, null, player, heldItem);
    }
}
