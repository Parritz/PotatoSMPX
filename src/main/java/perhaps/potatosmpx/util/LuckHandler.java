package perhaps.potatosmpx.util;

import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public enum LuckHandler {;
    public static final double maxLuck = 5.0;
    public static <T> Map<T, Double> getAdjustedWeights(Map<T, Double> inputWeights, double playerLuck) {
        double totalWeight = inputWeights.values().stream().mapToDouble(Double::doubleValue).sum();
        double averageWeight = totalWeight / inputWeights.size();

        Map<T, Double> adjustedDrops = new HashMap<>();
        double maxLuck = 5.0; // 500%

        for (Map.Entry<T, Double> entry : inputWeights.entrySet()) {
            double originalWeight = entry.getValue();
            double fraction = (playerLuck - 1.0) / (maxLuck - 1.0);
            double distance = averageWeight - originalWeight;
            double adjustedWeight = originalWeight + distance * fraction;
            adjustedDrops.put(entry.getKey(), adjustedWeight);
        }

        return adjustedDrops;
    }

    public static <T> double getTotalWeight(Map<T, Double> adjustedDrops) {
        return adjustedDrops.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    public static <T> T getResultEntry(Level playerWorld, double totalWeight, Map<T, Double> adjustedDrops) {
        double randomValue = playerWorld.getRandom().nextDouble() * totalWeight;

        for (Map.Entry<T, Double> entry : adjustedDrops.entrySet()) {
            double adjustedDropRate = entry.getValue();
            if (randomValue < adjustedDropRate) {
                return entry.getKey();
            }
            randomValue -= adjustedDropRate;
        }

        return null;
    }
}
