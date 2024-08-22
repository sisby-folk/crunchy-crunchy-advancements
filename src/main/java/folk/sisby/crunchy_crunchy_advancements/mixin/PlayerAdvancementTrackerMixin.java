package folk.sisby.crunchy_crunchy_advancements.mixin;

import folk.sisby.crunchy_crunchy_advancements.CrunchyAdvancements;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.PlayerAdvancementTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerAdvancementTracker.class)
public abstract class PlayerAdvancementTrackerMixin {
	@Redirect(method = "grantCriterion", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancement/AdvancementDisplay;shouldAnnounceToChat()Z"))
	public boolean grantCriterionBroadcastRemover(AdvancementDisplay instance) {
		return instance.shouldAnnounceToChat() && !CrunchyAdvancements.CONFIG.preventAdvancementBroadcasts;
	}
}
