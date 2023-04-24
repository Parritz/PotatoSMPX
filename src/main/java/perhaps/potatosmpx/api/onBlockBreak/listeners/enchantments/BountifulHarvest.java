package perhaps.potatosmpx.api.onBlockBreak.listeners.enchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import perhaps.potatosmpx.api.config.WeightedItems;
import perhaps.potatosmpx.api.onBlockBreak.listeners.EnchantmentFunction;
import perhaps.potatosmpx.api.registry.EnchantmentBase;
import perhaps.potatosmpx.api.registry.PlayerSkillBase;
import perhaps.potatosmpx.api.config.LuckHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static perhaps.potatosmpx.api.onBlockBreak.OnBlockBreak.*;

public class BountifulHarvest {
	public static int priority = 2;
	public static final List<Class<? extends Block>> validBlocks = Arrays.asList(
			NetherWartBlock.class,
			CropBlock.class
	);

	public static final Map<String, Object> enchantmentData = Map.of(
			"priority", priority,
			"validBlocks", validBlocks,
			"enchantment", EnchantmentBase.BOUNTIFUL_HARVEST,
			"tag", "drop"
	);

	EnchantmentFunction bountifulHarvest = (event, level, heldItem, state, block, serverWorld, playerWorld, pos, player) -> {
		List<ItemStack> blockDrops = getDrop(state, serverWorld, pos, player, heldItem, true);

		// Obtain the player's luck
		double playerLuck = PlayerSkillBase.getLuck(player) / 100.0;

		Map<Integer, Double> adjustedDrops = LuckHandler.getAdjustedWeights(WeightedItems.cropDrops, playerLuck);
		double totalWeight = LuckHandler.getTotalWeight(adjustedDrops);

		for (ItemStack drop : blockDrops) {
			if (drop.getItem() != Items.NETHER_WART && isSeed(drop.getItem())) { continue; } // Skip any seeds

			int currentCount = drop.getCount();
			int getResult = LuckHandler.getResultEntry(playerWorld, totalWeight, adjustedDrops);
			if (getResult == 0) continue;

			int additionalCount = getResult + level - currentCount;
			drop.setCount(currentCount + additionalCount);
		}
	};

	public static void bountifulHarvestEnchantment(BlockEvent.BreakEvent event, int level, ItemStack heldItem, BlockState state, Block block, ServerLevel serverWorld, Level playerWorld, BlockPos pos, Player player) {
		List<ItemStack> blockDrops = getDrop(state, serverWorld, pos, player, heldItem, true);

		// Obtain the player's luck
		double playerLuck = PlayerSkillBase.getLuck(player) / 100.0;

		Map<Integer, Double> adjustedDrops = LuckHandler.getAdjustedWeights(WeightedItems.cropDrops, playerLuck);
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
