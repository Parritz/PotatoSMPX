package perhaps.potatosmpx.enchantment.enchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;

import static net.minecraft.world.level.block.Block.getDrops;

@Mod.EventBusSubscriber
public class SmokeMasteryEnchantment extends Enchantment {
    public SmokeMasteryEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
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

    private Collection<ItemEntity> latestDrops;
    private final Map<EntityType<?>, Item> uncookedEntities = Map.of(
            EntityType.COW, Items.BEEF,
            EntityType.SHEEP, Items.MUTTON,
            EntityType.PIG, Items.PORKCHOP,
            EntityType.CHICKEN, Items.CHICKEN
    );
    private final Map<EntityType<?>, Item> cookedEntities = Map.of(
            EntityType.COW, Items.COOKED_BEEF,
            EntityType.SHEEP, Items.COOKED_MUTTON,
            EntityType.PIG, Items.COOKED_PORKCHOP,
            EntityType.CHICKEN, Items.COOKED_CHICKEN
    );

    @Override
    public void doPostAttack(@NotNull LivingEntity attacker, @NotNull Entity target, int level) {
        if (attacker.level.isClientSide) return;
        ServerLevel world = ((ServerLevel) attacker.level);
        BlockPos pos = target.blockPosition();
        EntityType<?> entityType = target.getType();

        if (!(target instanceof LivingEntity && ((LivingEntity) target).getHealth() <= 0)) return;
        if (!(attacker instanceof Player)) return;
        if (!(cookedEntities.containsKey(entityType) && world.getRandom().nextFloat() <= (0.10f * level))) return;

        ItemStack cookedMeatStack = new ItemStack(cookedEntities.get(entityType));
        ItemEntity cookedMeatEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, cookedMeatStack);
        world.addFreshEntity(cookedMeatEntity);
        for (ItemEntity drop : latestDrops) {
            if (drop.getItem().getItem() == uncookedEntities.get(entityType)) {
                drop.setRemoved(Entity.RemovalReason.DISCARDED);
            }
        }
    }

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        EntityType<?> entityType = event.getEntityLiving().getType();
        if (uncookedEntities.containsKey(entityType))
            latestDrops = event.getDrops();
    }
}
