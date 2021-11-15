package com.bluslee.mundo.core.process.base;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;

/**
 * {@link Repository} 抽象实现类，提供流程的管理，查找功能.
 *
 * @author carl.che
 * @see Repository
 */
public abstract class BaseRepository<N extends BaseProcessNode> implements Repository<N> {

    private final Set<ProcessEngine<N>> processes;

    public BaseRepository(final Set<ProcessEngine<N>> processes) {
        this.processes = ImmutableSet.copyOf(processes);
    }

    /**
     * 获取当前Repository管理的所有的流程.
     *
     * @return 所有的流程
     */
    @Override
    public Set<ProcessEngine<N>> processes() {
        return processes;
    }

    /**
     * 根据processId查询对应的流程，如果同一个id有多个流程，默认返回版本号最大的流程.
     *
     * @param processId 引擎唯一id
     * @return 找到的流程
     */
    @Override
    public ProcessEngine<N> getProcess(final String processId) {
        return processes.stream()
                .filter(process -> process.getId().equals(processId))
                .max(Comparator.comparing(ProcessEngine::getVersion))
                .orElse(null);
    }

    /**
     * 根据流程id，以及版本找到唯一的流程.
     *
     * @param processId 引擎唯一id
     * @param version   版本号
     * @return 对应的流程
     */
    @Override
    public ProcessEngine<N> getProcess(final String processId, final Integer version) {
        return processes.stream()
                .filter(process -> process.getId().equals(processId) && Objects.equals(process.getVersion(), version))
                .findFirst()
                .orElse(null);
    }
}
