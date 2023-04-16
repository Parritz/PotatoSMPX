package perhaps.potatosmpx.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab TAB_POTATOSMPX = new CreativeModeTab("potatosmpx") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.WOOD_WAND.get());
        }
    };
}
