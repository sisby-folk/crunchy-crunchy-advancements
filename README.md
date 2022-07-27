# Crunchy Crunchy Advancements

This mod:
 - Prevents recipe advancements from loading entirely (use [EMI](https://modrinth.com/mod/emi)+[NoRecipeBook](https://modrinth.com/mod/norecipebook-fabric)!)
 - Removes the advancements button from the pause screen (with credit to [MinimalMenu](https://modrinth.com/mod/minimalmenu))
 - Disables toast notifications and chat announcements for Advancements (with credit to [SomeOrdinaryTweaks](https://modrinth.com/mod/ordinarytweaks))

We're also planning on disabling the keybind in future, when the keybinds API is available.


## Design and Future plans

This mod is designed around removing parts of the advancement system where it's safe to, and hiding them where that isn't possible.

Personally, we use this mod for our pack [Tinkerer's Quilt!](https://modrinth.com/modpack/tinkerers-quilt), so that the vanilla-guiding quests can fully replace base-game advancements.

Some mods, like [Patchouli](https://modrinth.com/mod/patchouli), rely on the advancements system to track unlocks for their progression. As such completely gutting the whole system, or preventing any advancement from loading, could cause compatibility issues - this mod avoids this.

In future, we'd like to add configuration that allows users to manually specify what advancements they'd like to prevent from loading - with whitelist and blacklist modes.

## Further Info

All mods are built on the work of many others.

We're primarily modpack developers - not mod developers! If you want to port this mod, do it yourself!

Though feel free to let us know if we can spruce anything on the implementation side - PRs and issues with code snippets are welcome as long as you can help us understand them.
