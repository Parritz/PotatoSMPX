package perhaps.potatosmpx.api.config;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Map;

public class ConfigCommon {
    public static final String MOMENTUM_TAG = "momentum_mined_blocks";
    public static final int MOMENTUM_MAX_CONSECUTIVE_BLOCKS = 20;
    public static Map<Item, Item> cropCompressorDrops = Map.of(
            Items.WHEAT, Items.HAY_BLOCK
    );

    public static Map<Item, Item> composterDrops = Map.of(
            Items.WHEAT_SEEDS, Items.BONE_BLOCK,
            Items.BEETROOT_SEEDS, Items.BONE_BLOCK,
            Items.PUMPKIN_SEEDS, Items.BONE_BLOCK,
            Items.MELON_SEEDS, Items.BONE_BLOCK
    );

    public static final ForgeConfigSpec.ConfigValue<Integer> MINERS_INSTINCT_BASELEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> MINERS_INSTINCT_MAXLEVEL;

    public static final ForgeConfigSpec.ConfigValue<Integer> WARRIORS_MIGHT_BASELEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> WARRIORS_MIGHT_MAXLEVEL;

    public static final ForgeConfigSpec.ConfigValue<Integer> ARCHERS_PRECISION_BASELEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> ARCHERS_PRECISION_MAXLEVEL;

    public static final ForgeConfigSpec.ConfigValue<Integer> FARMERS_BOUNTY_BASELEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> FARMERS_BOUNTY_MAXLEVEL;

    public static final ForgeConfigSpec.ConfigValue<Integer> ALCHEMISTS_WISDOM_BASELEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> ALCHEMISTS_WISDOM_MAXLEVEL;

    public static final ForgeConfigSpec.ConfigValue<Integer> WIZARDS_SORCERY_BASELEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> WIZARDS_SORCERY_MAXLEVEL;

    public static final ForgeConfigSpec.ConfigValue<Integer> MINING_BASELEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> MINING_MAXLEVEL;

    public static final ForgeConfigSpec.ConfigValue<Integer> COMBAT_BASELEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> COMBAT_MAXLEVEL;

    public static final ForgeConfigSpec.ConfigValue<Integer> ARCHERY_BASELEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> ARCHERY_MAXLEVEL;

    public static final ForgeConfigSpec.ConfigValue<Integer> FARMING_BASELEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> FARMING_MAXLEVEL;

    public static final ForgeConfigSpec.ConfigValue<Integer> ALCHEMY_BASELEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> ALCHEMY_MAXLEVEL;

    public static final ForgeConfigSpec.ConfigValue<Integer> WIZARDRY_BASELEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> WIZARDRY_MAXLEVEL;

    static {
        BUILDER.push("PotatoSMPX Config");

        MINERS_INSTINCT_BASELEVEL = BUILDER.define("Miners Instinct Base Level", new Random().nextInt(1, 4));
        MINERS_INSTINCT_MAXLEVEL = BUILDER.define("Miners Instinct Max Level", 10);

        WARRIORS_MIGHT_BASELEVEL = BUILDER.define("Warriors Might Base Level", new Random().nextInt(1, 4));
        WARRIORS_MIGHT_MAXLEVEL = BUILDER.define("Warriors Might Max Level", 10);

        ARCHERS_PRECISION_BASELEVEL = BUILDER.define("Archers Precision Base Level", new Random().nextInt(1, 4));
        ARCHERS_PRECISION_MAXLEVEL = BUILDER.define("Archers Precision Max Level", 10);

        FARMERS_BOUNTY_BASELEVEL = BUILDER.define("Farmers Bounty Base Level", new Random().nextInt(1, 4));
        FARMERS_BOUNTY_MAXLEVEL = BUILDER.define("Farmers Bounty Max Level", 10);

        ALCHEMISTS_WISDOM_BASELEVEL = BUILDER.define("Alchemists Wisdom Base Level", new Random().nextInt(1, 4));
        ALCHEMISTS_WISDOM_MAXLEVEL = BUILDER.define("Alchemists Wisdom Max Level", 10);

        WIZARDS_SORCERY_BASELEVEL = BUILDER.define("Wizards Sorcery Base Level", new Random().nextInt(1, 4));
        WIZARDS_SORCERY_MAXLEVEL = BUILDER.define("Wizards Sorcery Max Level", 10);

        MINING_BASELEVEL = BUILDER.define("Mining Base Level", new Random().nextInt(1, 4));
        MINING_MAXLEVEL = BUILDER.define("Mining Max Level", 10);

        COMBAT_BASELEVEL = BUILDER.define("Combat Base Level", new Random().nextInt(1, 4));
        COMBAT_MAXLEVEL = BUILDER.define("Combat Max Level", 10);

        ARCHERY_BASELEVEL = BUILDER.define("Archery Base Level", new Random().nextInt(1, 4));
        ARCHERY_MAXLEVEL = BUILDER.define("Archery Max Level", 10);

        FARMING_BASELEVEL = BUILDER.define("Farming Base Level", new Random().nextInt(1, 4));
        FARMING_MAXLEVEL = BUILDER.define("Farming Max Level", 10);

        ALCHEMY_BASELEVEL = BUILDER.define("Alchemy Base Level", new Random().nextInt(1, 4));
        ALCHEMY_MAXLEVEL = BUILDER.define("Alchemy Max Level", 10);

        WIZARDRY_BASELEVEL = BUILDER.define("Wizardry Base Level", new Random().nextInt(1, 4));
        WIZARDRY_MAXLEVEL = BUILDER.define("Wizardry Max Level", 10);

        BUILDER.pop();
    }
}
