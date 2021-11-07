package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.BaseRepository;
import com.bluslee.mundo.core.process.base.ProcessEngine;

import java.util.Set;

/**
 * @author carl.che
 * @date 2021/11/7
 * @description Repository
 */
public class Repository<N extends BaseProcessNode> extends BaseRepository<N> {

    Repository(Set<ProcessEngine<N>> processes) {
        super(processes);
    }
}
