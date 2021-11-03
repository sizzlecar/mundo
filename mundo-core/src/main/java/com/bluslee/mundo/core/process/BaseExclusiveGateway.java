package com.bluslee.mundo.core.process;

/**
 * @author carl.che
 * @date 2021/10/30
 * @description BaseGateway 流程图中基本节点类型 - 排他网关，只会走某一条link
 */
public class BaseExclusiveGateway extends BaseGateway {

    public BaseExclusiveGateway(String id, String name) {
        super(id, name);
    }

    public BaseExclusiveGateway() {
    }
}
