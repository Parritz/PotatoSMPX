package perhaps.potatosmpx.api.config;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import perhaps.potatosmpx.api.onBlockBreak.listeners.enchantments.*;
import perhaps.potatosmpx.api.onBreakSpeed.listeners.enchantments.*;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class EnchantmentData {
    private EnchantmentFunction function;
    private int priority;
    private Enchantment enchantment;
    private String tag;

    private List<Class<? extends Block>> validBlocks;

    public EnchantmentData(int priority, Enchantment enchantment, String tag, @Nullable EnchantmentFunction func, @Nullable List<Class<? extends Block>> validBlocks) {
        this.priority = priority;
        this.enchantment = enchantment;
        this.tag = tag;
        this.function = func;
        this.validBlocks = validBlocks;
    }

    public int getPriority() {
        return priority;
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
    public List<Class<? extends Block>> getValidBlocks() {
        return validBlocks;
    }

    public static final List<EnchantmentData> onBlockBreakEnchantments = Arrays.asList(
            new EnchantmentData(quakeBreak.priority, quakeBreak.enchantment, quakeBreak.tag, quakeBreak.mainFunction, quakeBreak.validBlocks),
            new EnchantmentData(bountifulHarvestBreak.priority, bountifulHarvestBreak.enchantment, bountifulHarvestBreak.tag, bountifulHarvestBreak.mainFunction, bountifulHarvestBreak.validBlocks),
            new EnchantmentData(momentumBreak.priority, momentumBreak.enchantment, momentumBreak.tag, momentumBreak.mainFunction, momentumBreak.validBlocks),
            new EnchantmentData(cropCompressorBreak.priority, cropCompressorBreak.enchantment, cropCompressorBreak.tag, cropCompressorBreak.mainFunction, cropCompressorBreak.validBlocks),
            new EnchantmentData(replenishBreak.priority, replenishBreak.enchantment, replenishBreak.tag, replenishBreak.mainFunction, replenishBreak.validBlocks),
            new EnchantmentData(farmersDelightBreak.priority, farmersDelightBreak.enchantment, farmersDelightBreak.tag, farmersDelightBreak.mainFunction, farmersDelightBreak.validBlocks),
            new EnchantmentData(wisdomBreak.priority, wisdomBreak.enchantment, wisdomBreak.tag, wisdomBreak.mainFunction, wisdomBreak.validBlocks),
            new EnchantmentData(composterBreak.priority, composterBreak.enchantment, composterBreak.tag, composterBreak.mainFunction, composterBreak.validBlocks)
    );

    public static final List<EnchantmentData> onBreakSpeedEnchantments = Arrays.asList(
            new EnchantmentData(MomentumSpeed.priority, MomentumSpeed.enchantment, MomentumSpeed.tag, MomentumSpeed.mainFunction, MomentumSpeed.validBlocks),
            new EnchantmentData(DeepMiner.priority, DeepMiner.enchantment, DeepMiner.tag, DeepMiner.mainFunction, DeepMiner.validBlocks)
    );

    public static final List<EnchantmentData> onLivingDropsEnchantments = Arrays.asList(

    );
}
