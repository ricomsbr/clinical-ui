package br.com.ackta.clinical;

import org.junit.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import br.com.ackta.clinical.data.converter.PersistableEnumConverter;
import br.com.ackta.clinical.data.entity.IPersistable;

@Configuration
@EntityScan(basePackageClasses = {Jsr310JpaConverters.class, IPersistable.class, PersistableEnumConverter.class})
@ComponentScan(
		excludeFilters={@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, 
			value={ClinicalApplication.class})})
@EnableAutoConfiguration
@ScanOnlyForTest
public class ClinicalApplicationTests {

	@Test
	public void contextLoads() {
	}

}
