package perhaps.potatosmpx.api.config;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nullable;

public interface EnchantmentFunction {
    void apply(Event event, int level, ItemStack heldItem, @Nullable BlockState state, Block block, @Nullable ServerLevel serverWorld, Level playerWorld, BlockPos pos, Player player);
}