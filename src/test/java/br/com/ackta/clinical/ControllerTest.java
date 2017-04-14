/*
 * ControllerTest.java		14/12/2015
 * 
 * Copyright (C) 2015 FAPESP. All Rights Reserved.
 */
package br.com.ackta.clinical;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

/**
 * 
 * 
 * @author	RMendonca
 * @version @version@
 * @since	@since@
 */
public class ControllerTest {

	private HttpMessageConverter<Object> mappingJackson2HttpMessageConverter;
	protected MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	

	protected static String createString(int length) {
		final StringBuffer sb = new StringBuffer(length);
		for (int i = 0; i < length; i++) {
			sb.append("c");
		}
		return sb.toString();
	}

	@Autowired
	void setConverters(HttpMessageConverter<Object>[] converters) {
	
		this.mappingJackson2HttpMessageConverter = Arrays
				.asList(converters)
				.stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
				.findAny().get();
	
		Assert.assertNotNull("the JSON message converter must not be null",
				this.mappingJackson2HttpMessageConverter);
	}

	/**
	 * 
	 */
	public ControllerTest() {
		super();
	}

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	protected String json(Object o) throws IOException {
		final MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o,
				MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

}