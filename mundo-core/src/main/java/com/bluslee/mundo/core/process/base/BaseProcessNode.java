package com.bluslee.mundo.core.process.base;

import com.bluslee.mundo.core.expression.Execute;
import com.bluslee.mundo.core.process.graph.MutableValueGraph;
import java.util.Map;

/**
 * 流程图中节点基类,区分{@link BaseLink} 节点.
 *
 * @author carl.che
 * @see BaseLink
 */
public abstract class BaseProcessNode extends BaseElement {

    public BaseProcessNode(final String id, final String name) {
        super(id, name);
    }

    public BaseProcessNode() {
    }

    /**
     * 根据有向图，业务参数，表达式执行器，判断当前节点的下一个节点.
     *
     * @param processGraph 有向图
     * @param parameterMap 参数map
     * @param execute      执行器
     * @param <N>          BaseProcessNode类型
     * @param <V>          边的值的类型
     * @return 下一个节点包装器
     */
    public abstract <N extends BaseProcessNode, V> ProcessNodeWrap<N> next(MutableValueGraph<N, V> processGraph, Map<String, Object> parameterMap, Execute execute);

    /**
     * toString.
     *
     * @return toString
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseProcessNode{");
        sb.append("id='").append(getId()).append('\'');
        sb.append(", name='").append(getName()).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
