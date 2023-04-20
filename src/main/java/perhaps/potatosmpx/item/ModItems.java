package perhaps.potatosmpx.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import perhaps.potatosmpx.Potatosmpx;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Potatosmpx.MOD_ID);

    public static final RegistryObject<Item> WOOD_WAND = ITEMS.register("wood_wand", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TAB_POTATOSMPX)));
    public static final RegistryObject<Item> STONE_WAND = ITEMS.register("stone_wand", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TAB_POTATOSMPX)));
    public static final RegistryObject<Item> IRON_WAND = ITEMS.register("iron_wand", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TAB_POTATOSMPX)));
    public static final RegistryObject<Item> STEEL_WAND = ITEMS.register("steel_wand", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TAB_POTATOSMPX)));
    public static final RegistryObject<Item> TITANIUM_WAND = ITEMS.register("titanium_wand", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TAB_POTATOSMPX)));
    public static final RegistryObject<Item> HERO_WAND = ITEMS.register("hero_wand", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TAB_POTATOSMPX)));

    public static final RegistryObject<Item> HERO_SWORD = ITEMS.register("hero_sword", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TAB_POTATOSMPX)));
    public static final RegistryObject<Item> HERO_PICKAXE = ITEMS.register("hero_pickaxe", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TAB_POTATOSMPX)));
    public static final RegistryObject<Item> HERO_AXE = ITEMS.register("hero_axe", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TAB_POTATOSMPX)));

    public static final RegistryObject<Item> HERO_SHOVEL = ITEMS.register("hero_shovel", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TAB_POTATOSMPX)));

    public static final RegistryObject<Item> HERO_HOE = ITEMS.register("hero_hoe", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TAB_POTATOSMPX)));

    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TAB_POTATOSMPX)));
    public static final RegistryObject<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TAB_POTATOSMPX)));

    public static final RegistryObject<Item> TITANIUM_NUGGET = ITEMS.register("titanium_nugget", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TAB_POTATOSMPX)));
    public static final RegistryObject<Item> RAW_TITANIUM = ITEMS.register("raw_titanium", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TAB_POTATOSMPX)));

    public static final RegistryObject<Item> MANA_CRYSTAL = ITEMS.register("mana_crystal", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TAB_POTATOSMPX)));
    public static final RegistryObject<Item> COMPRESSED_MANA_SHARD = ITEMS.register("compressed_mana_shard", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TAB_POTATOSMPX)));
    public static final RegistryObject<Item> MASTER_CRYSTAL = ITEMS.register("master_crystal", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TAB_POTATOSMPX)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
