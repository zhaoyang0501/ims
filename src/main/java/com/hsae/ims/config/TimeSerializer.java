package com.hsae.ims.config;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
public class TimeSerializer  extends JsonSerializer<Timestamp>{
	@Override
	public void serialize(Timestamp value, JsonGenerator gen,
			SerializerProvider serializers) throws IOException,
			JsonProcessingException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String formattedDate = formatter.format(value);
		gen.writeString(formattedDate);
		
	}

}
