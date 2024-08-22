package folk.sisby.crunchy_crunchy_advancements.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import folk.sisby.crunchy_crunchy_advancements.CrunchyAdvancements;
import net.minecraft.advancement.PlayerAdvancementTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerAdvancementTracker.class)
public abstract class PlayerAdvancementTrackerMixin {
	@ModifyExpressionValue(method = "grantCriterion", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancement/AdvancementDisplay;shouldAnnounceToChat()Z"))
	public boolean grantCriterionBroadcastRemover(boolean original) {
		return original && !CrunchyAdvancements.CONFIG.preventAdvancementBroadcasts;
	}
}
