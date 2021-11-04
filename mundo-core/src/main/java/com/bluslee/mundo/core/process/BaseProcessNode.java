package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.expression.Execute;

import java.util.Map;

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

    /**
     * 获取下一个节点
     * @param processGraph 流程图
     * @param parameterMap 参数map
     * @param execute 执行器
     * @return 下一个节点包装器
     */
    public abstract <N extends BaseProcessNode, V> ProcessNodeWrap<N> next(MutableValueGraph<N, V> processGraph, Map<String, Object> parameterMap, Execute execute);
}
