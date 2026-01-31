# ScaleSQL Java 重写版本

Text-to-SQL 框架的 Java 实现，基于 Spring Boot 2.7.10

## 项目结构

```
scalesql-parent/
├── scalesql-core/          # 核心领域模型和接口
├── scalesql-data/          # 数据访问层（Hologres + MyBatis-Plus）
├── scalesql-llm/           # LLM 集成（Spring AI + ONNX Runtime）
├── scalesql-workflows/     # 预处理流程和检索模块
└── scalesql-api/           # REST API 层
```

## 技术栈

- **Java 8**
- **Spring Boot 2.7.10**
- **Hologres** - 阿里云实时数仓（向量检索 + 全文搜索 + 关系数据）
- **MyBatis-Plus** - 数据访问层
- **Spring AI** - LLM 集成
- **ONNX Runtime** - 嵌入模型（all-MiniLM-L6-v2）
- **Guava + Hutool** - 工具库

## 模块说明

### scalesql-core
核心领域模型和接口定义，不依赖任何其他模块。

**主要内容**：
- 领域模型（Schema、Table、Column 等）
- 核心接口定义
- 通用工具类

### scalesql-data
数据访问层，负责与 Hologres 数据库交互。

**主要功能**：
- PostgreSQL/Hologres 连接管理
- 向量检索（使用 Hologres 向量索引）
- 全文搜索
- Schema 元数据管理
- MyBatis-Plus CRUD 操作

### scalesql-llm
LLM 集成模块，负责与大语言模型交互。

**主要功能**：
- Spring AI 集成（支持 OpenAI、Gemini 等）
- ONNX Runtime 嵌入模型
- Prompt 模板管理
- LLM 调用封装

### scalesql-workflows
预处理流程和检索模块，实现核心业务逻辑。

**主要功能**：
- Schema 生成（LightSchema）
- 关键词提取
- 数据库单元检索
- 骨架检索（Skeleton Retrieval）
- SQL 生成流程

### scalesql-api
REST API 层，提供 HTTP 接口。

**主要功能**：
- Text-to-SQL API
- Schema 管理 API
- 健康检查
- 监控指标

## 构建项目

```bash
# 编译项目
mvn clean compile

# 运行测试
mvn test

# 打包
mvn clean package

# 跳过测试打包
mvn clean package -DskipTests
```

## 运行项目

```bash
# 运行 API 服务
cd scalesql-api
mvn spring-boot:run
```

## 配置说明

主要配置文件位于 `scalesql-api/src/main/resources/application.yml`

**必需配置**：
- Hologres 数据库连接信息
- LLM API 密钥（OpenAI、Gemini 等）
- ONNX 模型文件路径

## 开发指南

### 代码规范
- 所有代码注释使用中文
- 遵循 Java 8 标准
- 使用 Lombok 简化代码
- 使用 Hutool 工具类

### 分支策略
- `main` - 主分支
- `develop` - 开发分支
- `feature/*` - 功能分支

## 与 Python 版本的对应关系

| Python 模块 | Java 模块 | 说明 |
|------------|----------|------|
| `ScaleSQL/modules/light_schema.py` | `scalesql-workflows` | Schema 生成 |
| `ScaleSQL/modules/retrieve.py` | `scalesql-workflows` | 检索模块 |
| `ScaleSQL/retrievers/chroma.py` | `scalesql-data` | 向量检索（改用 Hologres） |
| `ScaleSQL/workflows/schema_generation.py` | `scalesql-workflows` | Schema 生成工作流 |

## 许可证

参见 [LICENSE](LICENSE) 文件
