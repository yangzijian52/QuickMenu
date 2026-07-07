# QuickMenu 全部指令说明

本文档按当前源码整理，适用于 QuickMenu `2.0.2`。当前版本使用 `/quickmenu` 作为管理命令。

## 权限节点

| 权限 | 默认 | 作用 |
| --- | --- | --- |
| `quickmenu.use` | 所有玩家 | 使用普通菜单命令 |
| `quickmenu.admin` | OP | 重载配置、为其他玩家打开菜单、关闭其他玩家菜单 |

## 基础打开命令

| 指令 | 权限 | 执行者 | 说明 |
| --- | --- | --- | --- |
| `/menu` | `quickmenu.use` | 玩家 | 打开主菜单。优先打开 `menus/main.yml`，如果没有 `main.yml`，打开第一个被加载的菜单。 |
| `/menu <菜单名>` | `quickmenu.use` | 玩家 | 打开指定菜单。`<菜单名>` 是 `plugins/QuickMenu/menus/` 下的文件名，不带 `.yml`，例如 `/menu tp` 打开 `tp.yml`。 |
| `/playermenu` | `quickmenu.use` | 玩家 | `/menu` 的别名，用法相同。 |

说明：

- Java 玩家会打开箱子 GUI。
- Floodgate/基岩版玩家会收到手机端表单。
- 如果菜单文件里配置了 `permission`，玩家还必须拥有该菜单权限。

## `/quickmenu`

`/quickmenu` 是管理命令入口。

| 指令 | 权限 | 执行者 | 说明 |
| --- | --- | --- | --- |
| `/quickmenu` | `quickmenu.use` | 玩家/控制台 | 显示帮助。控制台只能查看帮助和执行部分管理命令。 |
| `/quickmenu open` | `quickmenu.use` | 玩家 | 打开主菜单。 |
| `/quickmenu open <菜单名>` | `quickmenu.use` | 玩家 | 打开指定菜单，例如 `/quickmenu open money`。 |
| `/quickmenu clock` | `quickmenu.use` | 玩家 | 获取主菜单打开物品。默认是带插件标记的钟表。 |
| `/quickmenu clock <菜单名>` | `quickmenu.use` | 玩家 | 获取指定菜单的打开物品。右键后打开该菜单。 |
| `/quickmenu item` | `quickmenu.use` | 玩家 | `/quickmenu clock` 的别名。 |
| `/quickmenu item <菜单名>` | `quickmenu.use` | 玩家 | `/quickmenu clock <菜单名>` 的别名。 |
| `/quickmenu getMaterial` | `quickmenu.use` | 玩家 | 查看主手物品的 Bukkit 材质名，用于填写菜单配置里的 `material`。 |
| `/quickmenu close` | `quickmenu.use` | 玩家 | 关闭自己的当前菜单界面。 |
| `/quickmenu version` | `quickmenu.use` | 玩家/控制台 | 查看 QuickMenu 版本。 |
| `/quickmenu reload` | `quickmenu.admin` | 玩家/控制台 | 重载 `config.yml`、语言文件和 `menus/*.yml`。 |
| `/quickmenu adminOpen <玩家>` | `quickmenu.admin` | 玩家/控制台 | 为指定在线玩家打开主菜单。 |
| `/quickmenu adminOpen <玩家> <菜单名>` | `quickmenu.admin` | 玩家/控制台 | 为指定在线玩家打开指定菜单。 |
| `/quickmenu close <玩家>` | `quickmenu.admin` | 玩家/控制台 | 关闭指定在线玩家当前打开的菜单界面。 |

注意：

- `<菜单名>` 不带 `.yml`。
- `<玩家>` 必须是在线玩家名，当前实现使用精确匹配。
- `/quickmenu close <玩家>` 只有拥有 `quickmenu.admin` 时才会关闭别人菜单；普通玩家执行 `/quickmenu close xxx` 不会获得管理能力。

## 动态菜单命令

每个菜单文件都可以配置：

```yaml
openCommand: "cd"
```

配置后，玩家执行：

```text
/cd
```

就会打开这个菜单。

示例：

| 菜单文件 | 配置 | 实际命令 |
| --- | --- | --- |
| `main.yml` | `openCommand: "cd"` | `/cd` |
| `tp.yml` | `openCommand: "tpmenu"` | `/tpmenu` |
| `money.yml` | `openCommand: "moneymenu"` | `/moneymenu` |
| `fly.yml` | `openCommand: "flymenu"` | `/flymenu` |

说明：

- `openCommand` 不需要写 `/`。
- 如果多个菜单配置了同一个 `openCommand`，后加载的菜单会覆盖前面的映射，不建议重复。
- 动态命令通过玩家命令监听实现，不会出现在 `plugin.yml` 的静态命令列表里。

