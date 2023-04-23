package perhaps.potatosmpx.perks;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.Random;

public class MinersInstinct {
	public MinersInstinct() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
		event.setNewSpeed(event.getOriginalSpeed() * 2);
	}
}
