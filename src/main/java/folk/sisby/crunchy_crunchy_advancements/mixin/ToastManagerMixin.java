package folk.sisby.crunchy_crunchy_advancements.mixin;

import folk.sisby.crunchy_crunchy_advancements.CrunchyConfig;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.toast.AdvancementToast;
import net.minecraft.client.toast.RecipeToast;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ToastManager.class)
public abstract class ToastManagerMixin extends DrawableHelper {
	@Inject(
			method = "add",
			at = @At("HEAD"),
			cancellable = true
	)
	public void add(Toast toast, CallbackInfo ci) {
		if (toast instanceof AdvancementToast && CrunchyConfig.Client.removeAdvancementToasts) {
			ci.cancel();
		} else if (toast instanceof RecipeToast && CrunchyConfig.Client.removeRecipeToasts) {
			ci.cancel();
		}
	}
}
