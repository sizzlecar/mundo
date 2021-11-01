package com.bluslee.mundo.core.process;

/**
 * @author carl.che
 * @date 2021/11/1
 * @description BaseProcessNode 流程图中节点基类
 */
public abstract class BaseProcessNode extends BaseElement{

    public BaseProcessNode(String id, String name) {
        super(id, name);
    }

    public BaseProcessNode() {
    }
}
