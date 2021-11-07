package com.bluslee.mundo.xml;

import com.bluslee.mundo.xml.base.BaseXmlSchema;
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
@XStreamAlias("mundo")
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

        @XStreamImplicit(itemFieldName = "start")
        @Size(min = 1, max = 1000, message = "开始节点数量只能在{min}-{max}之间")
        @NotNull(message = "开始节点不能为空")
        protected List<ProcessStartNodeSchema> startList;

        /**
         * 流程节点集合
         */
        @XStreamImplicit(itemFieldName = "activity")
        @Size(min = 1, max = 1000, message = "流程节点数量只能在{min}-{max}之间")
        @NotNull(message = "流程节点不能为空")
        protected List<ProcessNodeSchema> activityList;

        /**
         * 排他网关节点集合
         */
        @XStreamImplicit(itemFieldName = "exclusiveGateway")
        @Size(min = 1, max = 1000, message = "节点数量只能在{min}-{max}之间")
        protected List<ProcessExclusiveGatewaySchema> exclusiveGatewayList;

        /**
         * 流程link集合
         */
        @XStreamImplicit(itemFieldName = "link")
        @Size(min = 1, max = 1000, message = "流程link数量只能在{min}-{max}之间")
        @NotNull(message = "流程link不能为空")
        protected List<ProcessLinkSchema> linkList;

        @XStreamImplicit(itemFieldName = "end")
        @Size(min = 1, max = 1000, message = "结束节点数量只能在{min}-{max}之间")
        @NotNull(message = "结束节点不能为空")
        protected List<ProcessEndNodeSchema> endList;


        public List<ProcessNodeSchema> getActivityList() {
            return activityList;
        }

        public void setActivityList(List<ProcessNodeSchema> activityList) {
            this.activityList = activityList;
        }

        public List<ProcessExclusiveGatewaySchema> getExclusiveGatewayList() {
            return exclusiveGatewayList;
        }

        public void setExclusiveGatewayList(List<ProcessExclusiveGatewaySchema> exclusiveGatewayList) {
            this.exclusiveGatewayList = exclusiveGatewayList;
        }

        public List<ProcessLinkSchema> getLinkList() {
            return linkList;
        }

        public void setLinkList(List<ProcessLinkSchema> linkList) {
            this.linkList = linkList;
        }

        public List<ProcessStartNodeSchema> getStartList() {
            return startList;
        }

        public void setStartList(List<ProcessStartNodeSchema> startList) {
            this.startList = startList;
        }

        public List<ProcessEndNodeSchema> getEndList() {
            return endList;
        }

        public void setEndList(List<ProcessEndNodeSchema> endList) {
            this.endList = endList;
        }
    }

    /**
     * 开始节点bean
     */
    public static class ProcessStartNodeSchema extends BaseXmlSchema {
        public ProcessStartNodeSchema(String id, String name) {
            super(id, name);
        }

        public ProcessStartNodeSchema() {
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
        protected String conditionExpression;

        public ProcessLinkSchema(String id, String name, String sourceId, String targetId) {
            super(id, name);
            this.sourceId = sourceId;
            this.targetId = targetId;
        }

        public ProcessLinkSchema(String sourceId, String targetId) {
            this.sourceId = sourceId;
            this.targetId = targetId;
        }

        public ProcessLinkSchema(String id, String name, String sourceId, String targetId, String conditionExpression) {
            super(id, name);
            this.sourceId = sourceId;
            this.targetId = targetId;
            this.conditionExpression = conditionExpression;
        }

        public ProcessLinkSchema() {
        }

        public String getSourceId() {
            return sourceId;
        }

        public void setSourceId(String sourceId) {
            this.sourceId = sourceId;
        }

        public String getTargetId() {
            return targetId;
        }

        public void setTargetId(String targetId) {
            this.targetId = targetId;
        }

        public String getConditionExpression() {
            return conditionExpression;
        }

        public void setConditionExpression(String conditionExpression) {
            this.conditionExpression = conditionExpression;
        }
    }

    /**
     * 结束节点bean
     */
    public static class ProcessEndNodeSchema extends BaseXmlSchema {
        public ProcessEndNodeSchema(String id, String name) {
            super(id, name);
        }

        public ProcessEndNodeSchema() {
        }
    }


    public List<ProcessSchema> getProcessList() {
        return processList;
    }

    public void setProcessList(List<ProcessSchema> processList) {
        this.processList = processList;
    }
}
