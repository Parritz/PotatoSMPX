package perhaps.potatosmpx.api.onDrop;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.world.BlockEvent;
import perhaps.potatosmpx.api.registry.EnchantmentBase;

import javax.annotation.Nullable;
import java.util.List;

public class OnDrop {
    public static void handleDrops(@Nullable BlockEvent.BreakEvent breakEvent, @Nullable LivingDropsEvent livingDropsEvent, @Nullable List<ItemStack> drops) {
        if (breakEvent != null) {
            Player player = breakEvent.getPlayer();
            Level playerWorld = player.level;
            ServerLevel serverWorld = (ServerLevel) playerWorld;
            BlockPos pos = breakEvent.getPos();
            ItemStack heldItem = player.getMainHandItem();
            if (drops == null) drops = Block.getDrops(breakEvent.getState(), serverWorld, pos, null, player, heldItem);

            int magnetismLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentBase.MAGNETISM.get(), heldItem);
            int blockX = (int) (pos.getX() + 0.5);
            int blockY = (int) (pos.getY() + 0.5);
            int blockZ = (int) (pos.getZ() + 0.5);

            for (ItemStack itemDrop : drops) {
                if (!(itemDrop.getCount() > 0)) continue;
                if (magnetismLevel > 0) {
                    player.getInventory().add(itemDrop);
                    continue;
                }
                ItemEntity itemEntity = new ItemEntity(serverWorld, blockX, blockY, blockZ, itemDrop);
                serverWorld.addFreshEntity(itemEntity);
            }
        } else if (livingDropsEvent != null) {
            System.out.println("LivingDrops event not implemented.");
            // Isn't a block
            // soon:tm:
        } else {
            System.out.println("No event provided!");
        }
    }
}
