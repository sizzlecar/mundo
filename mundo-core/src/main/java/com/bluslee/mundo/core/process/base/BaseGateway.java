package com.bluslee.mundo.core.process.base;

/**
 * 流程图中基本节点类型 - 网关，表示后续根据不同的条件可能会出现一个或者多个的情况.
 *
 * @author carl.che
 * @see BaseExclusiveGateway
 * @see BaseParallelGateway
 */
public abstract class BaseGateway extends BaseProcessNode {

    public BaseGateway(final String id, final String name) {
        super(id, name);
    }

    public BaseGateway() {
    }
}
