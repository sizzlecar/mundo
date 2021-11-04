package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.core.expression.Execute;

import java.util.*;

/**
 * @author carl.che
 * @date 2021/11/2
 * @description BaseDefaultProcessEngine 流程引擎基类，本质是一个提供了BaseProcessEngine服务的有向图
 */
public abstract class BaseDefaultProcessEngine<N extends BaseProcessNode, V> extends BaseDirectedValueGraph<N, V> implements BaseProcessEngine<N> {

    private final Execute execute;

    public BaseDefaultProcessEngine(Execute execute) {
        this.execute = execute;
    }

    @Override
    public ProcessNodeWrap<N> getProcessNode(String processNodeId) {
        N processCode = nodes().stream().filter(node -> node.getId().equals(processNodeId)).findFirst().orElse(null);
        return processCode == null ? null : ProcessNodeWrap.unParalle(processCode);
    }

    @Override
    public ProcessNodeWrap<N> getNextProcessNode(ProcessNodeWrap<N> currentNodeWrap, Map<String, Object> parameterMap) {
        N currentNode = currentNodeWrap.get();
        boolean contains = nodes().contains(currentNode);
        if (!contains) {
            //currentNode 在当前流程中不存在
            throw new MundoException("currentNode not in process");
        }
        return currentNode.next(this, parameterMap, execute);
    }

    @Override
    public ProcessNodeWrap<N> getNextProcessNode(String currentNodeId, Map<String, Object> parameterMap) {
        return getNextProcessNode(getProcessNode(currentNodeId), parameterMap);
    }

    @Override
    public List<N> forecastProcessNode(ProcessNodeWrap<N> currentNodeWrap, Map<String, Object> parameterMap) {
        N currentNode = currentNodeWrap.get();
        boolean contains = nodes().contains(currentNode);
        if (!contains) {
            //currentNode 在当前流程中不存在
            throw new MundoException("currentNode not in process");
        }
        List<N> forecastProcessNodeList = new ArrayList<>();
        N nextNode = null;
        while (true){
            ProcessNodeWrap<N> next = currentNode.next(this, parameterMap, execute);
            nextNode = next.get();
            forecastProcessNodeList.add(nextNode);
            if(nextNode instanceof EndNode){
                break;
            }
            currentNode = nextNode;
        }
        return forecastProcessNodeList;
    }

    @Override
    public List<N> forecastProcessNode(String currentNodeId, Map<String, Object> parameterMap) {
        return forecastProcessNode(getProcessNode(currentNodeId), parameterMap);
    }
}
