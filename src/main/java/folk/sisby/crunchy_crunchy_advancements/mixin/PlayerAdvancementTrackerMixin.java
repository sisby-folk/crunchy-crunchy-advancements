package folk.sisby.crunchy_crunchy_advancements.mixin;

import folk.sisby.crunchy_crunchy_advancements.CrunchyAdvancements;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.network.MessageType;
import net.minecraft.server.PlayerManager;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.UUID;

@Mixin(PlayerAdvancementTracker.class)
public abstract class PlayerAdvancementTrackerMixin {
	@Redirect(method = "grantCriterion", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcast(Lnet/minecraft/text/Text;Lnet/minecraft/network/MessageType;Ljava/util/UUID;)V"))
	public void grantCriterionBroadcastRemover(PlayerManager instance, Text message, MessageType type, UUID sender) {
		if (!CrunchyAdvancements.CONFIG.preventAdvancementBroadcasts) {
			instance.broadcast(message, type, sender);
		}
	}
}
