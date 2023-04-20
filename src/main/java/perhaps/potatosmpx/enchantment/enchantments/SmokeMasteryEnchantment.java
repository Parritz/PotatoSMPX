package perhaps.potatosmpx.enchantment.enchantments;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
        return level * 16;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 50;
    }

    private final Map<Item, ItemStack> recipeCache = new HashMap<>();

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        DamageSource source = event.getSource();
        Entity attacker = source.getDirectEntity();
        Collection<ItemEntity> entityDrops = event.getDrops();
        if (!(attacker instanceof Player player)) return;

        Level world = player.level;
        ItemStack heldItem = player.getMainHandItem();
        int level = EnchantmentHelper.getItemEnchantmentLevel(this, heldItem);
        if (level < 0 || world.isClientSide || heldItem.isEmpty()) return;

        for (ItemEntity itemEntity : entityDrops) {
            ItemStack drop = itemEntity.getItem();
            Item dropItem = drop.getItem();

            ItemStack result;
            if (recipeCache.containsKey(dropItem)) {
                result = recipeCache.get(dropItem);
            } else {
                SimpleContainer itemContainer = new SimpleContainer(drop);
                Optional<SmokingRecipe> optional = world.getRecipeManager().getRecipeFor(RecipeType.SMOKING, itemContainer, world);

                if (optional.isPresent()) {
                    SmokingRecipe smokingRecipe = optional.get();
                    result = smokingRecipe.getResultItem().copy();
                    recipeCache.put(dropItem, result);
                } else {
                    result = null;
                }
            }

            if (result == null) return;
            ItemStack copyItem = result.copy();
            itemEntity.setItem(copyItem);

            ItemStack tempItem = itemEntity.getItem();
            int count = tempItem.getCount();
            if (world.getRandom().nextFloat() > (0.05f * level)) return;

            int extraDupe = world.getRandom().nextFloat() > (0.05f * level) ? 4 : 2;
            if (extraDupe == 4 && (world.getRandom().nextFloat() > 0.05f * level)) extraDupe = 8;
            tempItem.setCount(count * extraDupe);
        }
    }
}
