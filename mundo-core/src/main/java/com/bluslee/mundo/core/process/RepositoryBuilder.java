package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.ProcessEngine;

import java.util.Set;

/**
 * @author carl.che
 * @date 2021/11/7
 * @description RepositoryBuilder
 */
public class RepositoryBuilder {

    public static <N extends BaseProcessNode> Repository<N> build(Set<ProcessEngine<N>> processes) {
        return new Repository<>(processes);
    }
}
