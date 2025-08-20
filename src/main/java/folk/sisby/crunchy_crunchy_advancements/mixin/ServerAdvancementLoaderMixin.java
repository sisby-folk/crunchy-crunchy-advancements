package folk.sisby.crunchy_crunchy_advancements.mixin;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import folk.sisby.crunchy_crunchy_advancements.CrunchyAdvancements;
import folk.sisby.crunchy_crunchy_advancements.CrunchyConfig;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

@Mixin(ServerAdvancementLoader.class)
public abstract class ServerAdvancementLoaderMixin {
	@ModifyExpressionValue(method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableMap$Builder;buildOrThrow()Lcom/google/common/collect/ImmutableMap;"))
	private ImmutableMap<Identifier, AdvancementEntry> filterMap(ImmutableMap<Identifier, AdvancementEntry> original) {
		Map<Identifier, AdvancementEntry> map = new HashMap<>(original);
		Predicate<AdvancementEntry> namespace_predicate = (entry) -> CrunchyAdvancements.CONFIG.filterNamespaces.contains(entry.id().getNamespace());
		Predicate<AdvancementEntry> path_predicate = (entry) -> CrunchyAdvancements.CONFIG.filterPaths.stream().anyMatch(path -> entry.id().toString().startsWith(path));
		Predicate<AdvancementEntry> recipe_predicate = CrunchyAdvancements.CONFIG.filterRecipes ? (entry) -> entry.value().criteria().containsKey("has_the_recipe") : (entry) -> false;
		Predicate<AdvancementEntry> filter_predicate = namespace_predicate.or(path_predicate).or(recipe_predicate);
		if (CrunchyAdvancements.CONFIG.filterMode.equals(CrunchyConfig.FilterMode.WHITELIST)) {
			map.values().removeIf(filter_predicate.negate());
		} else if (CrunchyAdvancements.CONFIG.filterMode.equals(CrunchyConfig.FilterMode.BLACKLIST)) {
			map.values().removeIf(filter_predicate);
		}
		return ImmutableMap.<Identifier, AdvancementEntry>builder().putAll(map).build();
	}
}
