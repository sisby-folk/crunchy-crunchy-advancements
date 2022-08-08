package folk.sisby.crunchy_crunchy_advancements.mixin;

import com.google.gson.Gson;
import folk.sisby.crunchy_crunchy_advancements.CrunchyConfig;
import net.minecraft.advancement.Advancement;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

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

		List<String> filter_namespaces = CrunchyConfig.Data.filterNamespace;
		Predicate<Map.Entry<Identifier, Advancement.Task>> namespace_predicate =
				(entry) -> filter_namespaces.contains(entry.getKey().getNamespace());

		List<String> filter_paths = CrunchyConfig.Data.filterPath;
		Predicate<Map.Entry<Identifier, Advancement.Task>> path_predicate =
				(entry) -> filter_paths.stream().anyMatch(path -> entry.getKey().toString().startsWith(path));

		Predicate<Map.Entry<Identifier, Advancement.Task>> recipe_predicate = CrunchyConfig.Data.filterRecipes ?
				(entry) -> entry.getValue().getCriteria().containsKey("has_the_recipe") : (entry) -> false;

		Predicate<Map.Entry<Identifier, Advancement.Task>> filter_predicate =
				namespace_predicate.or(path_predicate).or(recipe_predicate);

		if (CrunchyConfig.Data.filterMethod.equals(CrunchyConfig.FilterMethod.WHITELIST)) {
			map.entrySet().removeIf(filter_predicate.negate());
		} else if (CrunchyConfig.Data.filterMethod.equals(CrunchyConfig.FilterMethod.BLACKLIST)) {
			map.entrySet().removeIf(filter_predicate);
		}

		return map;
	}
}
