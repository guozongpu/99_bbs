package com.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.interceptor.Authority;
/** 
* @ClassName: AppConfig 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author (黄志强)  
* @date 2016年9月7日 下午2:54:46 
* @version V1.0 
*/
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.*")

public class AppConfig extends WebMvcConfigurerAdapter{

	//@Autowired
	//RoleToUserProfileConverter roleToUserProfileConverter;

	/**
     * ������ͼ ViewResolver
     */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		registry.viewResolver(viewResolver);
	}
	
	/**
     * ���þ�̬��Դ
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }
    
    /**
     * ����converter
     */
   /* @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(roleToUserProfileConverter);
    }*/
	

    /**
     * ����message������֤�?
     */
    @Bean
	public MessageSource messageSource() {
	    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
	    messageSource.setBasename("messages");
	    return messageSource;
	}
    

    @Override
    public void configurePathMatch(PathMatchConfigurer matcher) {
        matcher.setUseRegisteredSuffixPatternMatch(true);
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        Authority authority=new Authority();
        registry.addInterceptor(authority).addPathPatterns("/**");
        
    }
}

