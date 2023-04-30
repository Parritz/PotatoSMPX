package perhaps.potatosmpx.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.common.MinecraftForge;
import perhaps.potatosmpx.Potatosmpx;

public class KeyInit {
	private KeyInit() {

	}

	public static KeyMapping abilitiesKeyMapping;

	public static void init() {
		abilitiesKeyMapping = registerKey("Abilities", KeyMapping.CATEGORY_GAMEPLAY, InputConstants.KEY_U);
	}

	private static KeyMapping registerKey(String name, String category, int keyCode) {
		final var key = new KeyMapping("key." + Potatosmpx.MOD_ID + "." + name, keyCode, category);
		ClientRegistry.registerKeyBinding(key);
		return key;
	}
}
