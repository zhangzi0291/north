package com.north.base.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {

    /**
     * 可格式化 的日期 字串
     */
    private static final List<String> formarts = new ArrayList();

    static{
        formarts.add("yyyy-MM");
        formarts.add("yyyy-MM-dd");
        formarts.add("yyyy-MM-dd HH:mm");
        formarts.add("yyyy-MM-dd HH:mm:ss");
        formarts.add("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
    }

    @Override
    public LocalDateTime convert(String source) {
        if(StringUtils.isEmpty(source)){
            return null;
        }

        if(source.matches("^\\d{4}-\\d{1,2}$")){
            return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(formarts.get(0)));
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")){
            return LocalDateTime.parse(source+" 00:00:00", DateTimeFormatter.ofPattern(formarts.get(3)));
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")){
            return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(formarts.get(2)));
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")){
            return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(formarts.get(3)));
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}T{1}\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{3}([Z]|[+].{4,5})$")){
            LocalDateTime time =  ZonedDateTime.parse(source).withZoneSameInstant(ZoneId.of("+08:00")).toLocalDateTime();
            return time;
        }else if(source.matches("^[-\\+]?[\\d]*$"))
            return new Date(Long.valueOf(source)).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        else {
            throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
        }
    }


}
