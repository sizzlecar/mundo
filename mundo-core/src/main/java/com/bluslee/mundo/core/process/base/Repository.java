package com.bluslee.mundo.core.process.base;

import java.util.Set;

/**
 * 流程引擎集合服务.
 *
 * @author carl.che
 */
public interface Repository<N extends BaseProcessNode> {

    /**
     * 获取所有的BaseProcessEngine.
     *
     * @return 获取当前Repository管理的所有的BaseProcessEngine
     */
    Set<ProcessEngine<N>> processes();

    /**
     * 根据processId查询当前Repository匹配的BaseProcessEngine.
     *
     * @param processId 引擎唯一id
     * @return 匹配的 BaseProcessEngine
     */
    ProcessEngine<N> getProcess(String processId);

    /**
     * 根据processId和版本号查询当前Repository匹配的BaseProcessEngine.
     *
     * @param processId 引擎唯一id
     * @param version   版本号
     * @return 匹配的 BaseProcessEngine
     */
    ProcessEngine<N> getProcess(String processId, Integer version);

}
