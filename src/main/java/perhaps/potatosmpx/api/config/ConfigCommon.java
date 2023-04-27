package perhaps.potatosmpx.api.config;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import perhaps.potatosmpx.block.ModBlocks;

import java.util.Map;

public class ConfigCommon {
    public static final String MOMENTUM_TAG = "momentum_mined_blocks";
    public static final int MOMENTUM_MAX_CONSECUTIVE_BLOCKS = 20;
    public static Map<Item, Item> cropCompressorDrops = Map.of(
            Items.WHEAT, Items.HAY_BLOCK,
            Items.HAY_BLOCK, ModBlocks.COMPRESSED_HAY_BLOCK.get().asItem(),
            ModBlocks.COMPRESSED_HAY_BLOCK.get().asItem(), ModBlocks.DOUBLE_COMPRESSED_HAY_BLOCK.get().asItem(),
            ModBlocks.DOUBLE_COMPRESSED_HAY_BLOCK.get().asItem(), ModBlocks.TRIPLE_COMPRESSED_HAY_BLOCK.get().asItem(),
            ModBlocks.TRIPLE_COMPRESSED_HAY_BLOCK.get().asItem(), ModBlocks.QUADRUPLE_COMPRESSED_HAY_BLOCK.get().asItem(),
            ModBlocks.QUADRUPLE_COMPRESSED_HAY_BLOCK.get().asItem(), ModBlocks.QUINTUPLE_COMPRESSED_HAY_BLOCK.get().asItem()
    );

    public static Map<Item, Item> composterDrops = Map.of(
            Items.WHEAT_SEEDS, Items.BONE_BLOCK,
            Items.BEETROOT_SEEDS, Items.BONE_BLOCK,
            Items.PUMPKIN_SEEDS, Items.BONE_BLOCK,
            Items.MELON_SEEDS, Items.BONE_BLOCK
    );

    private final Map<EntityType<?>, Item> entityHeads = Map.of(
            EntityType.ZOMBIE, Items.ZOMBIE_HEAD,
            EntityType.SKELETON, Items.SKELETON_SKULL,
            EntityType.WITHER_SKELETON, Items.WITHER_SKELETON_SKULL,
            EntityType.CREEPER, Items.CREEPER_HEAD,
            EntityType.PLAYER, Items.PLAYER_HEAD
    );
}
