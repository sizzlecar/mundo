package com.bluslee.mundo.xml;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

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
}
