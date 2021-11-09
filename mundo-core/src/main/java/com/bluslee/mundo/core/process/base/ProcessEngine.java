package com.bluslee.mundo.core.process.base;

import java.util.Map;
import java.util.Set;

/**
 * @author carl.che
 * @date 2021/11/1
 * @description BaseProcess 基础流程接口
 */
public interface ProcessEngine<N extends BaseProcessNode> {

    /**
     * 获取当前流程id
     *
     * @return 当前流程id
     */
    String getId();

    /**
     * 获取当前引擎版本号
     *
     * @return 版本号
     */
    int getVersion();

    /**
     * 根据id在当前流程中寻找对应的node
     *
     * @param processNodeId id
     * @return 查找的结果
     */
    N getProcessNode(String processNodeId);

    /**
     * 根据当前节点，以及参数找出下一个节点
     *
     * @param currentNode  当前节点
     * @param parameterMap 参数map
     * @return 下一个节点
     */
    ProcessNodeWrap<N> getNextProcessNode(N currentNode, Map<String, Object> parameterMap);

    /**
     * 根据当前节点，以及参数找出下一个节点
     *
     * @param currentNodeId 当前节点id
     * @param parameterMap  参数map
     * @return 下一个节点
     */
    ProcessNodeWrap<N> getNextProcessNode(String currentNodeId, Map<String, Object> parameterMap);

    /**
     * 预测当前节点的后续节点
     *
     * @param currentNode  当前节点
     * @param parameterMap 参数map
     * @return 当前节点根据参数map预测出后续的节点
     */
    Set<N> forecastProcessNode(N currentNode, Map<String, Object> parameterMap);

    /**
     * 预测当前节点的后续节点
     *
     * @param currentNodeId 当前节点id
     * @param parameterMap  参数map
     * @return 当前节点根据参数map预测出后续的节点
     */
    Set<N> forecastProcessNode(String currentNodeId, Map<String, Object> parameterMap);

}
