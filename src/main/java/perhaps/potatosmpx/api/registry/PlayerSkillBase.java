package perhaps.potatosmpx.api.registry;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public enum PlayerSkillBase {;
    public static int getLuck(Player player) {
        return 100;
    }

    public static double getAdjustedDropRate(Player player, double baseDropRate) {
        double luck = getLuck(player) / 100.0;
        double adjustedDropRate = baseDropRate * (1.0 + luck);
        return Math.min(adjustedDropRate, 1.0);
    }
}
