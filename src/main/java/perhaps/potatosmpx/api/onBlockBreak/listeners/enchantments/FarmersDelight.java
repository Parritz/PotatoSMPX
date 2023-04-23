package perhaps.potatosmpx.api.onBlockBreak.listeners.enchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import perhaps.potatosmpx.api.registry.PlayerSkillBase;

import java.util.List;
import java.util.Map;

import static perhaps.potatosmpx.api.onBlockBreak.OnBlockBreak.getDrop;
import static perhaps.potatosmpx.api.onBlockBreak.OnBlockBreak.rareDrops;

public class FarmersDelight {
	public static void farmersDelightEnchantment(BlockEvent.BreakEvent event, int level, ItemStack heldItem, BlockState state, Block block, ServerLevel serverWorld, Level playerWorld, BlockPos pos, Player player) {
		List<ItemStack> blockDrops = getDrop(state, serverWorld, pos, player, heldItem);

		float runChance = 0.05f * level * (PlayerSkillBase.getLuck(player) / 100.0f);
		if (playerWorld.getRandom().nextFloat() > 0.999999) return;

		double randomValue = playerWorld.getRandom().nextDouble();
		double totalWeight = rareDrops.values().stream().mapToDouble(Double::doubleValue).sum();
		Item selectedItem = null;

		for (Map.Entry<Item, Double> entry : rareDrops.entrySet()) {
			double baseDropRate = entry.getValue();
			double adjustedDropRate = PlayerSkillBase.getAdjustedDropRate(player, baseDropRate) * totalWeight;
			if (randomValue < adjustedDropRate / totalWeight) {
				selectedItem = entry.getKey();
				break;
			}
			randomValue -= adjustedDropRate / totalWeight;
		}

		// Create an ItemStack with the random ore
		ItemStack oreStack = new ItemStack(selectedItem);
		oreStack.setCount(1);
		System.out.println(oreStack.getItem());

		// Add the ore to the drops
		blockDrops.add(oreStack);
	}
}
