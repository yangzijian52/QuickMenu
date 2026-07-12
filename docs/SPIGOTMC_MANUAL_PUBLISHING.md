# SpigotMC Manual Publishing

This file is a checklist for manually publishing QuickMenu on SpigotMC.

## Resource Metadata

- Resource type: Free
- Price: 0 USD
- License: MIT License
- Upload file: `target/QuickMenu.jar`
- GitHub release: `https://github.com/yangzijian52/QuickMenu/releases/tag/2.0.2`
- Source code: `https://github.com/yangzijian52/QuickMenu`
- BBCode resource page: `docs/SPIGOTMC-RESOURCE.md`
- BBCode documentation page: `docs/SPIGOTMC-RESOURCE-BBCODE.txt`

## Resource Type Decision

QuickMenu should be published as a free resource. The project is open source under the MIT License, the source code is publicly available on GitHub, and the plugin is a general-purpose server menu utility without license checks, account binding, premium modules or paid-only feature gates.

## Suggested SpigotMC Fields

- Title: `QuickMenu`
- Tag line: `Dual-platform GUI and Floodgate form menus for Paper 26.2`
- Category: Bukkit / Spigot Resources, Administration or Miscellaneous
- Native Minecraft version: Paper 26.2
- Tested Minecraft version: Paper 26.2
- Price: Free
- Source code URL: `https://github.com/yangzijian52/QuickMenu`
- Download: upload `target/QuickMenu.jar`
- External download URL: leave empty unless SpigotMC requires using GitHub Releases
- Description: paste `docs/SPIGOTMC-RESOURCE.md`
- Documentation: paste `docs/SPIGOTMC-RESOURCE-BBCODE.txt`

## Important Warnings

- The SpigotMC resource page, documentation and support channel are English-only. Chinese-language support is not provided on SpigotMC.
- This plugin targets Paper 26.2. Spigot compatibility has not been tested and is not claimed.
- Floodgate is optional for Java GUI menus but required for Bedrock/mobile form menus.
- Back up `plugins/QuickMenu/` before replacing the jar or rewriting menu YAML files.
- Review all configured commands before publishing examples. The `[op]` action should only be used for trusted commands.
- Do not upload files from `.idea/`, `target/` except the final jar, or temporary build files.

## Manual Publish Steps

1. Ensure the GitHub `main` branch is pushed.
2. Ensure `mvn clean package` succeeds.
3. Confirm the final jar exists at `target/QuickMenu.jar`.
4. Confirm `plugin.yml` inside the jar reports version `2.0.2`.
5. Confirm the GitHub Release exists and contains `QuickMenu.jar`.
6. Copy `docs/SPIGOTMC-RESOURCE.md` into the SpigotMC resource overview field.
7. Copy `docs/SPIGOTMC-RESOURCE-BBCODE.txt` into the detailed documentation/update field as appropriate.
8. Upload `target/QuickMenu.jar`.
9. Set the resource as free.
10. Publish the SpigotMC resource.

## Release Validation Template

- Build command: `mvn clean package`
- Build result: success
- Server test: Paper 26.2 passed
- Bedrock/Floodgate test: passed
- Artifact: `target/QuickMenu.jar`
- SHA-256: fill from the latest build
