package MVC.api;

import MVC.persistence.entities.Phone;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Created by en on 15.04.2015.
 */
@Service
public class CheckUpdateController {

   @Scheduled(fixedDelay=60000)
    private void chechphones() {
        RestTemplate restTemplate = new RestTemplate();
        Phone[] phones = restTemplate.getForObject("http://localhost:8080/api/phones/", Phone[].class);
        Arrays.asList(phones);
        for (Phone phone: phones){
            System.out.println(phone.toString());
        }
    }
}
