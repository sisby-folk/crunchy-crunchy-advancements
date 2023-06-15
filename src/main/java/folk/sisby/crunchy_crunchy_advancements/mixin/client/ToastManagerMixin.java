package folk.sisby.crunchy_crunchy_advancements.mixin.client;

import folk.sisby.crunchy_crunchy_advancements.CrunchyAdvancements;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.toast.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ToastManager.class)
public abstract class ToastManagerMixin extends DrawableHelper {
	@Inject(method = "add", at = @At("HEAD"), cancellable = true)
	public void add(Toast toast, CallbackInfo ci) {
		if (toast instanceof AdvancementToast && CrunchyAdvancements.CONFIG.removeAdvancementToasts || toast instanceof RecipeToast && CrunchyAdvancements.CONFIG.removeRecipeToasts) {
			ci.cancel();
		}
	}
}
