package com.bluslee.mundo.xml.base;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import java.util.Objects;

/**
 * BaseXmlSchema 对应mundo配置文件.
 *
 * @author carl.che
 */
public abstract class BaseXmlSchema {

    /**
     * XML元素唯一标识.
     */
    @XStreamAsAttribute
    @NotBlank(message = "id不能为空")
    @Length(max = 100, message = "id长度不能超过{max}")
    private String id;

    /**
     * XML元素用户自定义名称.
     */
    @XStreamAsAttribute
    @Length(max = 200, message = "name长度不能超过{max}")
    private String name;

    public BaseXmlSchema(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    public BaseXmlSchema() {
    }

    /**
     * getI.
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * setId.
     *
     * @param id id
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * getName.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setName .
     *
     * @param name name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * 根据id，name 判断是否相等.
     *
     * @param o 待比较的对象
     * @return true 相等 false 不相等
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseXmlSchema that = (BaseXmlSchema) o;
        return id.equals(that.id) && Objects.equals(name, that.name);
    }

    /**
     * 根据id,name 计算hashcode.
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
