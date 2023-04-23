package perhaps.potatosmpx.api.registry;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public enum PlayerSkillBase {;
    public static int getLuck(Player player) {
        return 500;
    }

    public static boolean willRunEnchantment(Player player, float baseChance, int currentLevel) {
        float runChance = baseChance * currentLevel * (getLuck(player) / 100.0f);

        Random random = new Random();
        return random.nextFloat() <= runChance;
    }
}
