package com.bluslee.mundo.core.process.base;

import java.util.Set;

/**
 * @author carl.che
 * @date 2021/11/6
 * @description Repository is a collection of BaseDefaultProcessEngine
 */
public interface BaseRepository<N extends BaseProcessNode> {

    /**
     * 获取所有的BaseProcessEngine
     * @return 获取当前Repository管理的所有的BaseProcessEngine
     */
    Set<BaseProcessEngine<N>> processes();

    /**
     * 根据processId查询当前Repository匹配的BaseProcessEngine
     * @param processId 引擎唯一id
     * @return 匹配的 BaseProcessEngine
     */
    BaseProcessEngine<N> getProcess(String processId);

    /**
     * 根据processId和版本号查询当前Repository匹配的BaseProcessEngine
     * @param processId 引擎唯一id
     * @param version 版本号
     * @return 匹配的 BaseProcessEngine
     */
    BaseProcessEngine<N> getProcess(String processId, int version);

}
