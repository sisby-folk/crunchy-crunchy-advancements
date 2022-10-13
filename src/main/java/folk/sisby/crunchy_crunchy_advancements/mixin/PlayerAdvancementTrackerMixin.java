package folk.sisby.crunchy_crunchy_advancements.mixin;

import folk.sisby.crunchy_crunchy_advancements.CrunchyConfig;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.PlayerManager;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerAdvancementTracker.class)
public abstract class PlayerAdvancementTrackerMixin {
	@Redirect(
			method = "grantCriterion",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/server/PlayerManager;m_bgctehjy(Lnet/minecraft/text/Text;Z)V"
			)
	)
	public void grantCriterionBroadcastRemover(PlayerManager instance, Text message, boolean overlay) {
		if (!CrunchyConfig.General.preventAdvancementBroadcasts) {
			instance.m_bgctehjy(message, overlay);
		}
	}
}
