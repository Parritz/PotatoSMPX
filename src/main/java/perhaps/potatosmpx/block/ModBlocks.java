package perhaps.potatosmpx.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import perhaps.potatosmpx.Potatosmpx;
import perhaps.potatosmpx.item.ModCreativeModeTab;
import perhaps.potatosmpx.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Potatosmpx.MOD_ID);

    public static final RegistryObject<Block> TITANIUM_BLOCK = registerBlock("titanium_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(9f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> STEEL_BLOCK = registerBlock("steel_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(9f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> TITANIUM_ORE = registerBlock("titanium_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(3f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> MANA_CRYSTAL_ORE = registerBlock("mana_crystal_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(3f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MANA_CRYSTAL_BLOCK = registerBlock("mana_crystal_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(3f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> COMPRESSED_HAY_BLOCK = registerBlock("compressed_hay_block", () -> new Block(BlockBehaviour.Properties.of(Material.SAND).strength(1f)));
    public static final RegistryObject<Block> DOUBLE_COMPRESSED_HAY_BLOCK = registerBlock("double_compressed_hay_block", () -> new Block(BlockBehaviour.Properties.of(Material.SAND).strength(1f)));
    public static final RegistryObject<Block> TRIPLE_COMPRESSED_HAY_BLOCK = registerBlock("triple_compressed_hay_block", () -> new Block(BlockBehaviour.Properties.of(Material.SAND).strength(1f)));
    public static final RegistryObject<Block> QUADRUPLE_COMPRESSED_HAY_BLOCK = registerBlock("quadruple_compressed_hay_block", () -> new Block(BlockBehaviour.Properties.of(Material.SAND).strength(1f)));
    public static final RegistryObject<Block> QUINTUPLE_COMPRESSED_HAY_BLOCK = registerBlock("quintuple_compressed_hay_block", () -> new Block(BlockBehaviour.Properties.of(Material.SAND).strength(1f)));
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, ModCreativeModeTab.TAB_POTATOSMPX);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
