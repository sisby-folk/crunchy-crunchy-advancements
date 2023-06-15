package folk.sisby.crunchy_crunchy_advancements.mixin;

import folk.sisby.crunchy_crunchy_advancements.CrunchyAdvancements;
import folk.sisby.crunchy_crunchy_advancements.CrunchyConfig;
import net.minecraft.advancement.Advancement;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Map;
import java.util.function.Predicate;

@Mixin(ServerAdvancementLoader.class)
public abstract class ServerAdvancementLoaderMixin {
	@ModifyArg(method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancement/AdvancementManager;load(Ljava/util/Map;)V"))
	private Map<Identifier, Advancement.Task> filterMap(Map<Identifier, Advancement.Task> map) {
		Predicate<Map.Entry<Identifier, Advancement.Task>> namespace_predicate = (entry) -> CrunchyAdvancements.CONFIG.filterNamespaces.contains(entry.getKey().getNamespace());
		Predicate<Map.Entry<Identifier, Advancement.Task>> path_predicate = (entry) -> CrunchyAdvancements.CONFIG.filterPaths.stream().anyMatch(path -> entry.getKey().toString().startsWith(path));
		Predicate<Map.Entry<Identifier, Advancement.Task>> recipe_predicate = CrunchyAdvancements.CONFIG.filterRecipes ? (entry) -> entry.getValue().getCriteria().containsKey("has_the_recipe") : (entry) -> false;
		Predicate<Map.Entry<Identifier, Advancement.Task>> filter_predicate = namespace_predicate.or(path_predicate).or(recipe_predicate);
		if (CrunchyAdvancements.CONFIG.filterMode.equals(CrunchyConfig.FilterMode.WHITELIST)) {
			map.entrySet().removeIf(filter_predicate.negate());
		} else if (CrunchyAdvancements.CONFIG.filterMode.equals(CrunchyConfig.FilterMode.BLACKLIST)) {
			map.entrySet().removeIf(filter_predicate);
		}
		return map;
	}
}
