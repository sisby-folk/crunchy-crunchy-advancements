package folk.sisby.crunchy_crunchy_advancements;

import folk.sisby.kaleido.api.KaleidoConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("deprecation")
public class CrunchyAdvancements implements ModInitializer {
	public static final String ID = "crunchy_crunchy_advancements";
	public static final Logger CRUNCHY_LOGGER = LoggerFactory.getLogger("Crunchy Crunchy Advancements");
	public static final CrunchyConfig CONFIG = KaleidoConfig.createToml(FabricLoader.getInstance().getConfigDir(), ID, "config", CrunchyConfig.class);

	@Override
	public void onInitialize() {
		CRUNCHY_LOGGER.info("[Crunchy Crunchy Advancements] Initialised!");
	}
}
