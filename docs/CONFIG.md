# ScaleSQL Java 版本 - 配置说明

## 配置文件位置

主配置文件：`scalesql-api/src/main/resources/application.yml`

## 必需配置项

### 1. Hologres 数据库配置

```yaml
hologres:
  url: jdbc:postgresql://your-hologres-endpoint:80/your_database
  username: your_username
  password: your_password
```

**说明**：
- Hologres 是阿里云的实时数仓，用于向量检索和全文搜索
- 需要替换为实际的 Hologres 连接信息

### 2. LLM API 配置

```yaml
llm:
  api-key: ${OPENAI_API_KEY:your-api-key}
  base-url: https://api.openai.com
  model: gpt-3.5-turbo
```

**说明**：
- 支持 OpenAI API 和兼容的 API（如 Azure OpenAI）
- 可以通过环境变量 `OPENAI_API_KEY` 设置 API Key
- 支持的模型：gpt-3.5-turbo, gpt-4, gemini-pro 等

### 3. 嵌入模型配置

```yaml
embedding:
  model:
    path: models/all-MiniLM-L6-v2.onnx
    dimension: 384
```

**说明**：
- 使用 ONNX Runtime 运行嵌入模型
- 默认模型：all-MiniLM-L6-v2（384 维）
- 需要下载模型文件并放置到指定路径

## 可选配置项

### 数据源配置

用于存储 Schema 元数据：

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/scalesql
    username: postgres
    password: postgres
```

### 服务器配置

```yaml
server:
  port: 8080
```

## 环境变量

支持通过环境变量覆盖配置：

- `OPENAI_API_KEY` - OpenAI API Key
- `HOLOGRES_URL` - Hologres 连接 URL
- `HOLOGRES_USERNAME` - Hologres 用户名
- `HOLOGRES_PASSWORD` - Hologres 密码

## 配置示例

### 开发环境

```yaml
spring:
  profiles:
    active: dev

hologres:
  url: jdbc:postgresql://localhost:5432/test_db
  username: test_user
  password: test_password

llm:
  api-key: sk-test-key
  model: gpt-3.5-turbo
```

### 生产环境

```yaml
spring:
  profiles:
    active: prod

hologres:
  url: jdbc:postgresql://prod-hologres:80/prod_db
  username: ${HOLOGRES_USERNAME}
  password: ${HOLOGRES_PASSWORD}

llm:
  api-key: ${OPENAI_API_KEY}
  model: gpt-4
```
