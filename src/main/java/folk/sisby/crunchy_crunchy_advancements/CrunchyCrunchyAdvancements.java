package folk.sisby.crunchy_crunchy_advancements;

import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class CrunchyCrunchyAdvancements implements ClientModInitializer {

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

	}
}
