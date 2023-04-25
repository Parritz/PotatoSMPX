package perhaps.potatosmpx.api.onBlockBreak.listeners.enchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import perhaps.potatosmpx.api.onBlockBreak.listeners.EnchantmentFunction;
import perhaps.potatosmpx.api.registry.EnchantmentBase;

import java.util.Arrays;
import java.util.List;

import static perhaps.potatosmpx.api.onBlockBreak.OnBlockBreak.changeState;

public class replenishBreak {
	public static int priority = 1;
	public static final List<Class<? extends Block>> validBlocks = Arrays.asList(
			NetherWartBlock.class,
			CropBlock.class
	);
	public static String tag = "break";
	public static Enchantment enchantment = EnchantmentBase.REPLENISH.get();
	public static EnchantmentFunction mainFunction = (event, level, heldItem, state, block, serverWorld, playerWorld, pos, player) -> {
		int greenThumbLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentBase.GREEN_THUMB.get(), heldItem);

		NetherWartBlock netherWartBlock = block instanceof NetherWartBlock ? (NetherWartBlock) block : null;
		CropBlock cropBlock = block instanceof CropBlock ? (CropBlock) block : null;

		BlockState seedBlockState = block.defaultBlockState();
		if (greenThumbLevel >= 1) {
			int maxAge = netherWartBlock != null ? NetherWartBlock.MAX_AGE : cropBlock != null ? cropBlock.getMaxAge() : 0;
			int stageBoost = (int) (0.5 * greenThumbLevel * maxAge);
			int newAge = Math.min(stageBoost, maxAge - 1);
			if (maxAge > 0) {
				seedBlockState = seedBlockState.setValue(netherWartBlock != null ? NetherWartBlock.AGE : cropBlock.getAgeProperty(), newAge);
			}
		}

		changeState(pos, seedBlockState);
	};
}
