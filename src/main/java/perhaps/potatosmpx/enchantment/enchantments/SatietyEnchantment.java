package perhaps.potatosmpx.enchantment.enchantments;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import perhaps.potatosmpx.enchantment.EnchantmentRarity;

public class SatietyEnchantment extends Enchantment {
    public SatietyEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int level) { return EnchantmentRarity.EPIC.getMinCost(level); }
    @Override
    public int getMaxCost(int level) { return EnchantmentRarity.EPIC.getMaxCost(level); }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        Entity target = event.getEntityLiving();
        Entity attacker = event.getSource().getDirectEntity();
        if (!(attacker instanceof Player player) || target instanceof Animal) return;

        Level world = player.level;
        ItemStack heldItem = player.getMainHandItem();
        int level = EnchantmentHelper.getItemEnchantmentLevel(this, heldItem);
        if (level <= 0 || world.isClientSide || heldItem.isEmpty() || world.getRandom().nextFloat() > (0.10f * level)) return;

        player.getFoodData().eat(3, 0.5f);
    }
}
