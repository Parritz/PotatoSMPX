package perhaps.potatosmpx.enchantment;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;

public class ItemUtils {
    public static boolean isItemAllowed(Item inputItem, Item... allowedItems) {
        for (Item allowedItem : allowedItems) {
            if (inputItem == allowedItem) {
                return true;
            }
        }
        return false;
    }

    public static boolean isItemTypeAllowed(Item inputItem, Class<?>... allowedItemTypes) {
        for (Class<?> allowedItemType : allowedItemTypes) {
            if (allowedItemType.isInstance(inputItem)) {
                return true;
            }
        }
        return false;
    }
}