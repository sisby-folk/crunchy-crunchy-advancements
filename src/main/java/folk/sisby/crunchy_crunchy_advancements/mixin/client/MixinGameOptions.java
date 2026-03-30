package folk.sisby.crunchy_crunchy_advancements.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import folk.sisby.crunchy_crunchy_advancements.CrunchyAdvancements;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.stream.Stream;

@Mixin(GameOptions.class)
public class MixinGameOptions {
	@Shadow
	@Final
	public KeyBinding advancementsKey;

	@ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;<init>(Ljava/lang/String;ILnet/minecraft/client/option/KeyBinding$Category;)V"))
	private static void unbindAdvancementsKeybind(Args args) {
		if (CrunchyAdvancements.CONFIG.removeAdvancementsKeybind && args.get(0).equals("key.advancements")) {
			args.set(1, InputUtil.UNKNOWN_KEY.getCode());
		}
	}

	@ModifyExpressionValue(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;flatMap(Ljava/util/function/Function;)Ljava/util/stream/Stream;", remap = false))
	private Stream<KeyBinding> removeAdvancementsKeybind(Stream<KeyBinding> original) {
		return original.filter(k -> k != advancementsKey);
	}
}
