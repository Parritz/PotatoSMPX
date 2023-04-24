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
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import perhaps.potatosmpx.api.registry.EnchantmentBase;

import java.util.List;

import static perhaps.potatosmpx.api.config.ConfigCommon.cropCompressorDrops;
import static perhaps.potatosmpx.api.onBlockBreak.OnBlockBreak.getDrop;

public class CropCompressor {
    public static void cropCompressorEnchantment(BlockEvent.BreakEvent event, int level, ItemStack heldItem, BlockState state, Block block, ServerLevel serverWorld, Level playerWorld, BlockPos pos, Player player) {
        List<ItemStack> cropDrops = getDrop(state, serverWorld, pos, player, heldItem, true);

        for (ItemStack cropDrop : cropDrops) {
            Item cropItem = cropDrop.getItem();
            if (cropCompressorDrops.get(cropItem) != null) {
                int currentCount = cropDrop.getCount();
                if (currentCount <= 9) continue;

                int convertedCount = currentCount / 9;
                int excessCount = currentCount % 9;

                cropDrop.setCount(excessCount);
                cropDrops.add(new ItemStack(cropCompressorDrops.get(cropItem), convertedCount));

                for (ItemStack itemStack : player.getInventory().items) {
                    if (itemStack.getItem() == Items.WHEAT) {
                        int newCount = itemStack.getCount();
                        int excessCount2 = newCount % 9;

                        itemStack.setCount(excessCount2);
                        cropDrops.add(new ItemStack(cropCompressorDrops.get(cropItem), newCount / 9));
                    }
                }
            }
        }
    }
}
