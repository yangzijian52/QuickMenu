# Changelog

## 2.0.2 - 2026-07-12

### Added

- Prepared SpigotMC publishing materials:
  - `docs/SPIGOTMC-RESOURCE.md`
  - `docs/SPIGOTMC-RESOURCE-BBCODE.txt`
  - `docs/SPIGOTMC_MANUAL_PUBLISHING.md`
- Documented the resource type as a free resource for SpigotMC.
- Added release validation notes for Paper 26.2 and Floodgate/Bedrock testing.

### Current Release Baseline

- Paper 26.2 dual-platform menu plugin.
- Java players use inventory GUI menus.
- Bedrock/mobile players use Floodgate forms when Floodgate is installed.
- Management command is `/quickmenu`.
- Menu opener items use plugin-owned persistent item tags.

### Validation

- Paper 26.2 server testing passed.
- Bedrock/Floodgate form testing passed.
- Maven build target remains `target/QuickMenu.jar`.

### Previous 2.0.2 Baseline

Released on 2026-07-08.

#### Added

- PlayerMenu-style slot-based menu configuration.
- Dual-platform menu rendering:
  - Java Edition inventory GUI.
  - Floodgate Bedrock/mobile form menus.
- Dynamic per-menu open commands through `openCommand`.
- Tagged menu opener items, defaulting to `CLOCK`.
- Command actions: `[command]`, `[console]`, `[op]`, `[message]`, `[open]`, `[close]`, `[sound]`.
- Detailed command documentation in `docs/COMMANDS.md`.

#### Changed

- Updated to Paper API `26.2.build.53-alpha`.
- Updated build version to `2.0.2`.
- Switched Floodgate from hard dependency to `softdepend`.
- Standardized management commands under `/quickmenu`.

#### Fixed

- Fixed menu loading failure when `customModelData` is not configured.
- Avoided treating normal player clocks as menu opener items.
