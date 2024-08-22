package folk.sisby.crunchy_crunchy_advancements.mixin.client;

import folk.sisby.crunchy_crunchy_advancements.CrunchyAdvancements;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(GameOptions.class)
public class MixinGameOptions {
	@Shadow @Final public KeyBinding advancementsKey;

	@ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;<init>(Ljava/lang/String;ILjava/lang/String;)V"))
	private void unbindAdvancementsKeybind(Args args) {
		if (CrunchyAdvancements.CONFIG.removeAdvancementsButton && args.get(0).equals("key.advancements")) {
			args.set(1, InputUtil.UNKNOWN_KEY.getCode());
		}
	}

	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lorg/apache/commons/lang3/ArrayUtils;addAll([Ljava/lang/Object;[Ljava/lang/Object;)[Ljava/lang/Object;"), index = 0)
	private Object[] removeAdvancementsKeybind(Object[] original) {
		return CrunchyAdvancements.CONFIG.removeAdvancementsButton ? ArrayUtils.removeElement(original, advancementsKey) : original;
	}
}
