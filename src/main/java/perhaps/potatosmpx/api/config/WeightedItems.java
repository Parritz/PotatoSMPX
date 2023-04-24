package perhaps.potatosmpx.api.config;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public enum WeightedItems {;
    public static final List<Item> cropSeeds = Arrays.asList(
        Items.WHEAT_SEEDS,
        Items.CARROT,
        Items.POTATO,
        Items.BEETROOT_SEEDS,
        Items.PUMPKIN_SEEDS,
        Items.MELON_SEEDS
    );
    public static final Map<Item, Double> rareDrops = Map.of(
            Items.COAL, 0.2,
            Items.IRON_INGOT, 0.2,
            Items.GOLD_INGOT, 0.15,
            Items.REDSTONE, 0.13,
            Items.LAPIS_LAZULI, 0.12,
            Items.DIAMOND, 0.1,
            Items.EMERALD, 0.08,
            Items.NETHERITE_SCRAP, 0.02
    );
    public static final Map<Integer, Double> cropDrops = Map.of(
            1, 0.4,
            2, 0.4,
            3, 0.1,
            4, 0.05,
            5, 0.03,
            6, 0.015,
            7, 0.005
    );
}
