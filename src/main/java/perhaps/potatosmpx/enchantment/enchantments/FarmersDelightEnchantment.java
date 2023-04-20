package perhaps.potatosmpx.enchantment.enchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import perhaps.potatosmpx.enchantment.EnchantmentRarity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FarmersDelightEnchantment extends Enchantment {
    public FarmersDelightEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int level) {
        return EnchantmentRarity.GODLY.getMinCost(level);
    }

    @Override
    public int getMaxCost(int level) { return EnchantmentRarity.GODLY.getMaxCost(level); }

    private final Map<Item, Double> rareDrops = Map.of(
            Items.COAL, 0.25,
            Items.IRON_INGOT, 0.3,
            Items.GOLD_INGOT, 0.25,
            Items.REDSTONE, 0.2,
            Items.LAPIS_LAZULI, 0.15,
            Items.DIAMOND, 0.1,
            Items.EMERALD, 0.08,
            Items.NETHERITE_SCRAP, 0.06
    );

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Level world = player.level;
        ItemStack heldItem = player.getMainHandItem();
        if (heldItem.isEmpty()) return;

        int level = EnchantmentHelper.getItemEnchantmentLevel(this, heldItem);
        if (level <= 0 || world.isClientSide) return;

        BlockState state = event.getState();
        Block block = state.getBlock();

        // Check if the block is a CropBlock and fully grown
        if (!(block instanceof CropBlock cropBlock) || !cropBlock.isMaxAge(state)) return;

        if (world.getRandom().nextFloat() > (0.05f * level)) return;

        Item selectedItem = null;
        double randomValue = world.getRandom().nextDouble();

        for (Map.Entry<Item, Double> entry : rareDrops.entrySet()) {
            if (randomValue < entry.getValue()) {
                selectedItem = entry.getKey();
                break;
            }
            randomValue -= entry.getValue();
        }

        // Create an ItemStack with the random ore
        ItemStack oreStack = new ItemStack(selectedItem);
        oreStack.setCount(1);

        // Spawn the ore as an item entity in the world
        BlockPos pos = event.getPos();
        double posX = pos.getX() + 0.5;
        double posY = pos.getY() + 0.5;
        double posZ = pos.getZ() + 0.5;
        ItemEntity oreEntity = new ItemEntity(world, posX, posY, posZ, oreStack);
        world.addFreshEntity(oreEntity);
    }
}
