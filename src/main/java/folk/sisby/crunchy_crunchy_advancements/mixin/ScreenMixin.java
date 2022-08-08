package folk.sisby.crunchy_crunchy_advancements.mixin;

import folk.sisby.crunchy_crunchy_advancements.CrunchyAdvancementsClient;
import folk.sisby.crunchy_crunchy_advancements.CrunchyConfig;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ScreenMixin extends AbstractParentElement implements Drawable {
	@Shadow
	protected MinecraftClient client;
	@Shadow
	public int width;
	@Shadow
	public int height;

	@Inject(method = "init*", at = @At("RETURN"))
	private void init(MinecraftClient client, int width, int height, CallbackInfo info) {
		if (!((Screen)(Object)this instanceof TitleScreen) && ((Screen)(Object)this instanceof GameMenuScreen) && CrunchyConfig.Client.removeAdvancementsButton) {
			final int buttonWidth = 204;
			final int spacing = 24;
			for (ClickableWidget button : Screens.getButtons((Screen)(Object)this)) {
				if (CrunchyAdvancementsClient.buttonMatchesKey(button, "gui.advancements")) {
					button.visible = false;
				}
				if (CrunchyAdvancementsClient.buttonMatchesKey(button, "gui.stats")) {
					button.setWidth(buttonWidth);
					button.x = this.width / 2 - buttonWidth / 2;
				}
			}
		}
	}
}
