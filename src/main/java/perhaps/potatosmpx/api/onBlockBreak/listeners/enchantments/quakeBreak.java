package perhaps.potatosmpx.api.onBlockBreak.listeners.enchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import perhaps.potatosmpx.api.config.EnchantmentFunction;
import perhaps.potatosmpx.api.registry.EnchantmentBase;
import perhaps.potatosmpx.api.registry.PlayerSkillBase;

import java.util.Arrays;
import java.util.List;

import static perhaps.potatosmpx.api.onBlockBreak.OnBlockBreak.*;

public class quakeBreak {
	public static int priority = 1;
	public static final List<Class<? extends Block>> validBlocks = Arrays.asList(

	);

	public static String tag = "break";
	public static Enchantment enchantment = EnchantmentBase.QUAKE.get();

	public static boolean isStoneBased(Block block) {
		// Add your own criteria for determining if a block is stone-based
		// Example: return block instanceof StoneBlock || block instanceof CobblestoneBlock;
		return true;
	}

	public static void breakBlocksInArea(Level world, BlockPos center, Player player, List<ItemStack> blockDrops, ItemStack heldItem, ServerLevel serverWorld) {
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				for (int dz = -1; dz <= 1; dz++) {
					BlockPos pos = center.offset(dx, dy, dz);
					BlockState state = world.getBlockState(pos);
					Block block = state.getBlock();

					if (isStoneBased(block)) {
						List<ItemStack> drops = Block.getDrops(state, serverWorld, pos, null, player, heldItem);
						blockDrops.addAll(drops);

						world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
						world.sendBlockUpdated(pos, state, Blocks.AIR.defaultBlockState(), 3);
					}
				}
			}
		}
	}

	public static EnchantmentFunction mainFunction = (event, level, heldItem, state, block, serverWorld, playerWorld, pos, player) -> {
		if (!PlayerSkillBase.willRunEnchantment(player, 0.1f, level)) return;

		if (isStoneBased(block)) {
			breakBlocksInArea(playerWorld, pos, player, Block.getDrops(state, serverWorld, pos, null, player, heldItem), heldItem, serverWorld);
		}
	};
}
