# Claude 项目配置

## 项目信息

**项目名称**: Agentar-Scale-SQL Java 重写版本

**项目描述**: 将 Python 实现的 Text-to-SQL 框架使用 Java 8 和 Spring Boot 2.7.10 进行完整重写

## 重要说明

**语言要求**: 所有与用户的交流、代码注释、文档、提交信息等，统一使用**中文输出**

## 技术栈

- Java 8
- Spring Boot 2.7.10
- **Hologres** (阿里云实时数仓 - 向量检索 + 全文搜索 + 关系数据)
- **MyBatis-Plus** (数据访问层 - CRUD + 向量查询)
- Spring AI (LLM 集成)
- ONNX Runtime (嵌入模型 all-MiniLM-L6-v2)
- **Guava** + **Hutool** (工具库)
- Maven 多模块项目

## 模块结构

1. **scalesql-core** - 核心领域模型和接口
2. **scalesql-data** - 数据访问层（PostgreSQL、向量搜索、全文搜索）
3. **scalesql-llm** - LLM 集成（Spring AI）
4. **scalesql-workflows** - 预处理流程和检索模块
5. **scalesql-api** - REST API 层

## 设计原则

- 保持与 Python 版本功能对等
- 使用 Spring Boot 最佳实践
- 代码注释和文档使用中文
- 遵循 Java 8 标准
