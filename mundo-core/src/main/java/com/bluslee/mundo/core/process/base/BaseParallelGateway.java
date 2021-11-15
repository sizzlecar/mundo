package com.bluslee.mundo.core.process.base;

/**
 * 并行网关基类，代表后续节点可以在满足条件的情况下可以同时到达.
 *
 * @author carl.che
 * @see com.bluslee.mundo.core.process.ParallelGateway
 */
public abstract class BaseParallelGateway extends BaseGateway {

    public BaseParallelGateway(final String id, final String name) {
        super(id, name);
    }

    public BaseParallelGateway() {
    }
}
