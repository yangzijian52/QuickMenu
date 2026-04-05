# QuickMenu

一个同时支持 Minecraft Java 版与基岩版的入门级菜单插件。

## 项目说明

本插件是基于同名创意重新实现的开源版本，面向 Paper 服务器使用，Java 玩家可使用 GUI 菜单，基岩版玩家可通过 Floodgate 表单打开菜单。

## 运行要求

- Java 25+
- Paper 26.1.x
- Floodgate

## 当前版本

- 插件版本：`1.0.1`
- Paper API：`26.1.1.build.20-alpha`

## 主要功能

- 同时支持 Java 版与基岩版玩家
- 支持多级菜单嵌套
- 支持玩家命令、OP 命令、控制台命令
- 支持菜单语言文件与独立菜单配置文件

## 安装方法

1. 先安装 `Floodgate`
2. 将 `QuickMenu.jar` 放入服务器的 `plugins` 目录
3. 启动服务器生成配置文件
4. 按需修改 `config.yml` 与 `menus/*.yml`

## 常用命令

- `/menu`：打开菜单
- `/quickmenu reload`：重载配置
- `/quickmenu version`：查看版本

权限节点：

- `quickmenu.admin`

## 配置说明

主配置文件：

- `plugins/QuickMenu/config.yml`

菜单配置目录：

- `plugins/QuickMenu/menus/`

至少需要保留一个主菜单文件 `main.yml`。

## 26.1 升级说明

- 已升级到 `paper-api 26.1.1.build.20-alpha`
- 编译与运行环境调整为 `Java 25+`
- `plugin.yml` 版本号改为自动读取 Maven 版本
- 详细说明见 [docs/PAPER-26.1-UPGRADE.md](docs/PAPER-26.1-UPGRADE.md)

## 构建

```bash
mvn clean package
```

构建产物默认位于：

- `target/QuickMenu.jar`

## 许可证

MIT
