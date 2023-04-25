package perhaps.potatosmpx.api.config;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CropHandler {
    public static boolean isSeed(Item item) {
        return WeightedItems.cropSeeds.contains(item);
    }

    public static boolean isCrop(Block block) {return block instanceof CropBlock || block instanceof NetherWartBlock;}
    public static boolean isCropItem(Item item) {
        return item == Items.WHEAT || item == Items.CARROT || item == Items.POTATO ||
                item == Items.BEETROOT || item == Items.NETHER_WART;
    }

    public static int getAge(BlockState state) {
        Block block = state.getBlock();
        if (block instanceof CropBlock) {
            CropBlock cropBlock = (CropBlock) state.getBlock();
            return state.getValue(cropBlock.getAgeProperty());
        } else if (block instanceof NetherWartBlock) {
            return state.getValue(NetherWartBlock.AGE);
        }

        return 0;
    }

    public static boolean isMaxAge(BlockState state) {
        Block block = state.getBlock();
        if (block instanceof CropBlock) {
            CropBlock cropBlock = (CropBlock) state.getBlock();
            return cropBlock.isMaxAge(state);
        } else if (block instanceof NetherWartBlock) {
            return state.getValue(NetherWartBlock.AGE) == 3;
        }

        return false;
    }
}
