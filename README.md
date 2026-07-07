# QuickMenu

QuickMenu 是一款面向 Paper 26.2 的双端玩家菜单插件，功能形态参考 PlayerMenu 文档重新实现：

- Java 版玩家通过背包 GUI 展示菜单。
- 基岩版/手机端玩家通过 Floodgate 发送表单菜单。
- 支持每个菜单独立 `openCommand`，例如 `/cd`。
- 管理命令统一使用 `/quickmenu`。
- 支持右键菜单物品打开主菜单。
- 支持按槽位配置按钮、材质、名称、Lore、发光、权限和点击动作。
- 支持多级菜单跳转。

本项目没有复制 PlayerMenu 源码，只按公开文档实现相似的菜单功能，并保留原 QuickMenu 的双端交互链路。

## 运行要求

- Java 25+
- Paper API `26.2.build.53-alpha`
- Paper 26.2 服务端
- Floodgate。未安装时 Java 菜单仍可用，手机端表单不可用。

## 构建

```bash
mvn clean package
```

产物：

```text
target/QuickMenu.jar
```

## 安装

1. 先安装 Floodgate。
2. 将 `target/QuickMenu.jar` 放入服务端 `plugins` 目录。
3. 启动服务端生成默认配置。
4. 修改 `plugins/QuickMenu/menus/*.yml` 后执行 `/quickmenu reload`。

## 命令

完整指令和参数说明见 [docs/COMMANDS.md](docs/COMMANDS.md)。

| 命令 | 权限 | 说明 |
| --- | --- | --- |
| `/menu [菜单]` | `quickmenu.use` | 打开主菜单或指定菜单 |
| `/quickmenu open [菜单]` | `quickmenu.use` | 打开主菜单或指定菜单 |
| `/quickmenu clock [菜单]` | `quickmenu.use` | 获取菜单打开物品 |
| `/quickmenu getMaterial` | `quickmenu.use` | 查看手中物品材质名 |
| `/quickmenu reload` | `quickmenu.admin` | 重载配置 |
| `/quickmenu adminOpen <玩家> [菜单]` | `quickmenu.admin` | 为指定玩家打开菜单 |
| `/quickmenu close [玩家]` | `quickmenu.use` / `quickmenu.admin` | 关闭自己或指定玩家菜单 |

## 菜单配置示例

```yaml
title: "&0玩家菜单"
size: 54
permission: ""
openCommand: "cd"

openItem:
  enable: true
  material: CLOCK
  name: "&a玩家菜单 &7(右键)"
  slot: 8

menu:
  spawn:
    index: 20
    name: "&a返回主城"
    material: GRASS_BLOCK
    lore:
      - "&7点击执行 &f/spawn"
    commands:
      - "[command] spawn"
```

## 支持的点击动作

```text
[command] spawn        玩家身份执行命令
[console] say {player} 控制台执行命令
[op] gamemode creative 临时 OP 执行命令
[message] &a文本       给玩家发送消息
[open] server          打开另一个菜单
[close]                关闭菜单
[sound] UI_BUTTON_CLICK 播放音效
```

支持占位符：

```text
{player}
%player_name%
%player%
```

## 默认菜单

- `menus/main.yml`：主菜单，默认打开命令 `/cd`。
- `menus/server.yml`：二级菜单，默认打开命令 `/servermenu`。

Java 玩家会看到 54 格/27 格背包 GUI；Floodgate 玩家会收到同一菜单转换后的手机端表单，点击按钮后执行相同的 `commands` 动作。