## 菜单点击动作

菜单按钮的 `commands` 支持多个动作，按顺序执行。

```yaml
commands:
  - "[message] &a欢迎你，{player}"
  - "[open] server"
```

| 动作 | 示例 | 说明 |
| --- | --- | --- |
| `[command]` | `[command] spawn` | 让玩家以自己身份执行命令。不要写开头 `/`。 |
| `[console]` | `[console] say {player}` | 让控制台执行命令。适合发奖励、执行管理命令。 |
| `[op]` | `[op] gamemode creative` | 临时给玩家 OP 后执行命令，执行后恢复原 OP 状态。慎用。 |
| `[message]` | `[message] &a你好` | 给玩家发送一条支持 `&` 颜色代码的消息。 |
| `[open]` | `[open] server` | 打开另一个菜单。参数是菜单名，不带 `.yml`。 |
| `[close]` | `[close]` | 关闭玩家当前菜单。 |
| `[sound]` | `[sound] UI_BUTTON_CLICK` | 给玩家播放音效。音效名会自动按 Bukkit/Paper 注册表解析。 |
| 无前缀命令 | `spawn` | 兼容写法，等同 `[command] spawn`。 |
| `cmd:` | `cmd:say hello` | 兼容旧写法，等同 `[console] say hello`。 |
| `op:` | `op:fly` | 兼容旧写法，等同 `[op] fly`。 |

## 占位符

以下占位符会在菜单名称、Lore 和动作命令中替换：

| 占位符 | 替换内容 |
| --- | --- |
| `{player}` | 玩家名 |
| `%player_name%` | 玩家名 |
| `%player%` | 玩家名 |

示例：

```yaml
commands:
  - "[console] eco give {player} 100"
  - "[message] &a已给你发放 100 金币。"
```

## 打开物品

每个菜单都可以配置一个打开物品：

```yaml
openItem:
  enable: true
  material: CLOCK
  name: "&a功能菜单 &7(右键)"
  slot: 8
```

字段说明：

| 字段 | 说明 |
| --- | --- |
| `enable` | 是否在玩家进服时自动发放这个菜单物品。 |
| `material` | 物品材质名，例如 `CLOCK`、`COMPASS`。 |
| `name` | 物品显示名，支持 `&` 颜色代码。 |
| `slot` | 进服自动发放时优先放入的背包槽位；该槽位被占用时会自动放入背包其他位置。 |

安全说明：

- QuickMenu 只识别插件发放、带 `open_menu` 标记的物品。
- 普通玩家自己合成或获得的钟表不会被误判为菜单物品。

## 菜单配置字段

| 字段 | 位置 | 说明 |
| --- | --- | --- |
| `title` | 菜单根节点 | 菜单标题。Java 版显示为 GUI 标题，基岩版显示为表单标题。 |
| `size` | 菜单根节点 | Java GUI 格数，会自动限制到 `9-54` 并对齐到 9 的倍数。 |
| `permission` | 菜单根节点或按钮节点 | 需要的权限。根节点限制整个菜单，按钮节点限制单个按钮。 |
| `openCommand` | 菜单根节点 | 该菜单的动态打开命令。 |
| `menu.<按钮>.index` | 按钮节点 | Java GUI 槽位，从 `0` 开始。 |
| `menu.<按钮>.name` | 按钮节点 | 按钮显示名。 |
| `menu.<按钮>.material` | 按钮节点 | 按钮材质名。 |
| `menu.<按钮>.amount` | 按钮节点 | 物品数量，范围 `1-64`。 |
| `menu.<按钮>.lore` | 按钮节点 | 按钮说明文本。 |
| `menu.<按钮>.commands` | 按钮节点 | 点击后执行的动作列表。 |
| `menu.<按钮>.isEnchant` | 按钮节点 | 是否显示附魔发光效果。 |
| `menu.<按钮>.hideFlag` | 按钮节点 | 是否隐藏物品属性/附魔标签。 |
| `menu.<按钮>.customModelData` | 按钮节点 | 自定义模型数据。也兼容 `custom-model-data`。 |
| `menu.<按钮>.head` | 按钮节点 | 玩家头颅拥有者。可写 `{player}`。 |

## 双端显示规则

Java 玩家：

- 看到完整箱子 GUI。
- 所有设置了 `index` 的按钮按槽位显示。
- 没有 `commands` 的物品可作为装饰或说明。

Floodgate/基岩版玩家：

- 收到手机端表单。
- 只有配置了 `commands` 的按钮会转换为可点击表单按钮。
- 没有 `commands` 的说明项会把 `lore` 汇总到表单正文里。
