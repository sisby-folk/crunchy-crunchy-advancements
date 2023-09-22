package folk.sisby.crunchy_crunchy_advancements;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("deprecation")
public class CrunchyAdvancements {
	public static final String ID = "crunchy_crunchy_advancements";
	public static final Logger CRUNCHY_LOGGER = LoggerFactory.getLogger("Crunchy Crunchy Advancements");
	public static final CrunchyConfig CONFIG = CrunchyConfig.createToml(FabricLoader.getInstance().getConfigDir(), ID, "config", CrunchyConfig.class);
}
