package vn.elca.training;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import vn.elca.training.service.ProjectService;
import vn.elca.training.util.GlobalExceptionHandler;
import vn.elca.training.web.ApplicationController;

/**
 * @author gtn
 *
 */
@SpringBootApplication(scanBasePackages = "vn.elca.training", exclude = {ErrorMvcAutoConfiguration.class})
@ComponentScan(basePackageClasses = { ApplicationController.class, ProjectService.class, GlobalExceptionHandler.class})
@PropertySource({"classpath:/application.properties", "classpath:/messages.properties", "classpath:/messages_fr.properties"})
public class ApplicationWebConfig extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ApplicationWebConfig.class);
    }

}
