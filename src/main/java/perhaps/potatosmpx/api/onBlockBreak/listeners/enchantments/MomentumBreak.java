package perhaps.potatosmpx.api.onBlockBreak.listeners.enchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import perhaps.potatosmpx.api.registry.EnchantmentBase;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static perhaps.potatosmpx.api.config.ConfigCommon.MOMENTUM_MAX_CONSECUTIVE_BLOCKS;
import static perhaps.potatosmpx.api.config.ConfigCommon.MOMENTUM_TAG;

public class MomentumBreak {
    public static int priority = 2;
    public static final List<Class<? extends Block>> validBlocks = Arrays.asList(

    );

    public static final Map<String, Object> enchantmentData = Map.of(
            "priority", priority,
            "validBlocks", validBlocks,
            "enchantment", EnchantmentBase.MOMENTUM,
            "tag", "data"
    );

    public static void momentumEnchantment(BlockEvent.BreakEvent event, int level, ItemStack heldItem, BlockState state, Block block, ServerLevel serverWorld, Level playerWorld, BlockPos pos, Player player) {
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
    }
}