package com.bluslee.mundo.core.process.base;

/**
 * @author carl.che
 * @date 2021/11/1
 * @description BaseProcessActivity 流程图中基本节点类型 - 活动
 */
public abstract class BaseActivity extends BaseProcessNode {

    public BaseActivity(String id, String name) {
        super(id, name);
    }

    public BaseActivity() {
    }

}
