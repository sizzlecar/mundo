package com.bluslee.mundo.core.process.base;

/**
 * 流程图中基本节点类型:活动，类似于BPMN规范中的activity.
 *
 * @author carl.che
 */
public abstract class BaseActivity extends BaseProcessNode {

    /**
     * 有参构造函数.
     * @param id    id
     * @param name  name
     */
    public BaseActivity(final String id, final String name) {
        super(id, name);
    }

    /**
     *
     * 无参构造函数.
     */
    public BaseActivity() {
    }

}
