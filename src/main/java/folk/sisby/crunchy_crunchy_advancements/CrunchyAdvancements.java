package folk.sisby.crunchy_crunchy_advancements;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrunchyAdvancements {
	public static final String ID = "crunchy_crunchy_advancements";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);
	public static final CrunchyConfig CONFIG = CrunchyConfig.createToml(FabricLoader.getInstance().getConfigDir(), "", ID, CrunchyConfig.class);
}
