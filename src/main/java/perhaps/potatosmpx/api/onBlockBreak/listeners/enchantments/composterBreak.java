package perhaps.potatosmpx.api.onBlockBreak.listeners.enchantments;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import perhaps.potatosmpx.api.config.EnchantmentFunction;
import perhaps.potatosmpx.api.registry.EnchantmentBase;

import java.util.Arrays;
import java.util.List;

import static perhaps.potatosmpx.api.config.ConfigCommon.composterDrops;

public class composterBreak {
    public static int priority = 1;
    public static final List<Class<? extends Block>> validBlocks = Arrays.asList(
            NetherWartBlock.class,
            CropBlock.class
    );

    public static String tag = "drop";
    public static Enchantment enchantment = EnchantmentBase.COMPOSTER.get();

    public static EnchantmentFunction mainFunction = (event, level, heldItem, state, block, serverWorld, playerWorld, pos, player) -> {
        List<ItemStack> cropDrops = Block.getDrops(state, serverWorld, pos, null, player, heldItem);

        for (ItemStack itemStack : player.getInventory().items) {
            Item cropItem = itemStack.getItem();

            Item compressedCrop = composterDrops.get(cropItem);
            if (compressedCrop == null) continue;

            int oldCount = itemStack.getCount();

            int compressedCount = oldCount / 9;
            int excessCount = oldCount % 9;

            itemStack.setCount(excessCount);
            cropDrops.add(new ItemStack(composterDrops.get(cropItem), compressedCount));
        }
    };
}
