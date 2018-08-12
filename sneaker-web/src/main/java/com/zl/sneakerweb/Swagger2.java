package com.zl.sneakerweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Auther: le
 * @Date: 2018/7/31 10:15
 * @Description:
 */


//swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
@Configuration
public class Swagger2 {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zl.sneakerweb.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("sneaker接口说明")
                .version("1.0")
                .termsOfServiceUrl("http://localhost:8888")
                .description("API描述")
                .build();
    }
}
