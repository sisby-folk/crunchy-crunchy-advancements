package folk.sisby.crunchy_crunchy_advancements.mixin.client;

import folk.sisby.crunchy_crunchy_advancements.CrunchyAdvancements;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public abstract class MixinGameMenuScreen extends Screen {
	protected MixinGameMenuScreen(Text title) {
		super(title);
	}

	@Inject(method = "initWidgets", at = @At("TAIL"))
	private void hideAdvancements(CallbackInfo ci) {
		if (CrunchyAdvancements.CONFIG.removeAdvancementsButton) {
			Element advancements = null;
			for (Element child : children()) {
				if (child instanceof ButtonWidget) {
					if (((ButtonWidget) child).getMessage().equals(new TranslatableText("gui.advancements"))) {
						advancements = child;
					} else if (((ButtonWidget) child).getMessage().equals(new TranslatableText("gui.stats"))) {
						((ButtonWidget) child).setWidth(204);
						((ButtonWidget) child).x = width / 2 - 204 / 2;
					}
				}
			}
			if (advancements != null) {
				remove(advancements);
			}
		}
	}
}
