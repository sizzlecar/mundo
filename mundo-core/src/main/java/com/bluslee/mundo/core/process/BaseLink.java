package com.bluslee.mundo.core.process;

/**
 * @author carl.che
 * @date 2021/11/1
 * @description BaseLink
 * @copyright COPYRIGHT © 2014 - 2021 VOYAGE ONE GROUP INC. ALL RIGHTS RESERVED.
 */
public class BaseLink extends BaseProcessNode {

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

    public BaseLink(String id, String name, BaseProcessNode source, BaseProcessNode target) {
        super(id, name);
        this.source = source;
        this.target = target;
    }

    public BaseLink(BaseProcessNode source, BaseProcessNode target) {
        this.source = source;
        this.target = target;
    }

    public BaseLink(String id, String name) {
        super(id, name);
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
}
