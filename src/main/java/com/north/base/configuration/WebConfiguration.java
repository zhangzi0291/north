package com.north.base.configuration;

import com.north.base.converter.StringToDateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.feed.AtomFeedHttpMessageConverter;
import org.springframework.http.converter.feed.RssChannelHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.List;

@Configuration
public class WebConfiguration extends WebMvcConfigurationSupport implements ApplicationContextAware {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static boolean romePresent = ClassUtils.isPresent("com.rometools.rome.feed.WireFeed", WebMvcConfigurationSupport.class.getClassLoader());
    private static final boolean jaxb2Present = ClassUtils.isPresent("javax.xml.bind.Binder", WebMvcConfigurationSupport.class.getClassLoader());
    private static final boolean jackson2Present = ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", WebMvcConfigurationSupport.class.getClassLoader()) && ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator", WebMvcConfigurationSupport.class.getClassLoader());
    private static final boolean jackson2XmlPresent = ClassUtils.isPresent("com.fasterxml.jackson.dataformat.xml.XmlMapper", WebMvcConfigurationSupport.class.getClassLoader());
    private static final boolean gsonPresent = ClassUtils.isPresent("com.google.gson.Gson", WebMvcConfigurationSupport.class.getClassLoader());
    private ApplicationContext applicationContext;

    @Resource
    private RequestMappingHandlerAdapter handlerAdapter;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }


    /**
     * 解决直接return String 中文乱码
     * @return
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new ResourceHttpMessageConverter());
        converters.add(new SourceHttpMessageConverter());
        converters.add(new AllEncompassingFormHttpMessageConverter());
        if (romePresent) {
            converters.add(new AtomFeedHttpMessageConverter());
            converters.add(new RssChannelHttpMessageConverter());
        }

        if (jackson2XmlPresent) {
            converters.add(new MappingJackson2XmlHttpMessageConverter(Jackson2ObjectMapperBuilder.xml().applicationContext(this.applicationContext).build()));
        } else if (jaxb2Present) {
            converters.add(new Jaxb2RootElementHttpMessageConverter());
        }

        if (jackson2Present) {
            converters.add(new MappingJackson2HttpMessageConverter(Jackson2ObjectMapperBuilder.json().applicationContext(this.applicationContext).build()));
        } else if (gsonPresent) {
            converters.add(new GsonHttpMessageConverter());
        }
    }

    /**
     * 添加转换器
     * @return
     */
    @Bean
    public String initEditableValidation() {
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter
                .getWebBindingInitializer();
        if (initializer.getConversionService() != null) {
            GenericConversionService genericConversionService = (GenericConversionService) initializer
                    .getConversionService();
            genericConversionService.addConverter(new StringToDateConverter());
        }
        return null;
    }

}
