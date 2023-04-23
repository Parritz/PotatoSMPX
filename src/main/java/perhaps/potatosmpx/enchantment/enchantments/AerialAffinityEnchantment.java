package perhaps.potatosmpx.enchantment.enchantments;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import perhaps.potatosmpx.enchantment.EnchantmentRarity;
import perhaps.potatosmpx.enchantment.ItemUtils;
import perhaps.potatosmpx.enchantment.ModEnchantments;

import java.util.Random;

public class AerialAffinityEnchantment extends Enchantment {
    public AerialAffinityEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinCost(int level) { return EnchantmentRarity.RARE.getMinCost(level); }
    @Override
    public int getMaxCost(int level) { return EnchantmentRarity.RARE.getMaxCost(level); }

    @Override
    public boolean canEnchant(ItemStack stack) {
        Item item = stack.getItem();
        return ItemUtils.isItemAllowed(item, Items.ELYTRA);
    }

    private static final float BASE_FLIGHT_SPEED = 0.05f;
    private static final float SPEED_BOOST_PER_LEVEL = 0.02f;
}
