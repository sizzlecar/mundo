package com.bluslee.mundo.core.process;

/**
 * @author carl.che
 * @date 2021/11/2
 * @description BaseElement process中基础元素
 */
public abstract class BaseElement {

    /**
     * 节点id,一个流程图中唯一
     */
    protected String id;

    /**
     * 节点名称
     */
    protected String name;

    public BaseElement(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public BaseElement() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
