# QuickMenu

一个同时支持基岩版和Java版的入门级菜单插件。

## 声明

本插件是受 [Asurin219](https://www.minebbs.com/resources/quickmenu.7649/) 在 MINEBBS 发布的 QuickMenu 插件启发而开发的开源版本。原作者的插件未开源，本项目为独立重新实现的同名插件。

感谢原作者 Asurin219 提供的创意灵感！

原插件地址：https://www.minebbs.com/resources/quickmenu.7649/

## 特性

- 🎮 同时支持 Minecraft Java版 和 基岩版
- 📋 简单易用的菜单配置
- ⚡ 支持执行多种类型的命令（普通命令、OP命令、控制台命令）
- 🔗 支持多级菜单嵌套
- 🎨 Java版使用GUI界面，基岩版使用表单界面

## 环境要求

- Java 21
- Paper 1.21.11 或更高版本
- Floodgate 插件（前置依赖）

## 安装

1. 确保服务器已安装 Floodgate 插件
2. 将 `QuickMenu.jar` 放入服务器的 `plugins` 文件夹
3. 重启服务器
4. 插件会自动生成配置文件

## 使用方法

### 对于玩家

- 玩家进入服务器时会自动获得一个钟表（菜单触发物品）
- Java版：手持钟表右键单击打开菜单
- 基岩版：手持钟表点击地面打开菜单
- 如果没有钟表，使用命令 `/menu` 也可以打开菜单

### 对于管理员

- `/quickmenu reload` - 重新加载插件配置
- `/quickmenu version` - 查看插件版本
- 权限节点：`quickmenu.admin`

## 配置文件

### 主配置文件 (config.yml)

```yaml
language: zh-CN        # 插件语言，支持 zh-CN（简体中文）
trigger_item: "CLOCK"  # 打开菜单使用的物品
```

### 菜单配置文件 (menus/*.yml)

**注意：至少需要有一个主菜单配置文件 `main.yml`**

```yaml
title: 主菜单              # 菜单标题
content: 这是一个主菜单    # 菜单文本内容（仅在基岩版显示）
menu:                      # 菜单项列表
  - text: 按钮1           # 按钮名称
    action: command       # 动作类型：command（执行命令）或 menu（打开子菜单）
    command: "say 这是按钮1"  # 要执行的命令
    java_icon:            # Java版显示的图标
      item: diamond       # 物品类型
  
  - text: 按钮2
    action: command
    command: "say 这是按钮2"
    java_icon:
      item: egg
  
  - text: 进入demo菜单
    action: menu
    menu: demo            # 子菜单文件名（不含.yml后缀）
    java_icon:
      item: gold_ingot
```


### 命令类型

插件支持三种命令执行方式：

1. **普通命令** - 以玩家身份执行
   ```yaml
   command: "say 你好"
   ```

2. **OP命令** - 以OP权限执行
   ```yaml
   command: "op:gamemode creative {player}"
   ```

3. **控制台命令** - 以控制台身份执行（最高权限）
   ```yaml
   command: "cmd:give {player} diamond 64"
   ```

**变量说明：**
- `{player}` - 会自动替换为点击菜单的玩家名字

## 示例配置

### 主菜单 (menus/main.yml)

```yaml
title: 主菜单
content: 欢迎使用菜单系统
menu:
  - text: 传送菜单
    action: menu
    menu: teleport
    java_icon:
      item: ender_pearl
  
  - text: 获得钻石
    action: command
    command: "cmd:give {player} diamond 1"
    java_icon:
      item: diamond
  
  - text: 切换创造模式
    action: command
    command: "op:gamemode creative"
    java_icon:
      item: command_block
```

### 子菜单 (menus/teleport.yml)

```yaml
title: 传送菜单
content: 选择传送地点
menu:
  - text: 传送到出生点
    action: command
    command: "spawn"
    java_icon:
      item: bed
  
  - text: 返回主菜单
    action: menu
    menu: main
    java_icon:
      item: barrier
```

## 编译

```bash
mvn clean package
```

编译后的jar文件位于 `target/QuickMenu.jar`

## 许可证

本项目采用 MIT 许可证

## 支持

如有问题或建议，请在 GitHub Issues 中提出
