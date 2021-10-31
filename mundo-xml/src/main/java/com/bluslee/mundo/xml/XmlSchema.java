package com.bluslee.mundo.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author carl.che
 * @date 2021/10/29
 * @description XmlSchema 解析XML配置文件对应的bean
 */
@Data
@XStreamAlias("definitions")
public class XmlSchema {

    /**
     * 多个流程定义
     */
    @XStreamImplicit(itemFieldName = "process")
    @Size(min = 1, max = 1000, message = "process最多配置{min}-{max}个流程")
    @Valid
    protected List<ProcessSchema> processList;

    /**
     * 流程xml定义
     */
    public static class ProcessSchema extends BaseXmlSchema {

        public ProcessSchema(String id, String name) {
            super(id, name);
        }

        public ProcessSchema() {
        }

        /**
         * 流程节点集合
         */
        @XStreamImplicit(itemFieldName = "processNode")
        @Size(min = 1, max = 1000, message = "流程节点数量只能在{min}-{max}之间")
        @NotNull(message = "流程节点不能为空")
        protected List<ProcessNodeSchema> processNodeList;

        /**
         * 排他网关节点集合
         */
        @XStreamImplicit(itemFieldName = "processExclusiveGateway")
        @Size(min = 1, max = 1000, message = "节点数量只能在{min}-{max}之间")
        protected List<ProcessExclusiveGatewaySchema> processExclusiveGatewayList;

        /**
         * 流程link集合
         */
        @XStreamImplicit(itemFieldName = "processLink")
        @Size(min = 1, max = 1000, message = "流程link数量只能在{min}-{max}之间")
        @NotNull(message = "流程link不能为空")
        protected List<ProcessLinkSchema> processLinkSchemaList;

        public List<ProcessNodeSchema> getProcessNodeList() {
            return processNodeList;
        }

        public void setProcessNodeList(List<ProcessNodeSchema> processNodeList) {
            this.processNodeList = processNodeList;
        }

        public List<ProcessExclusiveGatewaySchema> getProcessExclusiveGatewayList() {
            return processExclusiveGatewayList;
        }

        public void setProcessExclusiveGatewayList(List<ProcessExclusiveGatewaySchema> processExclusiveGatewayList) {
            this.processExclusiveGatewayList = processExclusiveGatewayList;
        }

        public List<ProcessLinkSchema> getProcessLinkSchemaList() {
            return processLinkSchemaList;
        }

        public void setProcessLinkSchemaList(List<ProcessLinkSchema> processLinkSchemaList) {
            this.processLinkSchemaList = processLinkSchemaList;
        }
    }

    /**
     * 流程节点bean
     */
    public static class ProcessNodeSchema extends BaseXmlSchema {
        public ProcessNodeSchema(String id, String name) {
            super(id, name);
        }

        public ProcessNodeSchema() {
        }
    }

    /**
     * 流程网关节点bean
     */
    public static class ProcessExclusiveGatewaySchema extends BaseXmlSchema {
        public ProcessExclusiveGatewaySchema(String id, String name) {
            super(id, name);
        }

        public ProcessExclusiveGatewaySchema() {
        }
    }

    /**
     * 流程link bean
     */
    public static class ProcessLinkSchema extends BaseXmlSchema {

        /**
         * from node id
         */
        @XStreamAsAttribute
        @NotBlank(message = "sourceId不能为空")
        @Length(max = 100, message = "sourceId长度不能超过{max}")
        protected String sourceId;

        /**
         * to node id
         */
        @XStreamAsAttribute
        @NotBlank(message = "targetId不能为空")
        @Length(max = 100, message = "targetId长度不能超过{max}")
        protected String targetId;

        /**
         * 表达式
         */
        @Length(max = 200, message = "表达式长度不能超过{max}")
        protected String conditionExpress;

        public ProcessLinkSchema(String id, String name, String sourceId, String targetId) {
            super(id, name);
            this.sourceId = sourceId;
            this.targetId = targetId;
        }

        public ProcessLinkSchema(String sourceId, String targetId) {
            this.sourceId = sourceId;
            this.targetId = targetId;
        }

        public ProcessLinkSchema(String id, String name, String sourceId, String targetId, String conditionExpress) {
            super(id, name);
            this.sourceId = sourceId;
            this.targetId = targetId;
            this.conditionExpress = conditionExpress;
        }

        public ProcessLinkSchema() {
        }
    }
}
