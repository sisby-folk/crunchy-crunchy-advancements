package folk.sisby.crunchy_crunchy_advancements;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class CrunchyAdvancements implements ModInitializer {
	public static final String ID = "crunchy_crunchy_advancements";
	@SuppressWarnings("deprecation")
	public static final CrunchyConfig CONFIG = CrunchyConfig.createToml(FabricLoader.getInstance().getConfigDirectory().toPath(), "", ID, CrunchyConfig.class);

	@Override
	public void onInitialize() {
	}
}
