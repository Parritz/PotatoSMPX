package perhaps.potatosmpx.api.onBlockBreak.listeners.enchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import perhaps.potatosmpx.api.config.WeightedItems;
import perhaps.potatosmpx.api.registry.EnchantmentBase;
import perhaps.potatosmpx.api.registry.PlayerSkillBase;
import perhaps.potatosmpx.api.config.LuckHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static perhaps.potatosmpx.api.onBlockBreak.OnBlockBreak.getDrop;

public class FarmersDelight {
	public static int priority = 2;
	public static final List<Class<? extends Block>> validBlocks = Arrays.asList(
			NetherWartBlock.class,
			CropBlock.class
	);

	public static final Map<String, Object> enchantmentData = Map.of(
			"priority", priority,
			"validBlocks", validBlocks,
			"enchantment", EnchantmentBase.FARMERS_DELIGHT,
			"tag", "drop"
	);

	public static void farmersDelightEnchantment(BlockEvent.BreakEvent event, int level, ItemStack heldItem, BlockState state, Block block, ServerLevel serverWorld, Level playerWorld, BlockPos pos, Player player) {
		List<ItemStack> blockDrops = getDrop(state, serverWorld, pos, player, heldItem, true);

		if (!PlayerSkillBase.willRunEnchantment(player, 0.05f, level)) return;
		double playerLuck = PlayerSkillBase.getLuck(player) / 100.0;

		Map<Item, Double> adjustedDrops = LuckHandler.getAdjustedWeights(WeightedItems.rareDrops, playerLuck);
		double totalWeight = LuckHandler.getTotalWeight(adjustedDrops);

		Item selectedItem = LuckHandler.getResultEntry(playerWorld, totalWeight, adjustedDrops);
		if (selectedItem == null) return;

		ItemStack oreStack = new ItemStack(selectedItem);
		oreStack.setCount(1);

		blockDrops.add(oreStack);
	}
}
