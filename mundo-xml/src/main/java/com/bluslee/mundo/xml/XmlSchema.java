package com.bluslee.mundo.xml;

import com.bluslee.mundo.xml.base.BaseXmlSchema;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import java.util.List;
import java.util.Objects;

/**
 * 解析XML配置文件对应的bean.
 *
 * @author carl.che
 */
@XStreamAlias("mundo")
public class XmlSchema {

    /**
     * 多个流程定义.
     */
    @XStreamImplicit(itemFieldName = "process")
    @Size(min = 1, max = 1000, message = "process最多配置{min}-{max}个流程")
    @Valid
    private List<ProcessSchema> processList;

    /**
     * 根据processList判断是否相等.
     *
     * @param o 比较的对象
     * @return true 相等，false 不相等
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        XmlSchema xmlSchema = (XmlSchema) o;
        return processList.equals(xmlSchema.processList);
    }

    /**
     * 根据processList生成hashcode.
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(processList);
    }

    public List<ProcessSchema> getProcessList() {
        return processList;
    }

    public void setProcessList(final List<ProcessSchema> processList) {
        this.processList = processList;
    }

    /**
     * 流程xml定义.
     */
    public static class ProcessSchema extends BaseXmlSchema {

        /**
         * 版本号.
         */
        @XStreamAsAttribute
        private Integer version = 0;

        @XStreamImplicit(itemFieldName = "start")
        @Size(min = 1, max = 1000, message = "start开始节点数量只能在{min}-{max}之间")
        @NotNull(message = "开始节点不能为空")
        private List<ProcessStartNodeSchema> startList;

        /**
         * 流程节点集合.
         */
        @XStreamImplicit(itemFieldName = "activity")
        @Size(min = 1, max = 1000, message = "activity流程节点数量只能在{min}-{max}之间")
        @NotNull(message = "流程节点不能为空")
        private List<ProcessNodeSchema> activityList;

        /**
         * 排他网关节点集合.
         */
        @XStreamImplicit(itemFieldName = "exclusiveGateway")
        @Size(min = 1, max = 1000, message = "exclusiveGateway节点数量只能在{min}-{max}之间")
        private List<ProcessExclusiveGatewaySchema> exclusiveGatewayList;

        /**
         * 并行网关节点集合.
         */
        @XStreamImplicit(itemFieldName = "parallelGateway")
        @Size(min = 1, max = 1000, message = "parallelGateway节点数量只能在{min}-{max}之间")
        private List<ProcessParallelGatewaySchema> parallelGatewayList;

        /**
         * 流程link集合.
         */
        @XStreamImplicit(itemFieldName = "link")
        @Size(min = 1, max = 1000, message = "link数量只能在{min}-{max}之间")
        @NotNull(message = "流程link不能为空")
        private List<ProcessLinkSchema> linkList;

        /**
         * 结束节点集合.
         */
        @XStreamImplicit(itemFieldName = "end")
        @Size(min = 1, max = 1000, message = "end节点数量只能在{min}-{max}之间")
        @NotNull(message = "结束节点不能为空")
        private List<ProcessEndNodeSchema> endList;

        public ProcessSchema(final String id, final String name) {
            super(id, name);
        }

        public ProcessSchema() {
        }

        public List<ProcessNodeSchema> getActivityList() {
            return activityList;
        }

        public void setActivityList(final List<ProcessNodeSchema> activityList) {
            this.activityList = activityList;
        }

        public List<ProcessExclusiveGatewaySchema> getExclusiveGatewayList() {
            return exclusiveGatewayList;
        }

        public void setExclusiveGatewayList(final List<ProcessExclusiveGatewaySchema> exclusiveGatewayList) {
            this.exclusiveGatewayList = exclusiveGatewayList;
        }

        public List<ProcessParallelGatewaySchema> getParallelGatewayList() {
            return parallelGatewayList;
        }

        public void setParallelGatewayList(final List<ProcessParallelGatewaySchema> parallelGatewayList) {
            this.parallelGatewayList = parallelGatewayList;
        }

        public List<ProcessLinkSchema> getLinkList() {
            return linkList;
        }

        public void setLinkList(final List<ProcessLinkSchema> linkList) {
            this.linkList = linkList;
        }

