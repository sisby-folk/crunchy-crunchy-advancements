package folk.sisby.crunchy_crunchy_advancements.mixin;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import folk.sisby.crunchy_crunchy_advancements.CrunchyAdvancements;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.PlayerManager;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerAdvancementTracker.class)
public abstract class PlayerAdvancementTrackerMixin {
	@WrapWithCondition(method = "grantCriterion", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcast(Lnet/minecraft/text/Text;Z)V"))
	public boolean grantCriterionBroadcastRemover(PlayerManager instance, Text text, boolean bl) {
		return !CrunchyAdvancements.CONFIG.preventAdvancementBroadcasts;
	}
}
