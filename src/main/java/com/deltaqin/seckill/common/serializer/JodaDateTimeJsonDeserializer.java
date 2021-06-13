package com.deltaqin.seckill.common.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 * @author deltaqin
 * @date 2021/6/13 上午9:05
 */
public class JodaDateTimeJsonDeserializer extends JsonDeserializer<DateTime> {
    // 从Redis里面取出来的日期要格式化
    @Override
    public DateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        String date = jsonParser.readValueAs(String.class);
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        return DateTime.parse(date, dateTimeFormatter);
    }
}
