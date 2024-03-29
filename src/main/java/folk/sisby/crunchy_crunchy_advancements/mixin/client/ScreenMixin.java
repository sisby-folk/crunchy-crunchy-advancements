package folk.sisby.crunchy_crunchy_advancements.mixin.client;

import folk.sisby.crunchy_crunchy_advancements.CrunchyAdvancements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Screen.class)
public abstract class ScreenMixin extends AbstractParentElement {
	@Shadow
	public int width;

	@Shadow
	@Final
	private List<Drawable> drawables;

	@Unique private static boolean buttonMatchesKey(ClickableWidget button, String key) {
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

	@Inject(method = "init*", at = @At("RETURN"))
	@SuppressWarnings("ConstantConditions")
	private void init(MinecraftClient client, int width, int height, CallbackInfo info) {
		if ((Object) this instanceof GameMenuScreen) {
			if (CrunchyAdvancements.CONFIG.removeAdvancementsButton) {
				final int buttonWidth = 204;
				for (Drawable element : this.drawables) {
					if (element instanceof ClickableWidget button) {
						if (buttonMatchesKey(button, "gui.advancements")) {
							button.visible = false;
						}
						if (buttonMatchesKey(button, "gui.stats")) {
							button.setWidth(buttonWidth);
							button.x = this.width / 2 - buttonWidth / 2;
						}
					}
				}
			}
		}
	}
}
