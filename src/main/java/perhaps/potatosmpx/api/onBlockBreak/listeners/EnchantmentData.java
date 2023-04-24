package perhaps.potatosmpx.api.onBlockBreak.listeners;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import perhaps.potatosmpx.api.onBlockBreak.listeners.enchantments.Quake;
import perhaps.potatosmpx.api.registry.EnchantmentBase;
import perhaps.potatosmpx.enchantment.enchantments.QuakeEnchantment;

import java.util.Arrays;
import java.util.List;

public class EnchantmentData {
    private EnchantmentFunction function;
    private int priority;
    private List<Class<? extends Block>> validBlocks;
    private Enchantment enchantment;
    private String tag;

    public EnchantmentData(int priority, List<Class<? extends Block>> validBlocks, Enchantment enchantment, String tag, EnchantmentFunction func) {
        this.priority = priority;
        this.validBlocks = validBlocks;
        this.enchantment = enchantment;
        this.tag = tag;
        this.function = func;
    }

    public int getPriority() {
        return priority;
    }

    public List<Class<? extends Block>> getValidBlocks() {
        return validBlocks;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public String getTag() {
        return tag;
    }

    public static final List<EnchantmentData> validEnchantments = Arrays.asList(
            new EnchantmentData(Quake.priority, EnchantmentBase.QUAKE.get(), Quake.tag, QuakeEnchantment)
    );
}
