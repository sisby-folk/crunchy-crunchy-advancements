<!--suppress HtmlDeprecatedTag, XmlDeprecatedElement -->
<center>
Takes a configurably-large bite out of advancements.<br/>
Requires <a href="https://modrinth.com/mod/connector">Connector</a> and <a href="https://modrinth.com/mod/forgified-fabric-api">FFAPI</a> on forge.<br/>
Works well with <a href="https://modrinth.com/mod/emi">EMI</a>.
</center>

---

A fully configurable mod that by default:
 - Removes the advancements button on the pause screen.
 - Disables toast notifications for advancements and recipe unlocks.
 - Prevents chat announcements for advancements.
 - Prevents recipe advancements from loading.

It can also arbitrarily filter advancements from loading at runtime based on namespace/path.

### Design Notes

Some mods, like [Patchouli](https://modrinth.com/mod/patchouli), rely on the advancements system to track their progression. This means gutting advancements in the wrong way can cause issues.

However, advancements and their visibility just doesn't suit every pack and player - so this mod is designed to configurably hide and remove advancements in a safe way.

---

### Afterword

All mods are built on the work of many others.

This mod is included in [Tinkerer's Quilt](https://modrinth.com/modpack/tinkerers-quilt) - which replaces all advancements with a full vanilla quest tree.

We're open to suggestions for how to implement stuff better - if you see something wonky and have an idea - let us know.
