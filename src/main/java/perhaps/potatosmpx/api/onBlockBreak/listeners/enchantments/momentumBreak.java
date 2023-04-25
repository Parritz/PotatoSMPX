package perhaps.potatosmpx.api.onBlockBreak.listeners.enchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import perhaps.potatosmpx.api.config.EnchantmentFunction;
import perhaps.potatosmpx.api.registry.EnchantmentBase;

import java.util.Arrays;
import java.util.List;

import static perhaps.potatosmpx.api.config.ConfigCommon.MOMENTUM_TAG;

public class momentumBreak {
    public static int priority = 2;
    public static final List<Class<? extends Block>> validBlocks = Arrays.asList(

    );

    public static String tag = "data";
    public static Enchantment enchantment = EnchantmentBase.MOMENTUM.get();

    public static EnchantmentFunction mainFunction = (event, level, heldItem, state, block, serverWorld, playerWorld, pos, player) -> {
        int minedBlocks = player.getPersistentData().getInt(MOMENTUM_TAG);

        CompoundTag persistentData = player.getPersistentData();
        if (persistentData.contains("last_mined_block_pos")) {
            long lastPosLong = persistentData.getLong("last_mined_block_pos");
            BlockPos lastPos = BlockPos.of(lastPosLong);

            if (pos.closerThan(lastPos, 2)) {
                minedBlocks++;
            } else {
                minedBlocks = 0;
            }
        }

        player.getPersistentData().putInt(MOMENTUM_TAG, minedBlocks);
        player.getPersistentData().putLong("last_mined_block_pos", pos.asLong());
    };
}