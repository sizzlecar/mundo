package com.bluslee.mundo.core.process.base;

/**
 * 流程图中基本节点类型:活动，类似于BPMN规范中的activity.
 *
 * @author carl.che
 */
public abstract class BaseActivity extends BaseProcessNode {

    public BaseActivity(final String id, final String name) {
        super(id, name);
    }

    public BaseActivity() {
    }

}
