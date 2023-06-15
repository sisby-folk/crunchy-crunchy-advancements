package folk.sisby.crunchy_crunchy_advancements.mixin;

import folk.sisby.crunchy_crunchy_advancements.CrunchyAdvancements;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.PlayerManager;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerAdvancementTracker.class)
public abstract class PlayerAdvancementTrackerMixin {
	@Redirect(method = "grantCriterion", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;method_43514(Lnet/minecraft/text/Text;Z)V"))
	public void grantCriterionBroadcastRemover(PlayerManager instance, Text message, boolean overlay) {
		if (!CrunchyAdvancements.CONFIG.preventAdvancementBroadcasts) {
			instance.method_43514(message, overlay);
		}
	}
}

