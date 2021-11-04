package com.bluslee.mundo.core.process;

import com.google.common.base.Objects;

/**
 * @author carl.che
 * @date 2021/11/2
 * @description BaseElement process中基础元素
 */
abstract class BaseElement {

    /**
     * 元素id,一个流程图中唯一
     */
    protected String id;

    /**
     * 元素名称
     */
    protected String name;

    public BaseElement(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public BaseElement() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseElement)) return false;
        BaseElement that = (BaseElement) o;
        return Objects.equal(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
