package com.bluslee.mundo.springboot.starter;

import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.Repository;
import com.bluslee.mundo.process.Bootstrap;
import com.bluslee.mundo.process.constants.Constants;
import com.bluslee.mundo.process.utils.ReflectionTools;
import com.bluslee.mundo.xml.base.XmlConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Optional;

/**
 * mundo springboot Configuration.
 * @author carl.che
 */
@EnableConfigurationProperties(MundoProperties.class)
@ConditionalOnProperty(prefix = "mundo", name = "enabled", havingValue = "true")
@Configuration
public class MundoAutoConfiguration {

    @Bean
    public Repository<? extends BaseProcessNode> createRepository(final MundoProperties mundoProperties) {
        MundoProperties.Xml mundoPropertiesXml = mundoProperties.getXml();
        com.bluslee.mundo.core.configuration.Configuration mundoConfig = null;
        if (mundoPropertiesXml != null) {
            mundoConfig = ReflectionTools.instance().firstNoParamConstructorSubInstance(mundoPropertiesXml.getXmlPackageName(),
                    com.bluslee.mundo.core.configuration.Configuration.class);
            Optional.ofNullable(mundoConfig).orElseThrow(() -> new MundoException("not found mundo config instance"));
            mundoConfig.setProperty(XmlConstants.ConfigKey.XML_PATH_CONFIG_NAME, mundoPropertiesXml.getXmlPath());
            mundoConfig.setProperty(Constants.ConfigKey.MODE_PACKAGE_NAME, mundoPropertiesXml.getXmlPackageName());
        } else {
            throw new MundoException("not found mundo xml config");
        }
        return Bootstrap.getInstance().build(mundoConfig);
    }

}
