package perhaps.potatosmpx.enchantment;

import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentUtils {
    public static boolean isEnchantmentAllowed(Enchantment inputEnchantment, Enchantment... disallowedEnchantments) {
        for (Enchantment disallowedEnchantment : disallowedEnchantments) {
            if (inputEnchantment == disallowedEnchantment) {
                return false;
            }
        }
        return true;
    }
}