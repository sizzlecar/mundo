package com.bluslee.mundo.xml.base;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * @author carl.che
 * @date 2021/10/30
 * @description BaseXmlSchema
 */
public abstract class BaseXmlSchema {

    /**
     * XML元素唯一标识
     */
    @XStreamAsAttribute
    @NotBlank(message = "id不能为空")
    @Length(max = 100, message = "id长度不能超过{max}")
    protected String id;

    /**
     * XML元素用户自定义名称
     */
    @XStreamAsAttribute
    @Length(max = 200, message = "name长度不能超过{max}")
    protected String name;

    public BaseXmlSchema(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public BaseXmlSchema() {
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
        if (o == null || getClass() != o.getClass()) return false;
        BaseXmlSchema that = (BaseXmlSchema) o;
        return id.equals(that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
