package folk.sisby.crunchy_crunchy_advancements.mixin;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import folk.sisby.crunchy_crunchy_advancements.CrunchyAdvancements;
import net.minecraft.advancement.PlayerAdvancementTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;
import java.util.function.Consumer;

@Mixin(PlayerAdvancementTracker.class)
public abstract class PlayerAdvancementTrackerMixin {
	@WrapWithCondition(method = "grantCriterion", at = @At(value = "INVOKE", target = "Ljava/util/Optional;ifPresent(Ljava/util/function/Consumer;)V"))
	public boolean grantCriterionBroadcastRemover(Optional<Object> instance, Consumer<Object> action) {
		return !CrunchyAdvancements.CONFIG.preventAdvancementBroadcasts;
	}
}

