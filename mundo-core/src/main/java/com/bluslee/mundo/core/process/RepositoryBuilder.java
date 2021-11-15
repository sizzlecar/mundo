package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.ProcessEngine;
import java.util.Set;

/**
 * RepositoryBuilder.
 *
 * @author carl.che
 */
public class RepositoryBuilder {

    public static <N extends BaseProcessNode> Repository<N> build(final Set<ProcessEngine<N>> processes) {
        return new Repository<>(processes);
    }
}
