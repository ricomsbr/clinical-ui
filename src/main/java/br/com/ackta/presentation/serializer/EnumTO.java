package br.com.ackta.presentation.serializer;

import org.springframework.context.i18n.LocaleContextHolder;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import br.com.ackta.clinical.SerializableResourceBundleMessageSource;
import br.com.ackta.util.EnumUtil;

public class EnumTO<E extends Enum<E>> {
	@JsonIgnore
	private E enumVar;
    private String key;
    private String keyPrefix;
    private String keySufix;
    private String message;

    public EnumTO(
    		final E enumVar,
    		SerializableResourceBundleMessageSource messageSource) {
    	this.enumVar = enumVar;
    	this.key = EnumUtil.getKey(enumVar);
        this.keyPrefix = EnumUtil.getKeyPrefix(enumVar);
        this.keySufix = EnumUtil.getKeySufix(enumVar);
        if (messageSource != null) {
        	this.message = messageSource.getMessage(key, null, key, LocaleContextHolder.getLocale());
        } else {
        	this.message = key;
        }
    }

    @JsonIgnore
    public E getEnum() {
    	return enumVar;
    }

    @JsonGetter
    public String getKey() {
		return key;
	}

    @JsonGetter
	public String getKeyPrefix() {
		return keyPrefix;
	}

	@JsonGetter
	public String getKeySufix() {
		return keySufix;
	}

	@JsonGetter
	public String getMessage() {
		return message;
	}

	@JsonGetter("enum")
	public String getName() {
		return enumVar.name();
	}

    @JsonSetter("enum")
	public void setEnum(E enumVar) {
		this.enumVar = enumVar;
	}

    @JsonSetter
	public void setKey(String key) {
		this.key = key;
	}

	@JsonSetter
	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	@JsonSetter
	public void setKeySufix(String keySufix) {
		this.keySufix = keySufix;
	}

	@JsonSetter
	public void setMessage(String message) {
		this.message = message;
	}

}
