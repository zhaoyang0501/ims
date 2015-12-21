package com.hsae.ims.config;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
/***
 * 日期JSON反序列化
 * @author panchaoyang
 *
 */
public class DateJsonDeserializer extends JsonDeserializer<Date> {  
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
    @Override  
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {  
        try {  
            return format.parse(jsonParser.getText());  
        } catch (ParseException e) {  
            throw new RuntimeException(e);  
        }  
    }  
}  