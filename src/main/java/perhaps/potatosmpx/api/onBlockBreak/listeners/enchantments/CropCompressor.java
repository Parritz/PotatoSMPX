package perhaps.potatosmpx.api.onBlockBreak.listeners.enchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import perhaps.potatosmpx.api.registry.EnchantmentBase;

import java.util.*;

import static perhaps.potatosmpx.api.config.ConfigCommon.cropCompressorDrops;
import static perhaps.potatosmpx.api.onBlockBreak.OnBlockBreak.getDrop;

public class CropCompressor {
    public static int priority = 2;
    public static final List<Class<? extends Block>> validBlocks = Arrays.asList(
        NetherWartBlock.class,
        CropBlock.class
    );

    public static final Map<String, Object> enchantmentData = Map.of(
            "priority", priority,
            "validBlocks", validBlocks,
            "enchantment", EnchantmentBase.CROP_COMPRESSOR,
            "tag", "drop"
    );

    public static void cropCompressorEnchantment(BlockEvent.BreakEvent event, int level, ItemStack heldItem, BlockState state, Block block, ServerLevel serverWorld, Level playerWorld, BlockPos pos, Player player) {
        List<ItemStack> cropDrops = getDrop(state, serverWorld, pos, player, heldItem, true);

        for (ItemStack cropDrop : cropDrops) {
            Item cropItem = cropDrop.getItem();

            Item compressedCrop = cropCompressorDrops.get(cropItem);
            if (compressedCrop == null) continue;

            int cropCount = cropDrop.getCount();
            if (cropCount <= 9) continue;

            int compressedCount = cropCount / 9;
            int excessCount = cropCount % 9;

            cropDrop.setCount(excessCount);
            cropDrops.add(new ItemStack(compressedCrop, compressedCount));
        }

        for (ItemStack itemStack : player.getInventory().items) {
            Item cropItem = itemStack.getItem();

            Item compressedCrop = cropCompressorDrops.get(cropItem);
            if (compressedCrop == null) continue;

            int oldCount = itemStack.getCount();

            int compressedCount = oldCount / 9;
            int excessCount = oldCount % 9;

            itemStack.setCount(excessCount);
            cropDrops.add(new ItemStack(cropCompressorDrops.get(cropItem), compressedCount / 9));
        }
    }
}
