package MVC.api;

import MVC.persistence.entities.Phone;
import MVC.persistence.repositories.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Created by en on 15.04.2015.
 */
@Service
@Transactional()
public class CheckUpdateController {

    @Autowired
    private PhoneRepository phoneRepository;

    @Scheduled(fixedDelay = 120000000)
    private void checkPhones() {

        RestTemplate restTemplate = new RestTemplate();
        Phone[] phones = restTemplate.getForObject("http://localhost:8080/api/phones/", Phone[].class);

        for (Phone phone : Arrays.asList(phones)) {
            if (phone.getId() % 5 == 0) {
                phone.setId(null);
            }
            phoneRepository.saveAndFlush(phone);
        }
    }
}
