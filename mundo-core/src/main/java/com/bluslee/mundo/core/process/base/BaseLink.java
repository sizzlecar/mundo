package com.bluslee.mundo.core.process.base;

/**
 * 流程中基础元素-连线，代表一个节点在一定条件下可以到达的下一个节点.
 *
 * @author carl.che
 * @see com.bluslee.mundo.core.process.Link
 */
public abstract class BaseLink extends BaseElement {

    /**
     * 出发节点.
     */
    private BaseProcessNode source;

    /**
     * 目标节点.
     */
    private BaseProcessNode target;

    /**
     * 出发节点到目标节点的条件表达式.
     */
    private String conditionExpression;

    public BaseLink(final String id, final String name,
                    final BaseProcessNode source, final BaseProcessNode target,
                    final String conditionExpression) {
        super(id, name);
        this.source = source;
        this.target = target;
        this.conditionExpression = conditionExpression;
    }

    public BaseLink(final BaseProcessNode source, final BaseProcessNode target, final String conditionExpression) {
        this.source = source;
        this.target = target;
        this.conditionExpression = conditionExpression;
    }

    public BaseLink() {
    }

    /**
     * 获取出发节点.
     *
     * @return 出发节点
     */
    public BaseProcessNode getSource() {
        return source;
    }

    /**
     * 设置目标节点.
     *
     * @param source 设置的目标节点
     */
    public void setSource(final BaseProcessNode source) {
        this.source = source;
    }

    /**
     * 获取目标节点.
     *
     * @return 目标节点
     */
    public BaseProcessNode getTarget() {
        return target;
    }

    /**
     * 设置目标节点.
     *
     * @param target 设置的目标节点
     */
    public void setTarget(final BaseProcessNode target) {
        this.target = target;
    }

    /**
     * 获取调价表达式.
     *
     * @return link的条件表达式
     */
    public String getConditionExpression() {
        return conditionExpression;
    }

    /**
     * 设置link的条件表达式.
     *
     * @param conditionExpression 设置的条件表达式
     */
    public void setConditionExpression(final String conditionExpression) {
        this.conditionExpression = conditionExpression;
    }
}
