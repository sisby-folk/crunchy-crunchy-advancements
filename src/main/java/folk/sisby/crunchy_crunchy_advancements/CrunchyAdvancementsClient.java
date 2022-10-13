package folk.sisby.crunchy_crunchy_advancements;

import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class CrunchyAdvancementsClient implements ClientModInitializer {

	public static final Logger CRUNCHY_LOGGER_CLIENT = LoggerFactory.getLogger("[Client] Crunchy Crunchy Advancements");

	/*
	* From TomB-134/MinimalMenu (MIT)
	 */
	public static boolean buttonMatchesKey (ClickableWidget button, String key) {
		Text buttonMessage = button.getMessage();
		Text keyMessage = Text.translatable(key);
		return Objects.equals(buttonMessage, keyMessage);
	}

	@Override
	public void onInitializeClient(ModContainer mod) {
		CrunchyConfig.touch();
		CRUNCHY_LOGGER_CLIENT.info("Initialised!");
	}
}
