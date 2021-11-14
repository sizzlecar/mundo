package com.bluslee.mundo.core.process.base;

import com.google.common.collect.ImmutableSet;

import java.util.Comparator;
import java.util.Set;

/**
 * @author carl.che
 * @date 2021/11/6
 * @description BaseDefaultRepository
 */
public abstract class BaseRepository<N extends BaseProcessNode> implements Repository<N> {

    private final Set<ProcessEngine<N>> processes;

    public BaseRepository(Set<ProcessEngine<N>> processes) {
        this.processes = ImmutableSet.copyOf(processes);
    }

    @Override
    public Set<ProcessEngine<N>> processes() {
        return processes;
    }

    @Override
    public ProcessEngine<N> getProcess(String processId) {
        return processes.stream()
                .filter(process -> process.getId().equals(processId))
                .max(Comparator.comparing(ProcessEngine::getVersion))
                .orElse(null);
    }

    @Override
    public ProcessEngine<N> getProcess(String processId, int version) {
        return processes.stream()
                .filter(process -> process.getId().equals(processId) && process.getVersion() == version)
                .findFirst()
                .orElse(null);
    }
}
