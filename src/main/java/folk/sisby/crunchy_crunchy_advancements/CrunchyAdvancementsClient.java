package folk.sisby.crunchy_crunchy_advancements;

import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrunchyAdvancementsClient implements ClientModInitializer {

	public static final Logger CRUNCHY_LOGGER_CLIENT = LoggerFactory.getLogger("[Client] Crunchy Crunchy Advancements");

	/*
	* From TomB-134/MinimalMenu (MIT)
	 */
	public static boolean buttonMatchesKey(ClickableWidget button, String key) {
		Text buttonMessage = button.getMessage();
		if (buttonMessage instanceof TranslatableText) {
			String buttonKey = ((TranslatableText) buttonMessage).getKey();
			if (buttonKey.equals(key)) {
				return true;
			}
			Object[] textArgs = ((TranslatableText) buttonMessage).getArgs();
			for (Object arg : textArgs) {
				if (arg instanceof TranslatableText) {
					String argKey = ((TranslatableText) arg).getKey();
					if (argKey.equals(key)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void onInitializeClient(ModContainer mod) {
		CrunchyConfig.touch();
		CRUNCHY_LOGGER_CLIENT.info("Initialised!");
	}
}
