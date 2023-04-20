package perhaps.potatosmpx.enchantment.enchantments;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.system.CallbackI;

import java.util.List;
import java.util.Map;

public class ReplenishEnchantment extends Enchantment {
    public ReplenishEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
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

    private final Map<Block, Item> seedBlocks = Map.of(
            Blocks.WHEAT, Items.WHEAT_SEEDS,
            Blocks.CARROTS, Items.CARROT,
            Blocks.POTATOES, Items.POTATO,
            Blocks.BEETROOTS, Items.BEETROOT_SEEDS,
            Blocks.NETHER_WART, Items.NETHER_WART
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
        List<ItemStack> drops = Block.getDrops(state, (ServerLevel) world, event.getPos(), null, player, heldItem);
        if (!seedBlocks.containsKey(block)) return;

        boolean hasSeed = false;
        Item seed = seedBlocks.get(block);
        for (ItemStack drop : drops) {
            System.out.println("checking....");
            if (drop.getItem() != seed) continue;
            drops.remove(drop);
            hasSeed = true;
            break;
        }

        // TODO: This doesn't work yet
        if (hasSeed) world.setBlock(event.getPos(), block.defaultBlockState(), 3);
    }
}
