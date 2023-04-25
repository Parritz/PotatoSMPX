package perhaps.potatosmpx.api.onBlockBreak.listeners.enchantments;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.OreBlock;
import perhaps.potatosmpx.api.config.EnchantmentFunction;
import perhaps.potatosmpx.api.registry.EnchantmentBase;

import java.util.Arrays;
import java.util.List;

public class wisdomBreak {
    public static int priority = 1;
    public static final List<Class<? extends Block>> validBlocks = Arrays.asList(
            NetherWartBlock.class,
            CropBlock.class,
            OreBlock.class
    );

    public static String tag = "data";
    public static Enchantment enchantment = EnchantmentBase.WISDOM.get();
    public static EnchantmentFunction mainFunction = (event, level, heldItem, state, block, serverWorld, playerWorld, pos, player) -> {
        int fortuneLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, heldItem);
        int silkTouchLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, heldItem);

        if (block instanceof OreBlock) {
            int extraXp = block.getExpDrop(state, playerWorld, pos, fortuneLevel, silkTouchLevel) * level;
            player.giveExperiencePoints(extraXp);
        } else {
            player.giveExperiencePoints((int) (0.5f + (level * 0.1)));
        }
    };
}
