package perhaps.potatosmpx.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import perhaps.potatosmpx.api.registry.DataManager;

// This class is for testing purposes
public class Abilities {
	public static void init(IEventBus eventBus) {
		eventBus.register(new Abilities());
	}
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (!KeyInit.abilitiesKeyMapping.isDown()) return;
		Player player = Minecraft.getInstance().player;
		LazyOptional<DataManager> data = player.getCapability(DataManager.DATA_CAPABILITY);
		System.out.println("CHECK 1");
		if (data.isPresent()) {
			DataManager manager = data.orElseThrow(NullPointerException::new);
			int currentTesting = manager.getTesting();
			System.out.println(currentTesting);
		} else {
			DataManager newManager = new DataManager();
			player.getCapability(DataManager.DATA_CAPABILITY).orElseThrow(NullPointerException::new).deserializeNBT(newManager.serializeNBT());
		}
	}
}
