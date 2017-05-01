package br.com.ackta.clinical;

import java.util.Locale;
import java.util.Properties;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class SerializableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

    public Properties getAllProperties(Locale locale) {
        clearCacheIncludingAncestors();
        final PropertiesHolder propertiesHolder = getMergedProperties(locale);
        final Properties properties = propertiesHolder.getProperties();

        return properties;
    }
}