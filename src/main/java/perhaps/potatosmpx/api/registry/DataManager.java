package perhaps.potatosmpx.api.registry;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DataManager implements ICapabilityProvider, INBTSerializable<CompoundTag> {
	public static final Capability<DataManager> DATA_CAPABILITY = null;

	private int testing = 0;

	public int getTesting() {
		return testing;
	}

	public void setTesting(int testing) {
		this.testing = testing;
	}

	public void increaseTesting() {
		testing++;
	}

	public void decreaseTesting() {
		testing--;
	}

	@NotNull
	@Override
	public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (cap == DATA_CAPABILITY) {
			return LazyOptional.of(() -> (T) this);
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		nbt.putInt("testing", testing);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		testing = nbt.getInt("testing");
	}
}