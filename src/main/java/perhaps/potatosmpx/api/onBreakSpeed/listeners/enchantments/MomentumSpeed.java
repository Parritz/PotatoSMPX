package perhaps.potatosmpx.api.onBreakSpeed.listeners.enchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;
import perhaps.potatosmpx.api.config.EnchantmentFunction;
import perhaps.potatosmpx.api.registry.EnchantmentBase;

import java.util.Arrays;
import java.util.List;

import static perhaps.potatosmpx.api.config.ConfigCommon.MOMENTUM_MAX_CONSECUTIVE_BLOCKS;
import static perhaps.potatosmpx.api.config.ConfigCommon.MOMENTUM_TAG;

public class MomentumSpeed {
    public static int priority = 2;
    public static final List<Class<? extends Block>> validBlocks = Arrays.asList(

    );

    public static String tag = "data";
    public static Enchantment enchantment = EnchantmentBase.MOMENTUM.get();
    public static EnchantmentFunction mainFunction = (event1, level, heldItem, state, block, serverWorld, playerWorld, pos, player) -> {
        PlayerEvent.BreakSpeed event = (PlayerEvent.BreakSpeed) event1;

        int minedBlocks = player.getPersistentData().getInt(MOMENTUM_TAG);

        double miningSpeedBonus = (0.05f * level) * (Math.min(minedBlocks, MOMENTUM_MAX_CONSECUTIVE_BLOCKS));
        event.setNewSpeed((float) (event.getNewSpeed() + miningSpeedBonus));
    };
}
