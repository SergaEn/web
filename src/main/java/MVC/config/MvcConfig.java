package MVC.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {


    @Controller
    static class Routes {
        @RequestMapping({
                "/phones",
                "/phones/{id:\\w+}",
                "/update",
                "/login",
                "/register"
        })
        public String index() {
            return "forward:index.html";
        }
    }

}
