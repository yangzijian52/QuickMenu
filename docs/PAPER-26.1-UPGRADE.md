# QuickMenu Paper 26.1 升级说明

## 概要

本文档用于记录 `QuickMenu` 升级到 Paper 26.1 的兼容信息。

- 项目版本：`1.0.1`
- 目标 Paper API：`26.1.1.build.20-alpha`
- 所需 Java 版本：`25+`
- 升级日期：`2026-04-05`

## 本次调整

- `paper-api` 从 `1.21.11-R0.1-SNAPSHOT` 升级到 `26.1.1.build.20-alpha`
- Maven 编译目标从 Java 21 升级到 Java 25
- `plugin.yml` 版本号改为从 Maven `project.version` 自动注入
- README 与升级说明改为中文并同步更新运行要求

## 说明

- 截至 `2026-04-05`，PaperMC Maven 中 `paper-api` 的 `latest` 与 `release` 仍指向 `26.1.1.build.20-alpha`
- 本插件仍使用 `plugin.yml` 作为入口，本次升级不需要切换到 `paper-plugin.yml`
- 本次升级以依赖、Java 版本和文档同步为主，源码层面未发现必须针对 26.1 单独修改的接口问题
- 当前只完成了本地编译验证，尚未完成服务端完整功能测试
- 未完成测试的主要原因是前置依赖 `Floodgate` 还没有在当前测试环境中完成联调，导致基岩版表单与相关菜单流程暂时无法确认是否可用

## 验证建议

1. 使用 Java 25 运行 `mvn package`。
2. 在 Paper 26.1 服务端测试 `/menu` 与 `/quickmenu reload`。
3. 同时验证 Java 版 GUI 与基岩版 Floodgate 表单是否都能正常打开。
