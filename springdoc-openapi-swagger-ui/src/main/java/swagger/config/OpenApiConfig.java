package swagger.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${springdoc.version}")
    private String appVersion;

    // 配置不同的API Doc到不同组
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("springshop-public")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        License license = new License()
                .name("Apache 2.0").url("http://springdoc.org");
        Info info = new Info()
                .title("My Open API Doc")
                .version(appVersion)
                .description("This is a sample Open API Doc")
                .termsOfService("http://swagger.io/terms/")
                .license(license);
        return new OpenAPI().info(info);
    }
}
