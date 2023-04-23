package perhaps.potatosmpx.api.onBlockBreak.listeners.enchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;

import static perhaps.potatosmpx.api.onBlockBreak.OnBlockBreak.getDrop;
import static perhaps.potatosmpx.api.onBlockBreak.OnBlockBreak.isSeed;

public class BountifulHarvest {
	public static void bountifulHarvestEnchantment(BlockEvent.BreakEvent event, int level, ItemStack heldItem, BlockState state, Block block, ServerLevel serverWorld, Level playerWorld, BlockPos pos, Player player) {
		List<ItemStack> blockDrops = getDrop(state, serverWorld, pos, player, heldItem);

		// Obtain the player's luck
		double playerLuck = PlayerSkillBase.getLuck(player) / 100.0;

		Map<Integer, Double> adjustedDrops = LuckHandler.getAdjustedWeights(cropDrops, playerLuck);
		double totalWeight = LuckHandler.getTotalWeight(adjustedDrops);

		for (ItemStack drop : blockDrops) {
			if (drop.getItem() != Items.NETHER_WART && isSeed(drop.getItem())) { continue; } // Skip any seeds

			int currentCount = drop.getCount();
			int getResult = LuckHandler.getResultEntry(playerWorld, totalWeight, adjustedDrops);
			if (getResult == 0) continue;

			int additionalCount = getResult + level - currentCount;
			drop.setCount(currentCount + additionalCount);
		}
	}
}
