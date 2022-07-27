package folk.sisby.crunchy_crunchy_advancements.mixin;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.advancement.AdvancementManager;
import net.minecraft.advancement.AdvancementPositioner;
import net.minecraft.loot.condition.LootConditionManager;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.advancement.Advancement;

import java.util.Map;

@Mixin(ServerAdvancementLoader.class)
public abstract class MixinAdvancementLoader extends JsonDataLoader {

	@Final
	@Shadow
	private static Logger LOGGER;

	@Shadow
	private AdvancementManager manager;

	@Final
	@Shadow
	private LootConditionManager conditionManager;

	public MixinAdvancementLoader(Gson gson, String string) {
		super(gson, string);
	}

	/**
	 * @author Sisby-Folk
	 * @reason Crunchy-Crunchy-Advancements preventing advancements from loading
	 */
	@Overwrite
	public void apply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler) {
		Map<Identifier, Advancement.Task> map2 = Maps.newHashMap();
		map.forEach((id, json) -> {
			try {
				JsonObject jsonObject = JsonHelper.asObject(json, "advancement");
				Advancement.Task task = Advancement.Task.fromJson(jsonObject, new AdvancementEntityPredicateDeserializer(id, this.conditionManager));
				if (!task.getCriteria().containsKey("has_the_recipe")) {
					map2.put(id, task);
				}
			} catch (Exception var6) {
				LOGGER.error("Parsing error loading custom advancement {}: {}", id, var6.getMessage());
			}

		});
		AdvancementManager advancementManager = new AdvancementManager();
		advancementManager.load(map2);

		for (Advancement advancement : advancementManager.getRoots()) {
			if (advancement.getDisplay() != null) {
				AdvancementPositioner.arrangeForTree(advancement);
			}
		}

		this.manager = advancementManager;
	}
}
