package perhaps.potatosmpx.api.onBlockBreak.listeners.enchantments;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import perhaps.potatosmpx.api.config.CropHandler;
import perhaps.potatosmpx.api.config.WeightedItems;
import perhaps.potatosmpx.api.config.EnchantmentFunction;
import perhaps.potatosmpx.api.registry.EnchantmentBase;
import perhaps.potatosmpx.api.registry.PlayerSkillBase;
import perhaps.potatosmpx.api.config.LuckHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static perhaps.potatosmpx.api.onBlockBreak.OnBlockBreak.*;

public class bountifulHarvestBreak {
	public static int priority = 1;
	public static final List<Class<? extends Block>> validBlocks = Arrays.asList(
			NetherWartBlock.class,
			CropBlock.class
	);

	public static String tag = "drop";
	public static Enchantment enchantment = EnchantmentBase.BOUNTIFUL_HARVEST.get();

	public static EnchantmentFunction mainFunction = (event, level, heldItem, state, block, serverWorld, playerWorld, pos, player) -> {
		List<ItemStack> blockDrops = Block.getDrops(state, serverWorld, pos, null, player, heldItem);

		// Obtain the player's luck
		double playerLuck = PlayerSkillBase.getLuck(player) / 100.0;

		Map<Integer, Double> adjustedDrops = LuckHandler.getAdjustedWeights(WeightedItems.cropDrops, playerLuck);
		double totalWeight = LuckHandler.getTotalWeight(adjustedDrops);

		for (ItemStack drop : blockDrops) {
			if (!CropHandler.isCropItem(drop.getItem())) { continue; } // Skip any seeds

			int currentCount = drop.getCount();
			int getResult = LuckHandler.getResultEntry(playerWorld, totalWeight, adjustedDrops);
			if (getResult == 0) continue;

			int additionalCount = getResult + level;
			drop.setCount(currentCount + additionalCount);
			System.out.println(drop.getCount());
		}
	};
}
