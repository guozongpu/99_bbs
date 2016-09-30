package com.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

public class JacksonCustomObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = -1613945048107136266L;

	public JacksonCustomObjectMapper() {
        this.setOptions();
    }

    private void setOptions() {
        this.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        this.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JacksonAnnotationIntrospector introspector = new JacksonAnnotationIntrospector();
        this.setAnnotationIntrospector(introspector);
        this.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
        this.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        this.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
    }
}
