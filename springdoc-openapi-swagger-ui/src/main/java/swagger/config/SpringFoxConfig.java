package swagger.config;

// @Configuration
// @EnableSwagger2 老版本SpringFox的配置
public class SpringFoxConfig {

    // 配置API文档显示的路径
    // .apis(RequestHandlerSelectors.basePackage("com.example.web.controller"))
    // .paths(PathSelectors.ant("/user/*"))
    //
    // @Bean
    // public Docket api() {
    //     return new Docket(DocumentationType.SWAGGER_2)
    //             .apiInfo(apiInfo())
    //             .select()
    //             .apis(RequestHandlerSelectors.any())
    //             .paths(PathSelectors.any())
    //             .build();
    // }
    //
    // 配置API的相关信息
    // private ApiInfo apiInfo() {
    //     return new ApiInfo(
    //             "My REST API",
    //             "Some custom description of API.",
    //             "API TOS",
    //             "Terms of service",
    //             new Contact("victor", "www.example.com", "myeaddress@company.com"),
    //             "License of API",
    //             "API license URL",
    //             Collections.emptyList());
    // }
}
