package folk.sisby.crunchy_crunchy_advancements.mixin;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import folk.sisby.crunchy_crunchy_advancements.CrunchyAdvancements;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.network.MessageType;
import net.minecraft.server.PlayerManager;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.UUID;

@Mixin(PlayerAdvancementTracker.class)
public abstract class PlayerAdvancementTrackerMixin {
	@WrapWithCondition(method = "grantCriterion", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcast(Lnet/minecraft/text/Text;Lnet/minecraft/network/MessageType;Ljava/util/UUID;)V"))
	public boolean grantCriterionBroadcastRemover(PlayerManager instance, Text text, MessageType messageType, UUID uUID) {
		return !CrunchyAdvancements.CONFIG.preventAdvancementBroadcasts;
	}
}
