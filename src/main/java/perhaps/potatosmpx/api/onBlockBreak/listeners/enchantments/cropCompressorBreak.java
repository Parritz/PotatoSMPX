package perhaps.potatosmpx.api.onBlockBreak.listeners.enchantments;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraftforge.event.world.BlockEvent;
import perhaps.potatosmpx.api.config.EnchantmentFunction;
import perhaps.potatosmpx.api.registry.EnchantmentBase;

import java.util.*;

import static perhaps.potatosmpx.api.config.ConfigCommon.cropCompressorDrops;
import static perhaps.potatosmpx.api.onDrop.OnDrop.handleDrops;

public class cropCompressorBreak {
    public static int priority = 3;
    public static final List<Class<? extends Block>> validBlocks = Arrays.asList(
        NetherWartBlock.class,
        CropBlock.class
    );
    public static String tag = "drop";
    public static Enchantment enchantment = EnchantmentBase.CROP_COMPRESSOR.get();

    public static List<ItemStack> compressInventory(Player player) {
        Map<Item, Integer> itemCounts = new HashMap<>();

        // Collect all compressible items and their counts
        for (ItemStack itemStack : player.getInventory().items) {
            if (itemStack.isEmpty()) continue;
            if (!cropCompressorDrops.containsKey(itemStack.getItem())) continue;

            Item item = itemStack.getItem();
            int count = itemStack.getCount();

            itemCounts.put(item, itemCounts.getOrDefault(item, 0) + count);
        }

        for (ItemStack itemStack : player.getInventory().items) {
            if (itemStack.isEmpty() || itemCounts.get(itemStack.getItem()) == null) continue;

            int count = itemCounts.get(itemStack.getItem());
            if (count >= 9) {
                itemStack.setCount(0);
            }
        }

        List<ItemStack> newItems = new ArrayList<>();
        for (Map.Entry<Item, Integer> entry : itemCounts.entrySet()) {
            Item item = entry.getKey();
            int count = entry.getValue();

            // Get the compressed item

            Item compressedItem = cropCompressorDrops.get(item);
            if (compressedItem == null || count <= 8) continue;

            while (count >= 9) {
                if (!cropCompressorDrops.containsKey(item)) break;

                int compressedCount = count / 9;
                int excessCount = count % 9;

                if (compressedCount >= 9) {
                    newItems.add(new ItemStack(compressedItem, excessCount));
                } else {
                    newItems.add(new ItemStack(compressedItem, compressedCount));
                    newItems.add(new ItemStack(item, excessCount));
                }

                count = compressedCount;
                item = compressedItem;
            }
        }

        return newItems;
    }

    public static EnchantmentFunction mainFunction = (event, level, heldItem, state, block, serverWorld, playerWorld, pos, player) -> {
        List<ItemStack> newDrops = compressInventory(player);

        // Add compressed items to player inventory
        handleDrops((BlockEvent.BreakEvent) event, null, newDrops);
    };
}
