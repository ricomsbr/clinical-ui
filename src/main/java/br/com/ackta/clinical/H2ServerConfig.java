package br.com.ackta.clinical;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="ackta.h2-config")
public class H2ServerConfig {
	
	List<String> params;
	
	/**
	 * @return the params
	 */
	public List<String> getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(List<String> params) {
		this.params = params;
	}

}
