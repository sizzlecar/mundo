package com.bluslee.mundo.core.process.base;

/**
 * @author carl.che
 * @date 2021/10/30
 * @description BaseGateway 流程图中基本节点类型 - 网关
 */
public abstract class BaseGateway extends BaseProcessNode {

    public BaseGateway(String id, String name) {
        super(id, name);
    }

    public BaseGateway() {
    }
}
