package perhaps.potatosmpx.api.config;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import perhaps.potatosmpx.api.onBlockBreak.listeners.enchantments.*;
import perhaps.potatosmpx.api.onBreakSpeed.listeners.enchantments.MomentumSpeed;

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

    public EnchantmentFunction getFunction() {
        return function;
    }

    public static final List<EnchantmentData> onBlockBreakEnchantments = Arrays.asList(
            new EnchantmentData(quakeBreak.priority, quakeBreak.validBlocks, quakeBreak.enchantment, quakeBreak.tag, quakeBreak.mainFunction),
            new EnchantmentData(bountifulHarvestBreak.priority, bountifulHarvestBreak.validBlocks, bountifulHarvestBreak.enchantment, bountifulHarvestBreak.tag, bountifulHarvestBreak.mainFunction),
            new EnchantmentData(momentumBreak.priority, momentumBreak.validBlocks, momentumBreak.enchantment, momentumBreak.tag, momentumBreak.mainFunction),
            new EnchantmentData(cropCompressorBreak.priority, cropCompressorBreak.validBlocks, cropCompressorBreak.enchantment, cropCompressorBreak.tag, cropCompressorBreak.mainFunction),
            new EnchantmentData(replenishBreak.priority, replenishBreak.validBlocks, replenishBreak.enchantment, replenishBreak.tag, replenishBreak.mainFunction),
            new EnchantmentData(farmersDelightBreak.priority, farmersDelightBreak.validBlocks, farmersDelightBreak.enchantment, farmersDelightBreak.tag, farmersDelightBreak.mainFunction),
            new EnchantmentData(wisdomBreak.priority, wisdomBreak.validBlocks, wisdomBreak.enchantment, wisdomBreak.tag, wisdomBreak.mainFunction),
            new EnchantmentData(composterBreak.priority, composterBreak.validBlocks, composterBreak.enchantment, composterBreak.tag, composterBreak.mainFunction)
    );

    public static final List<EnchantmentData> onBreakSpeedEnchantments = Arrays.asList(
            new EnchantmentData(MomentumSpeed.priority, MomentumSpeed.validBlocks, MomentumSpeed.enchantment, MomentumSpeed.tag, MomentumSpeed.mainFunction)
    );
}
