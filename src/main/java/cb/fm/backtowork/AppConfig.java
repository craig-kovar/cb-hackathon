package cb.fm.backtowork;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class AppConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cb.fm.backtowork.web"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaInfo())
                .useDefaultResponseMessages(false);
    }

    private ApiInfo metaInfo() {
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        return apiInfoBuilder.title("Back To Work")
                .contact(new Contact("Fighting Mongoose LLC",null,null))
                .description("Empowering your employee's to safely return to the office")
                .version("1.1")
                .build();

    }

}