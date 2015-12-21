package com.hsae.ims.config;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
public class JacksonObjectMapper  extends ObjectMapper {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JacksonObjectMapper() {
        super();
        this.getSerializerProvider().setNullValueSerializer(new NullValueSerializer());
    }
	
    public  class NullValueSerializer extends JsonSerializer<Object> {
		@Override
		public void serialize(Object value,
				com.fasterxml.jackson.core.JsonGenerator gen,
				com.fasterxml.jackson.databind.SerializerProvider serializers)
				throws IOException,
				com.fasterxml.jackson.core.JsonProcessingException {
				gen.writeString("");
			
		}
        
    }
}
