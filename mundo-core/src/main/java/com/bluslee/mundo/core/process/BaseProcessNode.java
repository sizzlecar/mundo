package com.bluslee.mundo.core.process;

/**
 * @author carl.che
 * @date 2021/11/1
 * @description BaseProcessNode 流程图中节点基类
 */
public abstract class BaseProcessNode {

    /**
     * 节点id,一个流程图中唯一
     */
    private String id;

    /**
     * 节点名称
     */
    private String name;


    public BaseProcessNode(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public BaseProcessNode() {
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
