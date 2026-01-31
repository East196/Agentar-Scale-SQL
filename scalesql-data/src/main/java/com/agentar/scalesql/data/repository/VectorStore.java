package com.agentar.scalesql.data.repository;

import com.agentar.scalesql.core.model.RetrievalRequest;
import com.agentar.scalesql.core.model.RetrievalResponse;

import java.util.List;

/**
 * 向量存储接口
 * 定义向量检索的基本操作
 *
 * @author ScaleSQL Team
 */
public interface VectorStore {

    /**
     * 连接到向量存储
     */
    void connect();

    /**
     * 断开连接
     */
    void disconnect();

    /**
     * 添加文档
     *
     * @param documents 文档列表
     * @return 文档 ID 列表
     */
    List<String> addDocuments(List<Document> documents);

    /**
     * 搜索相似文档
     *
     * @param request 检索请求
     * @return 检索响应
     */
    RetrievalResponse search(RetrievalRequest request);

    /**
     * 文档模型
     */
    class Document {
        private String content;
        private java.util.Map<String, Object> metadata;

        public Document(String content, java.util.Map<String, Object> metadata) {
            this.content = content;
            this.metadata = metadata;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public java.util.Map<String, Object> getMetadata() {
            return metadata;
        }

        public void setMetadata(java.util.Map<String, Object> metadata) {
            this.metadata = metadata;
        }
    }
}
