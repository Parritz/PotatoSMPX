package perhaps.potatosmpx;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import perhaps.potatosmpx.block.ModBlocks;
import perhaps.potatosmpx.client.KeyInit;
import perhaps.potatosmpx.enchantment.ModEnchantments;
import perhaps.potatosmpx.item.ModItems;

@Mod(Potatosmpx.MOD_ID)
public class Potatosmpx {
    public static final String MOD_ID = "potatosmpx";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Potatosmpx() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModEnchantments.register(eventBus);
        KeyInit.init();

        eventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Setting up Potato SMP X mod!");
    }
}
