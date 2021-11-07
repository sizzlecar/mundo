package com.bluslee.mundo.core.process.base;

import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.Set;

/**
 * @author carl.che
 * @date 2021/11/6
 * @description BaseDefaultRepository
 */
public abstract class BaseDefaultRepository<N extends BaseProcessNode> implements BaseRepository<N> {

    private final Set<BaseProcessEngine<N>> processes = new HashSet<>();

    @Override
    public Set<BaseProcessEngine<N>> processes() {
        return ImmutableSet.copyOf(processes);
    }

    @Override
    public BaseProcessEngine<N> getProcess(String processId) {
        return processes.stream()
                .filter(process -> process.getId().equals(processId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public BaseProcessEngine<N> getProcess(String processId, int version) {
        return processes.stream()
                .filter(process -> process.getId().equals(processId) && process.getVersion() == version)
                .findFirst()
                .orElse(null);
    }
}
