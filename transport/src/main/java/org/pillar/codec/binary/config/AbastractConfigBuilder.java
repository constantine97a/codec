package org.pillar.codec.binary.config;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by pillar on 2015/8/10.
 */
public abstract class AbastractConfigBuilder {
    private final Logger logger = LoggerFactory.getLogger(AbastractConfigBuilder.class);

    private final String defaultConfigClasspath = "/transport-default.properties";

    private final String configClasspath = "/transport.properties";

    private PropertiesFactoryBean propertiesFactoryBean;

    /**
     * read-only
     */
    private Properties properties;

    public AbastractConfigBuilder() {
        try {
            properties = readProperties();
        } catch (IOException e) {
            logger.error("defaultConfigClasspath:{/transport-default.properties},configClasspath:{/transport.properties} " +
                    "throw IO Exception");
        }
    }

    private Properties readProperties() throws IOException {
        ClassPathResource defaultResource = new ClassPathResource(defaultConfigClasspath);
        ClassPathResource overriderResource = new ClassPathResource(configClasspath);
        propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocations(new Resource[]{defaultResource, overriderResource});
        propertiesFactoryBean.setIgnoreResourceNotFound(true);
        try {
            propertiesFactoryBean.afterPropertiesSet();
            return propertiesFactoryBean.getObject();
        } catch (IOException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            throw e;
        }
    }

    public ExtendedProperties getProperties() {
        return ExtendedProperties.convertProperties(properties);
    }


    public abstract Object build();

}
