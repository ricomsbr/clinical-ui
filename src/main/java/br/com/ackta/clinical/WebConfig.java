package br.com.ackta.clinical;

import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import br.com.ackta.clinical.data.entity.Gender;
import br.com.ackta.clinical.presentation.serializer.ChronoUnitSerializer;
import br.com.ackta.clinical.presentation.serializer.GenderDeserializer;
import br.com.ackta.clinical.presentation.serializer.GenderSerializer;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	private static final Locale DEFAULT_LOCALE = new Locale("pt", "BR");

	@Value("${clinical.report.patientReportLayout}")
	private Resource patientReportLayout;

	@Value("${clinical.report.patientReportPath}")
	private String patientReportPath;
	
	@Autowired
	private H2ServerConfig h2ServerConfig;
	
	@Bean(initMethod="start", destroyMethod="stop" )
	@PostConstruct
	public Server h2WebServer() throws SQLException {
		Server result = null;
		List<String> paramsList = h2ServerConfig.getParams();
		if (paramsList != null) {
			String[] params = new String[] {};
			params = paramsList.toArray(params);
			result = Server.createTcpServer(params);
		}
		return result;
	}
	
	/**
	 * @return the patientReportLayout
	 */
	public Resource getPatientReportLayout() {
		return patientReportLayout;
	}

	/**
	 * @return the patientReportPath
	 */
	public String getPatientReportPath() {
		return patientReportPath;
	}

	@Bean
	public LocaleResolver localeResolver() {
		final SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(DEFAULT_LOCALE);
		return resolver;
	}

	@Bean
	public SerializableResourceBundleMessageSource messageSource() {
		final SerializableResourceBundleMessageSource serializableResourceBundleMessageSource = new SerializableResourceBundleMessageSource();
		serializableResourceBundleMessageSource.setBasenames(
				"classpath:/i18n/msgs",
				"classpath:/i18n/enums",
				"classpath:/i18n/ui-msgs",
				"classpath:/i18n/ValidatorMessages");
		return serializableResourceBundleMessageSource;
	}

	@Bean
	public Module module(SerializableResourceBundleMessageSource messageSource) {
		//Esses serializer não estão sendo usado no Thymeleaf, somente quando for usar o Angular
		final SimpleModule module = new SimpleModule();
        module.addSerializer(ChronoUnit.class, new ChronoUnitSerializer(messageSource));
        module.addSerializer(Gender.class, new GenderSerializer(messageSource));
        module.addDeserializer(Gender.class, new GenderDeserializer());
		return module;
	}

	@Bean
	@Autowired
	public RestTemplate restTemplate(ObjectMapper objectMapper) {
		final RestTemplate restTemplate = new RestTemplate();
		final List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
	    final MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
	    jsonMessageConverter.setObjectMapper(objectMapper);
	    messageConverters.add(jsonMessageConverter);
	    restTemplate.setMessageConverters(messageConverters);
		return restTemplate;
	}
}
