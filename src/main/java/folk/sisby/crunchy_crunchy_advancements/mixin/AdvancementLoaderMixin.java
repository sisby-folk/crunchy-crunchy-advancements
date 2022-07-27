package folk.sisby.crunchy_crunchy_advancements.mixin;

import com.google.gson.Gson;
import net.minecraft.advancement.Advancement;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Map;

@Mixin(ServerAdvancementLoader.class)
public abstract class AdvancementLoaderMixin extends JsonDataLoader {
	public AdvancementLoaderMixin(Gson gson, String string) {
		super(gson, string);
	}

	@ModifyArg(
			method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/advancement/AdvancementManager;load(Ljava/util/Map;)V")
	)
	private Map<Identifier, Advancement.Task> filterMap(Map<Identifier, Advancement.Task> map) {
		map.entrySet().removeIf((entry) -> entry.getValue().getCriteria().containsKey("has_the_recipe"));
		return map;
	}
}
