package folk.sisby.crunchy_crunchy_advancements.mixin;

import folk.sisby.crunchy_crunchy_advancements.CrunchyAdvancements;
import folk.sisby.crunchy_crunchy_advancements.CrunchyConfig;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.server.ServerAdvancementLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

@Mixin(ServerAdvancementLoader.class)
public abstract class ServerAdvancementLoaderMixin {
	@ModifyArg(method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancement/AdvancementManager;addAll(Ljava/util/Collection;)V"))
	private Collection<AdvancementEntry> filterMap(Collection<AdvancementEntry> immutableEntries) {
		Collection<AdvancementEntry> entries = new ArrayList<>(immutableEntries);
		Predicate<AdvancementEntry> namespace_predicate = (entry) -> CrunchyAdvancements.CONFIG.filterNamespaces.contains(entry.id().getNamespace());
		Predicate<AdvancementEntry> path_predicate = (entry) -> CrunchyAdvancements.CONFIG.filterPaths.stream().anyMatch(path -> entry.id().toString().startsWith(path));
		Predicate<AdvancementEntry> recipe_predicate = CrunchyAdvancements.CONFIG.filterRecipes ? (entry) -> entry.value().comp_1915().containsKey("has_the_recipe") : (entry) -> false;
		Predicate<AdvancementEntry> filter_predicate = namespace_predicate.or(path_predicate).or(recipe_predicate);
		if (CrunchyAdvancements.CONFIG.filterMode.equals(CrunchyConfig.FilterMode.WHITELIST)) {
			entries.removeIf(filter_predicate.negate());
		} else if (CrunchyAdvancements.CONFIG.filterMode.equals(CrunchyConfig.FilterMode.BLACKLIST)) {
			entries.removeIf(filter_predicate);
		}
		return entries;
	}
}
