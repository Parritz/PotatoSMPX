package perhaps.potatosmpx.enchantment.enchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
import perhaps.potatosmpx.enchantment.ModEnchantments;

import java.util.List;

public class BountifulHarvestEnchantment extends Enchantment {
    public BountifulHarvestEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public int getMinCost(int level) { return EnchantmentRarity.COMMON.getMinCost(level); }
    @Override
    public int getMaxCost(int level) { return EnchantmentRarity.COMMON.getMaxCost(level); }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Level world = player.level;
        ItemStack heldItem = player.getMainHandItem();
        if (heldItem.isEmpty()) return;

        int level = EnchantmentHelper.getItemEnchantmentLevel(this, heldItem);
        int replenishLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.REPLENISH.get(), heldItem);
        if (level <= 0 || world.isClientSide || replenishLevel >= 1) return;

        BlockState state = event.getState();
        Block block = state.getBlock();

        // Check if the block is a CropBlock and fully grown
        if (!(block instanceof CropBlock cropBlock) || !cropBlock.isMaxAge((state))) return;

        List<ItemStack> blockDrops = Block.getDrops(state, (ServerLevel) world, event.getPos(), null, player, heldItem);

        // Increase drops by 25% per level
        for (ItemStack drop : blockDrops) {
            if (drop.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof CropBlock) continue; // Skip any seeds
            int additionalCount = (int) Math.floor(drop.getCount() * level);
            drop.setCount(drop.getCount() + additionalCount);
        }

        // Cancel the default block breaking event and drop the modified drops
        event.setCanceled(true);
        BlockPos pos = event.getPos();
        for (ItemStack drop : blockDrops) {
            if (drop.getCount() > 0) {
                ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop);
                world.addFreshEntity(itemEntity);
            }
        }

        world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
    }
}
