package perhaps.potatosmpx.api.onBlockBreak.listeners.enchantments;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import perhaps.potatosmpx.api.config.WeightedItems;
import perhaps.potatosmpx.api.config.EnchantmentFunction;
import perhaps.potatosmpx.api.registry.EnchantmentBase;
import perhaps.potatosmpx.api.registry.PlayerSkillBase;
import perhaps.potatosmpx.api.config.LuckHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class farmersDelightBreak {
	public static int priority = 2;
	public static final List<Class<? extends Block>> validBlocks = Arrays.asList(
			NetherWartBlock.class,
			CropBlock.class
	);
	public static String tag = "drop";
	public static Enchantment enchantment = EnchantmentBase.FARMERS_DELIGHT.get();
	public static EnchantmentFunction mainFunction = (event, level, heldItem, state, block, serverWorld, playerWorld, pos, player) -> {
		List<ItemStack> blockDrops = Block.getDrops(state, serverWorld, pos, null, player, heldItem);

		if (!PlayerSkillBase.willRunEnchantment(player, 0.05f, level)) return;
		double playerLuck = PlayerSkillBase.getLuck(player) / 100.0;

		Map<Item, Double> adjustedDrops = LuckHandler.getAdjustedWeights(WeightedItems.rareDrops, playerLuck);
		double totalWeight = LuckHandler.getTotalWeight(adjustedDrops);

		Item selectedItem = LuckHandler.getResultEntry(playerWorld, totalWeight, adjustedDrops);
		if (selectedItem == null) return;

		ItemStack oreStack = new ItemStack(selectedItem);
		oreStack.setCount(1);

		blockDrops.add(oreStack);
	};
}
