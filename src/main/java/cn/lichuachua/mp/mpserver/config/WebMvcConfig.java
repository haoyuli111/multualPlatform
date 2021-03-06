package cn.lichuachua.mp.mpserver.config;

import cn.lichuachua.mp.mpserver.web.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author  李歘歘
 */
//@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;
    /**
     * 配置静态页面请求处理
     */

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/css/**",
                        "/*",
                        "/lib/**",
                        "/plugins/**",
                        "/examples/**",
                        "/articleImages/**",
                        "/accessory/**",
                        "/avatar/**",
                        "/js/**",
                        "/image/**",
                        "/fonts/**",
                        "/resource/**",
                        "/error",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/v2/**",
                        "/swagger-ui.html",
                        "/user/register",
                        "/user/login",
                        "/user/forget",
                        "/user/sendCode",
                        "/user/search/{mobile}",
                        "/article/queryList",
                        "/article/queryListByPage/{pageable}",
                        "/article/queryUserArticleList/{userId}",
                        "/article/queryArticleListByArticleId/{articleId}",
                        "/article/query/{articleId}",
                        "/article/comment/query/{articleId}",
                        "/article/likes/likesList/{articleId}",
                        "/article/type/queryArticleTypeList",
                        "/team/queryList",
                        "/team/queryPublic/{teamId}",
                        "/team/queryListByType/{typeId}",
                        "/team/queryAllList",
                        "/announcement/queryList"
                );
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }
}
