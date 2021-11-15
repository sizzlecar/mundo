package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.BaseRepository;
import com.bluslee.mundo.core.process.base.ProcessEngine;
import java.util.Set;

/**
 * BaseRepository默认实现.
 *
 * @author carl.che
 */
public class Repository<N extends BaseProcessNode> extends BaseRepository<N> {

    Repository(final Set<ProcessEngine<N>> processes) {
        super(processes);
    }
}
