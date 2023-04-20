package perhaps.potatosmpx.enchantment.enchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Map;

public class BeheadingEnchantment extends Enchantment {
    public BeheadingEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinCost(int level) {
        return level * 12;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 50;
    }

    private final Map<EntityType<?>, Item> entityHeads = Map.of(
            EntityType.ZOMBIE, Items.ZOMBIE_HEAD,
            EntityType.SKELETON, Items.SKELETON_SKULL,
            EntityType.WITHER_SKELETON, Items.WITHER_SKELETON_SKULL,
            EntityType.CREEPER, Items.CREEPER_HEAD
    );

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        Entity target = event.getEntityLiving();
        Entity attacker = event.getSource().getDirectEntity();
        BlockPos pos = target.blockPosition();
        if (!(attacker instanceof Player player)) return;

        Level world = player.level;
        ItemStack heldItem = player.getMainHandItem();
        int level = EnchantmentHelper.getItemEnchantmentLevel(this, heldItem);
        if (level <= 0 || world.isClientSide || heldItem.isEmpty()) return;

        EntityType<?> entityType = target.getType();
        if (!entityHeads.containsKey(entityType) || world.getRandom().nextFloat() > (0.05f * level)) return;

        ItemStack entityHeadStack = new ItemStack(entityHeads.get(entityType));
        ItemEntity headEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, entityHeadStack);
        world.addFreshEntity(headEntity);
    }
}
