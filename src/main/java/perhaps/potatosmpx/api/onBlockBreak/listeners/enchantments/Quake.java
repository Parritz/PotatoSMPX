package perhaps.potatosmpx.api.onBlockBreak.listeners.enchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;

import static perhaps.potatosmpx.api.onBlockBreak.OnBlockBreak.breakBlocksInArea;
import static perhaps.potatosmpx.api.onBlockBreak.OnBlockBreak.isStoneBased;

public class Quake {
	public static void quakeEnchantment(BlockEvent.BreakEvent event, int level, ItemStack heldItem, BlockState state, Block block, ServerLevel serverWorld, Level playerWorld, BlockPos pos, Player player) {
		if (playerWorld.getRandom().nextFloat() > (0.1f * level)) return;

		if (isStoneBased(block)) {
			breakBlocksInArea(playerWorld, pos, player);
		}
	}
}
