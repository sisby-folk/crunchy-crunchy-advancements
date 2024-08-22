package folk.sisby.crunchy_crunchy_advancements;

import net.fabricmc.loader.api.FabricLoader;

public class CrunchyAdvancements {
	public static final String ID = "crunchy_crunchy_advancements";
	public static final CrunchyConfig CONFIG = CrunchyConfig.createToml(FabricLoader.getInstance().getConfigDir(), "", ID, CrunchyConfig.class);
}
