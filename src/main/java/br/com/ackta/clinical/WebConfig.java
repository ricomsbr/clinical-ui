package br.com.ackta.clinical;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	private static final Locale DEFAULT_LOCALE = new Locale("pt", "BR");
		
	@Bean 
	public LocaleResolver localeResolver() {
		final SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(DEFAULT_LOCALE);
		return resolver;
	}
}
