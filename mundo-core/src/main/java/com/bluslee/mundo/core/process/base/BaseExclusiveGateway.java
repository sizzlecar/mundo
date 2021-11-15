package com.bluslee.mundo.core.process.base;

/**
 * 流程图中基本节点类型 - 排他网关，代表只会选择后续的某一条 {@link com.bluslee.mundo.core.process.Link}.
 *
 * @author carl.che
 * @see com.bluslee.mundo.core.process.Link
 */
public abstract class BaseExclusiveGateway extends BaseGateway {

    public BaseExclusiveGateway(final String id, final String name) {
        super(id, name);
    }

    public BaseExclusiveGateway() {
    }
}
