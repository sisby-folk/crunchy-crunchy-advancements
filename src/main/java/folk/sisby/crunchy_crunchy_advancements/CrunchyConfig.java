package folk.sisby.crunchy_crunchy_advancements;


import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ValueList;

import java.util.List;

public class CrunchyConfig extends WrappedConfig {
	@Comment("When enabled, the server will not send chat messages about advancements, ignoring the relevant game rule")
	public Boolean preventAdvancementBroadcasts = true;
	@Comment("If set to OFF, all advancements will load normally from data packs")
	@Comment("If set to BLACKLIST, advancements that are congruent with the filters below will not be loaded")
	@Comment("If set to WHITELIST, only advancements that are congruent with the filters below will be loaded")
	public FilterMode filterMode = FilterMode.BLACKLIST;
	@Comment("Namespaces to be entirely filtered, e.g. 'minecraft' or 'antique_atlas'")
	public List<String> filterNamespaces = ValueList.create("");
	@Comment("Namespace-inclusive paths to be filtered, e.g. 'minecraft:recipes/' or 'tconstruct:foundry/'")
	public List<String> filterPaths = ValueList.create("");
	@Comment("Whether to filter advancements that are triggered when obtaining a recipe, used for recipe advancements")
	public Boolean filterRecipes = true;
	@Comment("Whether to remove the advancements keybind from the game")
	public Boolean removeAdvancementsKeybind = true;
	@Comment("Whether to remove the advancements button from the pause menu, extending the stats button to fill space")
	public Boolean removeAdvancementsButton = true;
	@Comment("Whether to hide the toast that appears when an advancement is completed")
	public Boolean removeAdvancementToasts = true;
	@Comment("Whether to hide the toast that appears when you unlock a recipe")
	public Boolean removeRecipeToasts = true;

	public enum FilterMode {
		OFF,
		BLACKLIST,
		WHITELIST
	}
}
