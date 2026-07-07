# QuickMenu Paper 26.2 交付说明

## 概要

本文档记录 QuickMenu 改造为 PlayerMenu 风格双端菜单插件后的 Paper 26.2 兼容信息。

- 项目版本：`2.0.2`
- 目标 Paper API：`26.2.build.53-alpha`
- 所需 Java 版本：`25+`
- 文档更新日期：`2026-07-08`

## 本次调整

- `paper-api` 升级到 `26.2.build.53-alpha`。
- 保留 Floodgate 支持：Java 玩家使用背包 GUI，基岩版/手机端玩家使用 Floodgate 表单；Floodgate 缺失时插件不再整体禁用。
- 新增 PlayerMenu 风格的槽位菜单配置：`title`、`size`、`openCommand`、`openItem`、`menu.*.index`。
- 支持点击动作：`[command]`、`[console]`、`[op]`、`[message]`、`[open]`、`[close]`、`[sound]`。
- 支持 `/quickmenu open`、`/quickmenu reload`、`/quickmenu clock`、`/quickmenu getMaterial`、`/quickmenu adminOpen`、`/quickmenu close`。
- 管理命令统一使用 `/quickmenu`。
- 打开物品使用 `PersistentDataContainer` 写入插件私有标记，不再按材质误判普通物品。
- 修复 README 中文乱码并补充完整使用说明。

## 验证

已在 Java 25 环境下执行：

```bash
mvn clean package
```

构建产物：

```text
target/QuickMenu.jar
```

jar 内已确认：

- `plugin.yml` 版本为 `2.0.2`。
- 默认资源包含 `menus/main.yml` 和 `menus/server.yml`。
- `plugin.yml` 包含 `softdepend: [floodgate]`，Floodgate 存在时启用手机端表单，缺失时保留 Java GUI。

## 实服验证建议

1. 将 `target/QuickMenu.jar` 放入 Paper 26.2 服务端 `plugins` 目录，并确保 Floodgate 已安装。
2. 启动后用 Java 玩家测试 `/menu`、`/quickmenu open`、`/cd` 的背包 GUI。
3. 用 Floodgate/基岩版玩家测试同样入口是否弹出手机端表单。
4. 测试 `/quickmenu clock` 发放的菜单物品右键打开主菜单。
5. 测试 `main.yml` 中 `[open] server` 的二级菜单跳转。
6. 修改 `menus/*.yml` 后执行 `/quickmenu reload`。
