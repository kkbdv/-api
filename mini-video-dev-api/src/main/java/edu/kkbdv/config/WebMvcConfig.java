package edu.kkbdv.config;

import edu.kkbdv.interceptor.MiniInterceptor;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")//防止swagger的资源被拦截
                .addResourceLocations("file:G:/kkbdv_dev/");
    }

    /**
     * 注册拦截器到spring 容器
     * @return
     */
    @Bean
    public MiniInterceptor miniInterceptor(){
        return new MiniInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(miniInterceptor()).addPathPatterns("/user/**")
                .excludePathPatterns("/user/login","/user/register","/user/queryPublisher","/user/logout")
        .addPathPatterns("/video/upload");
        super.addInterceptors(registry);
    }
}
