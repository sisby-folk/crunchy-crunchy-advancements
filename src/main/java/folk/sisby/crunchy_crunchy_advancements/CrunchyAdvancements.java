package folk.sisby.crunchy_crunchy_advancements;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.config.QuiltConfig;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrunchyAdvancements implements ModInitializer {
	public static final String ID = "crunchy_crunchy_advancements";
	public static final Logger CRUNCHY_LOGGER = LoggerFactory.getLogger("Crunchy Crunchy Advancements");
	public static final CrunchyConfig CONFIG = QuiltConfig.create(ID, "config", CrunchyConfig.class);

	@Override
	public void onInitialize(ModContainer mod) {
		CRUNCHY_LOGGER.info("[Crunchy Crunchy Advancements] Initialised!");
	}
}