        public List<ProcessStartNodeSchema> getStartList() {
            return startList;
        }

        public void setStartList(final List<ProcessStartNodeSchema> startList) {
            this.startList = startList;
        }

        public List<ProcessEndNodeSchema> getEndList() {
            return endList;
        }

        public void setEndList(final List<ProcessEndNodeSchema> endList) {
            this.endList = endList;
        }

        public Integer getVersion() {
            return version;
        }

        public void setVersion(final Integer version) {
            this.version = version;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ProcessSchema that = (ProcessSchema) o;
            return Objects.equals(version, that.version)
                    && Objects.equals(startList, that.startList)
                    && Objects.equals(activityList, that.activityList)
                    && Objects.equals(exclusiveGatewayList, that.exclusiveGatewayList)
                    && Objects.equals(linkList, that.linkList)
                    && Objects.equals(endList, that.endList);
        }

        @Override
        public int hashCode() {
            return Objects.hash(version, startList, activityList, exclusiveGatewayList, linkList, endList);
        }

    }

    /**
     * 开始节点bean.
     */
    public static class ProcessStartNodeSchema extends BaseXmlSchema {
        public ProcessStartNodeSchema(final String id, final String name) {
            super(id, name);
        }

        public ProcessStartNodeSchema() {
        }
    }

    /**
     * 流程节点bean.
     */
    public static class ProcessNodeSchema extends BaseXmlSchema {
        public ProcessNodeSchema(final String id, final String name) {
            super(id, name);
        }

        public ProcessNodeSchema() {
        }
    }

    /**
     * 流程排他网关节点bean.
     */
    public static class ProcessExclusiveGatewaySchema extends BaseXmlSchema {
        public ProcessExclusiveGatewaySchema(final String id, final String name) {
            super(id, name);
        }

        public ProcessExclusiveGatewaySchema() {
        }
    }

    /**
     * 流程并行网关节点bean.
     */
    public static class ProcessParallelGatewaySchema extends BaseXmlSchema {
        public ProcessParallelGatewaySchema(final String id, final String name) {
            super(id, name);
        }

        public ProcessParallelGatewaySchema() {
        }
    }

    /**
     * 流程link bean.
     */
    public static class ProcessLinkSchema extends BaseXmlSchema {

        /**
         * from node id.
         */
        @XStreamAsAttribute
        @NotBlank(message = "sourceId不能为空")
        @Length(max = 100, message = "sourceId长度不能超过{max}")
        private String sourceId;

        /**
         * to node id.
         */
        @XStreamAsAttribute
        @NotBlank(message = "targetId不能为空")
        @Length(max = 100, message = "targetId长度不能超过{max}")
        private String targetId;

        /**
         * 表达式.
         */
        @Length(max = 200, message = "表达式长度不能超过{max}")
        private String conditionExpression;

        public ProcessLinkSchema(final String id, final String name, final String sourceId, final String targetId) {
            super(id, name);
            this.sourceId = sourceId;
            this.targetId = targetId;
        }

        public ProcessLinkSchema(final String sourceId, final String targetId) {
            this.sourceId = sourceId;
            this.targetId = targetId;
        }

        public ProcessLinkSchema(final String id, final String name, final String sourceId, final String targetId, final String conditionExpression) {
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

        public void setSourceId(final String sourceId) {
            this.sourceId = sourceId;
        }

        public String getTargetId() {
            return targetId;
        }

        public void setTargetId(final String targetId) {
            this.targetId = targetId;
        }

        public String getConditionExpression() {
            return conditionExpression;
        }

        public void setConditionExpression(final String conditionExpression) {
            this.conditionExpression = conditionExpression;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            if (!super.equals(o)) {
                return false;
            }
            ProcessLinkSchema that = (ProcessLinkSchema) o;
            return sourceId.equals(that.sourceId) && targetId.equals(that.targetId) && Objects.equals(conditionExpression, that.conditionExpression);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), sourceId, targetId, conditionExpression);
        }
    }

    /**
     * 结束节点bean.
     */
    public static class ProcessEndNodeSchema extends BaseXmlSchema {
        public ProcessEndNodeSchema(final String id, final String name) {
            super(id, name);
        }

        public ProcessEndNodeSchema() {
        }
    }

}
