package perhaps.potatosmpx.api.registry;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import perhaps.potatosmpx.Potatosmpx;
import perhaps.potatosmpx.enchantment.enchantments.*;

public class EnchantmentBase {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Potatosmpx.MOD_ID);

    public static RegistryObject<Enchantment> BLAST_MASTERY = ENCHANTMENTS.register("blast_mastery", () -> new BlastMasteryEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> SMOKE_MASTERY = ENCHANTMENTS.register("smoke_mastery", () -> new SmokeMasteryEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> AUTO_SMELT = ENCHANTMENTS.register("auto_smelt", () -> new AutoSmeltEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> BEHEADING = ENCHANTMENTS.register("beheading", () -> new BeheadingEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> ORE_COMPACTOR = ENCHANTMENTS.register("ore_compactor", () -> new OreCompactorEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> THUNDER_STRIKE = ENCHANTMENTS.register("thunder_strike", () -> new ThunderStrikeEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> SATIETY = ENCHANTMENTS.register("satiety", () -> new SatietyEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> SWIFT_STRIKE = ENCHANTMENTS.register("swift_strike", () -> new SwiftStrikeEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> ENDER_MIND = ENCHANTMENTS.register("ender_mind", () -> new EnderMindEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}));
    public static RegistryObject<Enchantment> WISDOM = ENCHANTMENTS.register("wisdom", () -> new WisdomEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> GLIMMERING = ENCHANTMENTS.register("glimmering", () -> new GlimmeringEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR_HEAD, EquipmentSlot.HEAD));
    public static RegistryObject<Enchantment> GOD_OF_THE_SEA = ENCHANTMENTS.register("god_of_the_sea", () -> new GodOfTheSeaEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> GOD_OF_THUNDER = ENCHANTMENTS.register("god_of_thunder", () -> new GodOfThunderEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> HOMING = ENCHANTMENTS.register("homing", () -> new HomingEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> FROZEN_BLADE = ENCHANTMENTS.register("frozen_blade", () -> new FrozenBladeEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> VENOMOUS_STRIKE = ENCHANTMENTS.register("venomous_strike", () -> new VenomousStrikeEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> NOCTURNAL = ENCHANTMENTS.register("nocturnal", () -> new NocturnalEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> ARCTIC_STORM = ENCHANTMENTS.register("arctic_storm", () -> new ArcticStormEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> DIMENSIONAL_RIFT = ENCHANTMENTS.register("dimensional_rift", () -> new DimensionalRiftEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> SOUL_REAPER = ENCHANTMENTS.register("soul_reaper", () -> new SoulReaperEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> TIDAL_WAVE = ENCHANTMENTS.register("tidal_wave", () -> new TidalWaveEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> COLD_BLOODED = ENCHANTMENTS.register("cold_blooded", () -> new ColdBloodedEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> ATOMIC_RESISTANCE = ENCHANTMENTS.register("atomic_resistance", () -> new AtomicResistanceEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> BOUNTIFUL_HARVEST = ENCHANTMENTS.register("bountiful_harvest", () -> new BountifulHarvestEnchantment(Enchantment.Rarity.COMMON, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> NIMBLE_FEET = ENCHANTMENTS.register("nimble_feet", () -> new NimbleFeetEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> QUICK_STRIKE = ENCHANTMENTS.register("quick_strike", () -> new QuickStrikeEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> STONEBREAKER = ENCHANTMENTS.register("stonebreaker", () -> new StoneBreakerEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> LUCKY_STRIKE = ENCHANTMENTS.register("lucky_strike", () -> new LuckyStrikeEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> FARMERS_DELIGHT = ENCHANTMENTS.register("farmers_delight", () -> new FarmersDelightEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> BLAZING_AURA = ENCHANTMENTS.register("blazing_aura", () -> new BlazingAuraEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.ARMOR_HEAD, EquipmentSlot.HEAD));
    public static RegistryObject<Enchantment> AIRBORNE = ENCHANTMENTS.register("airborne", () -> new AirborneEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR_FEET, EquipmentSlot.FEET));

    public static RegistryObject<Enchantment> ARCANE_SHIELD = ENCHANTMENTS.register("arcane_shield", () -> new ArcaneShieldEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.ARMOR_CHEST, EquipmentSlot.CHEST));
    public static RegistryObject<Enchantment> CURSE_OF_MISFORTUNE = ENCHANTMENTS.register("curse_of_misfortune", () -> new CurseOfMisfortuneEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> SHIELD_BREAKER = ENCHANTMENTS.register("shield_breaker", () -> new ShieldBreakerEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> PARALYSIS = ENCHANTMENTS.register("paralysis", () -> new ParalysisEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> POISON_ASPECT = ENCHANTMENTS.register("poison_aspect", () -> new PoisonAspectEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> PUMPKIN_HEAD = ENCHANTMENTS.register("pumpkin_head", () -> new PumpkinHeadEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.ARMOR_HEAD, EquipmentSlot.HEAD));
    public static RegistryObject<Enchantment> PYROMANIA = ENCHANTMENTS.register("pyromania", () -> new PyromaniaEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> REPLENISH = ENCHANTMENTS.register("replenish", () -> new ReplenishEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> RISE = ENCHANTMENTS.register("rise", () -> new RiseEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> SHOTGUN = ENCHANTMENTS.register("shotgun", () -> new ShotgunEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.BOW, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> SNIPER = ENCHANTMENTS.register("sniper", () -> new SniperEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.BOW, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> SUGAR_RUSH = ENCHANTMENTS.register("sugar_rush", () -> new SugarRushEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}));
    public static RegistryObject<Enchantment> TANK = ENCHANTMENTS.register("tank", () -> new TankEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.ARMOR_CHEST, EquipmentSlot.CHEST));
    public static RegistryObject<Enchantment> LIFESTEALER = ENCHANTMENTS.register("lifestealer", () -> new LifestealerEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> MOMENTUM = ENCHANTMENTS.register("momentum", () -> new MomentumEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> QUAKE = ENCHANTMENTS.register("quake", () -> new QuakeEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> DETONATION = ENCHANTMENTS.register("detonation", () -> new DetonationEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BOW, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> ELEMENTAL_MASTERY = ENCHANTMENTS.register("elemental_mastery", () -> new ElementalMasteryEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> DIVINE_BLESSING = ENCHANTMENTS.register("divine_blessing", () -> new DivineBlessingEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}));
    public static RegistryObject<Enchantment> WINDFURY = ENCHANTMENTS.register("windfury", () -> new WindfuryEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> VAMPIRIC_TOUCH = ENCHANTMENTS.register("vampiric_touch", () -> new VampiricTouchEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> DEEP_MINER = ENCHANTMENTS.register("deep_miner", () -> new DeepMinerEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> EARTH_MOVER = ENCHANTMENTS.register("earth_mover", () -> new EarthMoverEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> BOUNTY_HUNTER = ENCHANTMENTS.register("bounty_hunter", () -> new BountyHunterEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> AERIAL_AFFINITY = ENCHANTMENTS.register("aerial_affinity", () -> new AerialAffinityEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR_CHEST, EquipmentSlot.CHEST));
    public static RegistryObject<Enchantment> GREEN_THUMB = ENCHANTMENTS.register("green_thumb", () -> new GreenThumbEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> CROP_COMPRESSOR = ENCHANTMENTS.register("crop_compressor", () -> new CropCompressorEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> MAGNETISM = ENCHANTMENTS.register("magnetism", () -> new MagnetismEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.BREAKABLE, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> COMPOSTER = ENCHANTMENTS.register("composter", () -> new ComposterEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
