package br.com.training.pet.configuration;

import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.AbstractPathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	class BasePathProvider extends AbstractPathProvider {
		public static final String ROOT_API_BASE_PATH = "/swagger/";

		public BasePathProvider() {
			// TODO Auto-generated constructor stub
		}

		@Override
		protected String applicationPath() {
			return ROOT_API_BASE_PATH;
		}

		@Override
		protected String getDocumentationPath() {
			return ROOT_API_BASE_PATH;
		}

	}
	
    @Autowired
    private Environment env;

    @Bean
    public Docket api() {
        Predicate<RequestHandler> endpointSelector = RequestHandlerSelectors.any();

        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(this.getGlobalParameters())
                .select()
                .apis(endpointSelector)
                .paths(PathSelectors.any())
                .build()
                .pathProvider(new BasePathProvider());
    }

    private List<Parameter> getGlobalParameters() {
        List<Parameter> list = new ArrayList<Parameter>();
        // If security is enabled, adds the Authorization Header.
        /*
        if(Boolean.valueOf(env.getProperty(SECURITY_ENABLED_PROPERTIES))) {
            ParameterBuilder param = new ParameterBuilder().name("Authorization")
                    .description("To obtain the Authorization Token see (auth-ms -> token-rest -> generateToken)")
                    .modelRef(new ModelRef("string")).parameterType("header").required(true);
            list.add(param.build());
        }*/
        return list;
    }
}
