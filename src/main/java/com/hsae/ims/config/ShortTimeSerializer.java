package com.hsae.ims.config;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
/***
 * 短格式的时间序列化
 * @author panchaoyang
 *
 */
public class ShortTimeSerializer  extends JsonSerializer<Timestamp>{

	@Override
	public void serialize(Timestamp value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		String formattedDate = formatter.format(value);
		jgen.writeString(formattedDate);
	}

}
