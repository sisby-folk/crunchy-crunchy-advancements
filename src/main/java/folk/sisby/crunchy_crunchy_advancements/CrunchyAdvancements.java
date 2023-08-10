package folk.sisby.crunchy_crunchy_advancements;

import net.fabricmc.api.ModInitializer;
import org.quiltmc.config.impl.ConfigImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;

@SuppressWarnings("deprecation")
public class CrunchyAdvancements implements ModInitializer {
	public static final String ID = "crunchy_crunchy_advancements";
	public static final Logger CRUNCHY_LOGGER = LoggerFactory.getLogger("Crunchy Crunchy Advancements");
	public static final CrunchyConfig CONFIG = ConfigImpl.create(QuiltifiedFabricConfig.ENV, ID,"config",  Paths.get(""), b -> {}, CrunchyConfig.class, b -> {});

	@Override
	public void onInitialize() {
		CRUNCHY_LOGGER.info("[Crunchy Crunchy Advancements] Initialised!");
	}
}
