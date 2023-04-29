package perhaps.potatosmpx.api.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerAdvantages {
    private Map<String, Map<String, Object>> playerAdvantages;

    private List<String> playerPerks = List.of(
            "Miner's Instinct",
            "Warrior's Might",
            "Archer's Precision",
            "Farmer's Bounty",
            "Alchemists Wisdom",
            "Wizard's Sorcery"
    );

    private List<String> playerSkills = List.of(
            "Mining",
            "Combat",
            "Archery",
            "Farming",
            "Alchemy",
            "Wizardry"
    );

    public PlayerAdvantages() {
        playerAdvantages = new HashMap<>();

        for (String perk : playerPerks) {
            Map<String, Object> innerMap = new HashMap<>();
            innerMap.put("Enabled", false);
            innerMap.put("Type", "Perk");

            playerAdvantages.put(perk, innerMap);
        }

        for (String skill : playerSkills) {
            Map<String, Object> innerMap = new HashMap<>();
            innerMap.put("Level", 0);
            innerMap.put("Type", "Skill");

            playerAdvantages.put(skill, innerMap);
        }
    }

    public Map<String, Map<String, Object>> getAdvantages() {
        return playerAdvantages;
    }

    public void grant(String advantageName) {
        Map<String, Object> advantage = playerAdvantages.get(advantageName);
        if (advantage != null) {
            advantage.put("Enabled", true);
            System.out.println("Granted advantage: " + advantageName);
        } else {
            System.out.println("Advantage not found: " + advantageName);
        }
    }

    public void revoke(String advantageName) {
        Map<String, Object> advantage = playerAdvantages.get(advantageName);
        if (advantage != null) {
            advantage.put("Enabled", false);
            System.out.println("Granted advantage: " + advantageName);
        } else {
            System.out.println("Advantage not found: " + advantageName);
        }
    }

    public void saveNBTData(CompoundTag nbt) {

    }
}
