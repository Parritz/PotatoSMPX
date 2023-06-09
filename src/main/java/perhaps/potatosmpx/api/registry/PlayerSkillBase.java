package perhaps.potatosmpx.api.registry;

import net.minecraft.world.entity.player.Player;

import java.util.Random;

public class PlayerSkillBase {
    public static int getLuck(Player player) {
        return 500;
    }

    public static boolean willRunEnchantment(Player player, float baseChance, int currentLevel) {
        float runChance = baseChance * currentLevel * (getLuck(player) / 100.0f);

        Random random = new Random();
        return random.nextFloat() <= runChance;
    }
}
