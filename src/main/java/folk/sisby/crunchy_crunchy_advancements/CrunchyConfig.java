package folk.sisby.crunchy_crunchy_advancements;

import com.google.common.base.Ascii;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import folk.sisby.crunchy_crunchy_advancements.com.unascribed.qdcss.QDCSS;
import folk.sisby.crunchy_crunchy_advancements.com.unascribed.qdcss.SyntaxErrorException;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static folk.sisby.crunchy_crunchy_advancements.CrunchyAdvancements.CRUNCHY_LOGGER;

/*
 * This is just YConfig from yttr (MIT)
 */
public class CrunchyConfig {

	public enum FilterMethod {
		OFF,
		BLACKLIST,
		WHITELIST
	}

	@Retention(RetentionPolicy.RUNTIME)
	private @interface Key {
		String value();
	}

	public static final QDCSS defaults;
	public static QDCSS data;

	private static final List<Class<?>> sections = List.of(
			General.class, Data.class, Client.class, Debug.class
	);
	private static final Map<String, Class<?>> keyTypes = new HashMap<>();

	static {
		URL defaultsUrl = CrunchyConfig.class.getResource("/config/crunchy-crunchy-advancements-default.css");
		try {
			assert defaultsUrl != null;
			defaults = QDCSS.load(defaultsUrl);
		} catch (IOException e) {
			throw new Error("Could not load config defaults", e);
		}
		load();
		save();
	}

	public static Class<?> getKeyType(String key) {
		return keyTypes.getOrDefault(key, void.class);
	}

	public static void copyFieldsToData() {
		for (Class<?> section : sections) {
			for (Field f : section.getFields()) {
				if (Modifier.isStatic(f.getModifiers())) {
					Key k = f.getAnnotation(Key.class);
					if (k != null) {
						try {
							if (f.getType() == boolean.class) {
								data.put(k.value(), f.getBoolean(null) ? "on" : "off");
							} else if (f.getType().isEnum()) {
								data.put(k.value(), Ascii.toLowerCase(((Enum<?>) f.get(null)).name()));
							} else if (String.class.isAssignableFrom(f.getType())) {
								data.put(k.value(), (String) f.get(null));
							} else if (List.class.isAssignableFrom(f.getType())) {
								data.put(k.value(), ((List<String>) f.get(null)).stream().map(str -> "'" + str + "'").toList());
							}
						} catch (Exception e) {
							CRUNCHY_LOGGER.error("Could not serialize config", e);
						}
					}
				}
			}
		}
	}

	public static List<String> ignoreUnset(List<String> li) {
		return li.stream().filter(s -> !s.equals("unset")).toList();
	}

	public static void copyDataToFields() {
		for (Class<?> section : sections) {
			for (Field f : section.getFields()) {
				if (Modifier.isStatic(f.getModifiers())) {
					Key k = f.getAnnotation(Key.class);
					if (k != null) {
						try {
							keyTypes.put(k.value(), f.getType());
							if (f.getType() == boolean.class) {
								f.set(null, data.getBoolean(k.value()).get());
							} else if (f.getType().isEnum()) {
								f.set(null, data.getEnum(k.value(), (Class) f.getType()).get());
							} else if (String.class.isAssignableFrom(f.getType())) {
								f.set(null, data.get(k.value()).get());
							} else if (List.class.isAssignableFrom(f.getType())) {
								f.set(null, ignoreUnset(data.getAll(k.value())));
							}
						} catch (Exception e) {
							CRUNCHY_LOGGER.error("Could not memoize config", e);
						}
					}
				}
			}
		}
	}

	public static void load() {
		File cfg = new File("config/crunchy-crunchy-advancements.css");
		if (!cfg.exists()) {
			data = QDCSS.empty().merge(defaults);
			save();
		} else {
			QDCSS dataTmp;
			try {
				dataTmp = QDCSS.load(cfg);
			} catch (IOException e) {
				CRUNCHY_LOGGER.error("IO error when reading configuration. Using defaults", e);
				dataTmp = defaults;
			} catch (SyntaxErrorException e) {
				CRUNCHY_LOGGER.error("Syntax error in configuration: {}. Using defaults", e.getMessage());
				dataTmp = defaults;
			}
			data = defaults.merge(dataTmp);
		}
		copyDataToFields();
	}

	public static void save() {
		copyFieldsToData();
		URL templateUrl = CrunchyConfig.class.getResource("/config/crunchy-crunchy-advancements-template.css");
		File cfg = new File("config/crunchy-crunchy-advancements.css");
		try {
			Files.createParentDirs(cfg);
			assert templateUrl != null;
			List<String> l = new ArrayList<>(Resources.asCharSource(templateUrl, Charsets.UTF_8).readLines());

			for (Map.Entry<String, List<String>> en : data.entrySet()) {
				IntStream.range(0, l.size()).filter(i -> l.get(i).contains("var(" + en.getKey() + ")")).findFirst().ifPresent(i -> {
					String line = l.get(i);
					l.remove(i);
					if (en.getValue().isEmpty()) {
						l.add(i, line.replace("var(" + en.getKey() + ")", "unset"));
					}
					en.getValue().forEach((val) -> l.add(i, line.replace("var(" + en.getKey() + ")", val)));
				});
			}
			Files.asCharSink(cfg, Charsets.UTF_8).writeLines(l);
		} catch (IOException e) {
			CRUNCHY_LOGGER.error("IO error when copying default configuration", e);
		}
	}

	static void touch() {
	}

	public static final class General {
		@Key("general.prevent-advancement-broadcasts")
		public static boolean preventAdvancementBroadcasts = true;

		static {
			touch();
		}

		private General() {
		}
	}

	public static final class Data {
		@Key("data.filter-method")
		public static FilterMethod filterMethod = FilterMethod.BLACKLIST;
		@Key("data.filter-namespace")
		public static List<String> filterNamespace = new ArrayList<>();
		@Key("data.filter-path")
		public static List<String> filterPath = new ArrayList<>();
		@Key("data.filter-recipes")
		public static boolean filterRecipes = true;

		static {
			touch();
		}

		private Data() {
		}
	}

	public static final class Client {
		@Key("client.remove-advancements-button")
		public static boolean removeAdvancementsButton = true;
		@Key("client.remove-advancements-keybinding")
		public static boolean removeAdvancementsKeybinding = true;
		@Key("client.remove-advancement-toasts")
		public static boolean removeAdvancementToasts = true;
		@Key("client.remove-recipe-toasts")
		public static boolean removeRecipeToasts = true;

		static {
			touch();
		}

		private Client() {
		}
	}

	public static final class Debug {

		static {
			touch();
		}

		private Debug() {
		}
	}

}
