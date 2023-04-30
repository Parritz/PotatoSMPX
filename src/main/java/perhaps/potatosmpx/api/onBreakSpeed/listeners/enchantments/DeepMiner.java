package perhaps.potatosmpx.api.onBreakSpeed.listeners.enchantments;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.player.PlayerEvent;
import perhaps.potatosmpx.api.config.EnchantmentFunction;
import perhaps.potatosmpx.api.registry.EnchantmentBase;

import java.util.Arrays;
import java.util.List;

public class DeepMiner {
    public static int priority = 2;
    public static final List<Class<? extends Block>> validBlocks = Arrays.asList(

    );

    public static String tag = "data";
    public static Enchantment enchantment = EnchantmentBase.DEEP_MINER.get();
    public static EnchantmentFunction mainFunction = (originalEvent, level, heldItem, state, block, serverWorld, playerWorld, pos, player) -> {
        PlayerEvent.BreakSpeed event = (PlayerEvent.BreakSpeed) originalEvent;
        double miningSpeedBonus = (2f * level);

        event.setNewSpeed((float) (event.getNewSpeed() + miningSpeedBonus));
    };
}
