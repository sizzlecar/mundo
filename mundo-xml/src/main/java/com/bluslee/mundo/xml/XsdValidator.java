package com.bluslee.mundo.xml;

import com.bluslee.mundo.core.configuration.Configuration;
import com.bluslee.mundo.core.configuration.RepositoryFactory;
import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.core.validate.Validator;
import com.bluslee.mundo.xml.base.XmlConstants;
import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * XsdValidator.
 * @author carl.che
 */
public class XsdValidator implements Validator {

    private final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

    private final Schema schema;

    private final RepositoryFactory.LifeCycle matchLifeCycle;

    public XsdValidator() {
        try {
            schema = schemaFactory.newSchema(new StreamSource(getClass()
                    .getResourceAsStream(XmlConstants.ConfigKey.XSD_PATH)));
        } catch (SAXException | NullPointerException e) {
            throw new MundoException("初始化XSD发生错误", e);
        }
        this.matchLifeCycle = RepositoryFactory.LifeCycle.LOAD;
    }

    @Override
    public <T> void validate(final Configuration configuration, final T validateModel) throws MundoException {
        try {
            schema.newValidator().validate(new StreamSource(new ByteArrayInputStream(configuration.getInitData())));
        } catch (SAXException | IOException e) {
            throw new MundoException("校验XML发生错误", e);
        }
    }

    @Override
    public boolean match(final RepositoryFactory.LifeCycle lifeCycle) {
        return Objects.equals(matchLifeCycle, lifeCycle);
    }

    @Override
    public int order() {
        return 0;
    }
}
