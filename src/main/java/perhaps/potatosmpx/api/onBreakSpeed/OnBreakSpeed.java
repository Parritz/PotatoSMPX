package perhaps.potatosmpx.api.onBreakSpeed;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;
import perhaps.potatosmpx.api.config.CropHandler;
import perhaps.potatosmpx.api.config.EnchantmentData;
import perhaps.potatosmpx.api.config.EnchantmentFunction;
import perhaps.potatosmpx.api.registry.EnchantmentBase;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static perhaps.potatosmpx.api.config.EnchantmentData.onBlockBreakEnchantments;
import static perhaps.potatosmpx.api.config.EnchantmentData.onBreakSpeedEnchantments;

public class OnBreakSpeed {
    private static final Map<Enchantment, EnchantmentData> enchantmentMapPriority = new HashMap<>();
    private static final Map<Enchantment, EnchantmentData> enchantmentMap = new HashMap<>();
    private static final Map<Enchantment, EnchantmentData> combinedMap = new LinkedHashMap<>();

    public static void listenBreakSpeed(PlayerEvent.BreakSpeed breakSpeed) {
        Player player = breakSpeed.getPlayer();
        Level playerWorld = player.level;
        ItemStack heldItem = player.getMainHandItem();
        if (heldItem.isEmpty()) return;
        if (enchantmentMap.isEmpty()) {
            for (EnchantmentData data : onBreakSpeedEnchantments) {
                int priority = data.getPriority();
                if (priority == 0) continue;

                Enchantment enchantment = data.getEnchantment();

                if (priority == 1) {
                    enchantmentMapPriority.put(enchantment, data);
                } else {
                    enchantmentMap.put(enchantment, data);
                }
            }

            combinedMap.putAll(enchantmentMapPriority);
            combinedMap.putAll(enchantmentMap);
        }

        BlockState state = breakSpeed.getState();
        Block block = state.getBlock();
        BlockPos pos = breakSpeed.getPos();

        int enchantmentsPassed = 0;
        for (Map.Entry<Enchantment, EnchantmentData> entry : combinedMap.entrySet()) {
            Enchantment enchantment = entry.getKey();
            EnchantmentData data = entry.getValue();

            int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(enchantment, heldItem);
            if (enchantmentLevel == 0) continue;

            List<Class<? extends Block>> ValidBlocks = data.getValidBlocks();

            boolean isValidBlock = ValidBlocks.isEmpty();
            for (Class<? extends Block> validBlockClass : ValidBlocks) {
                if (validBlockClass.isInstance(block)) {
                    isValidBlock = true;
                    break;
                }
            }

            if (!isValidBlock) continue;
            enchantmentsPassed++;

            EnchantmentFunction function = data.getFunction();
            function.apply(breakSpeed, enchantmentLevel, heldItem, state, block, null, playerWorld, pos, player);
        }
    }
}
