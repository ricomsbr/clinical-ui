package br.com.ackta.clinical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import br.com.ackta.clinical.data.entity.IPersistable;

@EntityScan(basePackageClasses = { Jsr310JpaConverters.class, IPersistable.class })
@ComponentScan(basePackages = { "br.com.ackta.clinical" }, excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = { ScanOnlyForTest.class }) })
// @EnableJpaRepositories("br.com.ackta.clinical")
@SpringBootApplication
public class ClinicalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicalApplication.class, args);
	}
}
