# Tibetan-Chess-JJ

![Java](https://img.shields.io/badge/Java-17-blue) ![Swing](https://img.shields.io/badge/GUI-Swing-orange)

一个基于Java Swing的五子棋游戏，支持人机对战和AI自动对弈，包含多种AI算法和游戏模式。

## 项目结构

## 项目结构 (Project Structure)

```text
src/
│
├── board/                  # 游戏核心组件 | Game Core Components
│   ├── Windows.java        # 主界面和游戏逻辑 | Main UI & Game Logic
│   └── Core.java           # 核心游戏规则实现 | Core Game Rules Implementation
│
├── ai/                     # 人工智能模块 | AI Modules
│   ├── fallChessAI.java    # 落子阶段AI | Piece Placement AI
│   ├── walkChessAI.java    # 行子阶段AI | Piece Movement AI
│   └── flyChessAI.java     # 飞子阶段AI | Flying Piece AI
```

## 功能特性

- 🎮 **多种游戏模式**：
  - 人人对战
  - AI执白（玩家执黑）
  - AI执黑（玩家执白）
  - AI自动对弈

- 🤖 **智能AI系统**：
  - 落子阶段AI（`fallChessAI`）
  - 行子阶段AI（`walkChessAI`）
  - 飞子阶段AI（`flyChessAI`）
  - 支持局面评估函数

- 🖥️ **图形界面**：
  - 14×14标准棋盘
  - 实时信息显示区
  - 完整的游戏控制按钮

- ⚙️ **游戏功能**：
  - 移动/吃子操作
  - 悔棋功能
  - 棋盘复盘
  - 游戏状态保存

## 快速开始

1. **环境要求**：
   - JDK 17或更高版本

2. **运行游戏**：
   ```bash
   javac board/Main.java
   java board.Main

## Copyright
-这个项目仅作用于娱乐，不作用于商业等有关于利益的场景，若要使用请引用此项目
