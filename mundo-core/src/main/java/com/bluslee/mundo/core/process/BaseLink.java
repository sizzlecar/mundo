package com.bluslee.mundo.core.process;

/**
 * @author carl.che
 * @date 2021/11/1
 * @description BaseLink
 */
public abstract class BaseLink extends BaseElement{

    /**
     * source node
     */
    private BaseProcessNode source;

    /**
     * target node
     */
    private BaseProcessNode target;

    /**
     * 条件表达式
     */
    private String conditionExpression;

    public BaseLink(String id, String name, BaseProcessNode source, BaseProcessNode target, String conditionExpression) {
        super(id, name);
        this.source = source;
        this.target = target;
        this.conditionExpression = conditionExpression;
    }

    public BaseLink(BaseProcessNode source, BaseProcessNode target, String conditionExpression) {
        this.source = source;
        this.target = target;
        this.conditionExpression = conditionExpression;
    }

    public BaseLink() {
    }

    public BaseProcessNode getSource() {
        return source;
    }

    public void setSource(BaseProcessNode source) {
        this.source = source;
    }

    public BaseProcessNode getTarget() {
        return target;
    }

    public void setTarget(BaseProcessNode target) {
        this.target = target;
    }

    public String getConditionExpression() {
        return conditionExpression;
    }

    public void setConditionExpression(String conditionExpression) {
        this.conditionExpression = conditionExpression;
    }
}
