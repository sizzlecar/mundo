package com.bluslee.mundo.core.process.base;

import com.google.common.base.Objects;

/**
 * 流程中元素基类.
 *
 * @author carl.che
 */
public abstract class BaseElement {

    /**
     * 元素id,一个流程图中唯一.
     */
    private String id;

    /**
     * 元素名称.
     */
    private String name;

    public BaseElement(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    public BaseElement() {
    }

    /**
     * 获取元素id.
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置元素id.
     *
     * @param id id
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * 获取元素的名称.
     *
     * @return 元素名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置元素名称.
     *
     * @param name 设置的名称
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * equals方法，使用id作为判断.
     *
     * @param o 比较的对象
     * @return true 相等，false 不相等
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseElement)) {
            return false;
        }
        BaseElement that = (BaseElement) o;
        return Objects.equal(id, that.id);
    }

    /**
     * 根据id生成hashcode.
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
