package perhaps.potatosmpx.api.data;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DataManager implements ICapabilityProvider, INBTSerializable<CompoundTag> {
	public static final Capability<PlayerAdvantages> PLAYER_ADVANTAGES = CapabilityManager.get(new CapabilityToken<PlayerAdvantages>() { });

	private PlayerAdvantages advantages = null;
	private final LazyOptional<PlayerAdvantages> optional = LazyOptional.of(this::createPlayerAdvantages);

	private PlayerAdvantages createPlayerAdvantages() {
		if (this.advantages == null) {
			this.advantages = new PlayerAdvantages();
		}

		return this.advantages;
	}

	public PlayerAdvantages getPlayerAdvantages() {
		return advantages;
	}

	@NotNull
	@Override
	public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (cap == PLAYER_ADVANTAGES) {
			return optional.cast();
		}

		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		// abc
	}
}