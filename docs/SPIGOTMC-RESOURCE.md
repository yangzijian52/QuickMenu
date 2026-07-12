[CENTER][SIZE=7][B]QuickMenu[/B][/SIZE]
[SIZE=4]Dual-platform GUI and Floodgate Form Menus for Paper 26.2[/SIZE][/CENTER]

[COLOR=#ff4d4d][B]Language notice:[/B][/COLOR] The SpigotMC resource page, documentation and support channel are English-only. Chinese-language support is not provided on SpigotMC.

[SIZE=5][B]About QuickMenu[/B][/SIZE]
QuickMenu is a lightweight menu plugin for Paper 26.2 servers. It provides inventory GUI menus for Java Edition players and Floodgate form menus for Bedrock/mobile players. Menu layouts are configured through simple YAML files with slot-based items, display names, lore, permissions and click actions.

QuickMenu is inspired by the general PlayerMenu-style menu workflow, but it is an independent implementation. It does not copy PlayerMenu source code.

[SIZE=5][B]Compatibility[/B][/SIZE]
[LIST]
[*][B]Server software:[/B] Paper 26.2
[*][B]Java:[/B] 25 or newer
[*][B]Paper API target:[/B] 26.2.build.53-alpha
[*][B]Java Edition support:[/B] Inventory GUI menus
[*][B]Bedrock/mobile support:[/B] Floodgate form menus when Floodgate is installed
[*][B]Server testing:[/B] Successfully tested on a live Paper 26.2 server
[*][B]Bedrock testing:[/B] Successfully tested through Floodgate
[*]Spigot compatibility has not been tested and is not claimed
[/LIST]

[SIZE=5][B]Free Resource[/B][/SIZE]
QuickMenu is prepared as a [B]free[/B] SpigotMC resource. The reason is that the project is open source under the MIT License, the source code is publicly available on GitHub, and the plugin provides a general-purpose server utility without license checks, premium-only modules or paid feature gates.

[SIZE=5][B]Main Features[/B][/SIZE]
[LIST]
[*]Java Edition inventory GUI menus
[*]Floodgate Bedrock/mobile form menus
[*]PlayerMenu-style slot-based YAML configuration
[*]Multiple menu files under plugins/QuickMenu/menus/
[*]Per-menu open commands such as /cd or /servermenu
[*]Tagged menu opener item, using CLOCK by default
[*]Normal player clocks are not treated as menu items
[*]Nested menu navigation through [open]
[*]Player, console, temporary OP, message, close and sound actions
[*]Per-menu and per-button permissions
[*]Legacy menu-list compatibility for older QuickMenu configs
[/LIST]

[SIZE=5][B]Safety and Data Notes[/B][/SIZE]
[LIST]
[*]QuickMenu does not store player databases, balances or transaction data.
[*]Configuration is stored in YAML files inside plugins/QuickMenu/.
[*]Menu opener items use a plugin-owned PersistentDataContainer tag named open_menu.
[*]Only tagged opener items trigger menus; normal clocks or compasses do not.
[*]The [op] action temporarily grants OP only for the configured command and then restores the previous OP state.
[*]Use [op] only for trusted commands and trusted menu files.
[/LIST]

[SIZE=5][B]Dependencies[/B][/SIZE]
[LIST]
[*][B]Required:[/B] Paper 26.2 and Java 25+
[*][B]Optional:[/B] Floodgate 2.x for Bedrock/mobile form menus
[*]Without Floodgate, Java GUI menus still work normally.
[/LIST]

[SIZE=5][B]Links[/B][/SIZE]
[LIST]
[*][URL=https://github.com/yangzijian52/QuickMenu]Source Code[/URL]
[*][URL=https://github.com/yangzijian52/QuickMenu/releases]Downloads[/URL]
[*][URL=https://github.com/yangzijian52/QuickMenu/issues]Support and Issues[/URL]
[/LIST]

[SIZE=5][B]Important Notes[/B][/SIZE]
[LIST]
[*]This resource targets Paper 26.2 only.
[*]Install Floodgate before using Bedrock/mobile form menus.
[*]SpigotMC support is English-only.
[*]Back up plugins/QuickMenu/ before replacing the jar or rewriting menu files.
[*]The plugin is configurable, so command safety depends on the server owner's menu YAML files.
[/LIST]
