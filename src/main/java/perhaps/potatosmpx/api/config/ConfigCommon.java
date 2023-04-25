package perhaps.potatosmpx.api.config;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Map;

public class ConfigCommon {
    public static final String MOMENTUM_TAG = "momentum_mined_blocks";
    public static final int MOMENTUM_MAX_CONSECUTIVE_BLOCKS = 20;
    public static Map<Item, Item> cropCompressorDrops = Map.of(
            Items.WHEAT, Items.HAY_BLOCK
    );

    public static Map<Item, Item> composterDrops = Map.of(
            Items.WHEAT_SEEDS, Items.BONE_BLOCK,
            Items.BEETROOT_SEEDS, Items.BONE_BLOCK,
            Items.PUMPKIN_SEEDS, Items.BONE_BLOCK,
            Items.MELON_SEEDS, Items.BONE_BLOCK
    );
}
