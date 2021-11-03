package com.bluslee.mundo.core.process;

/**
 * @author carl.che
 * @date 2021/11/4
 * @description ProcessNodeWrap
 */
public abstract class ProcessNodeWrap {

    /**
     * 当前流程节点是否属于并行节点
     */
    public abstract boolean currentNodeParallel();

    /**
     * 当前流程节点的下一个节点是否属于并行节点
     */
    public abstract boolean nextNodeParallel();


}
