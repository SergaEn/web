package MVC;

import MVC.persistence.entities.Phone;
import MVC.persistence.entities.Visa;
import MVC.persistence.repositories.PhoneRepository;
import MVC.persistence.repositories.VisaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by varArg on 04.04.2015.
 */

@Component
class CommandRunner implements CommandLineRunner {

    @Autowired
    VisaRepository visaRepository;

    @Override
    public void run(String... arg0) throws Exception {

        visaRepository.save(new Visa(500.5, "firstName", "lastName", "visa", "987654321", 888, new GregorianCalendar(2016, 5, 20)));
        visaRepository.save(new Visa(99999.5, "Sergei", "En", "visa", "123456789", 999, new GregorianCalendar(2015, 5, 20)));


        List images = Arrays.asList("img/phones/dell-streak-7.0.jpg",
                "img/phones/dell-streak-7.1.jpg",
                "img/phones/dell-streak-7.2.jpg",
                "img/phones/dell-streak-7.3.jpg",
                "img/phones/dell-streak-7.4.jpg");

        Phone phone = new Phone("Dell Streak 7", 7, images,
                "Introducing Dell™ Streak 7",
                565.0);
        phoneRepository.save(phone);


        images = Arrays.asList("img/phones/dell-venue.0.jpg",
                "img/phones/dell-venue.1.jpg",
                "img/phones/dell-venue.2.jpg",
                "img/phones/dell-venue.3.jpg",
                "img/phones/dell-venue.4.jpg",
                "img/phones/dell-venue.5.jpg");

        phone = new Phone("Dell Venue", 20, images,
                "Smart Phone providing instant access to everything you love",
                750.0);
        phoneRepository.save(phone);


        images = Arrays.asList("img/phones/droid-2-global-by-motorola.0.jpg",
                "img/phones/droid-2-global-by-motorola.1.jpg",
                "img/phones/droid-2-global-by-motorola.2.jpg");

        phone = new Phone("Motorola", 2, images,
                "With Quad Band GSM, the DROID 2 Global can send email",
                350.0);
        phoneRepository.save(phone);


        images = Arrays.asList("img/phones/droid-pro-by-motorola.0.jpg",
                "img/phones/droid-pro-by-motorola.1.jpg");

        phone = new Phone("Pro by Motorola", 2, images,
                "Access your work directory, email or calendar with DROID Pro by Motorola",
                350.0);
        phoneRepository.save(phone);

        images = Arrays.asList("img/phones/lg-axis.0.jpg",
                "img/phones/lg-axis.1.jpg",
                "img/phones/lg-axis.2.jpg");

        phone = new Phone("LG Axis", 5, images,
                "Android plus QWERTY is a powerful duo",
                450.0);
        phoneRepository.save(phone);

        images = Arrays.asList("img/phones/nexus-s.0.jpg",
                "img/phones/nexus-s.1.jpg",
                "img/phones/nexus-s.2.jpg",
                "img/phones/nexus-s.3.jpg");

        phone = new Phone("Nexus S", 5, images,
                "Nexus S is the next generation of Nexus devices",
                550.0);
        phoneRepository.save(phone);

        images = Arrays.asList("img/phones/motorola-atrix-4g.0.jpg",
                "img/phones/motorola-atrix-4g.1.jpg",
                "img/phones/motorola-atrix-4g.2.jpg",
                "img/phones/motorola-atrix-4g.3.jpg");

        phone = new Phone("MOTOROLA ATRIX\u2122 4G", 15, images,
                "MOTOROLA ATRIX 4G gives you dual-core processing power and the revolutionary webtop application",
                220.0);
        phoneRepository.save(phone);

        images = Arrays.asList("img/phones/motorola-bravo-with-motoblur.0.jpg",
                "img/phones/motorola-bravo-with-motoblur.1.jpg",
                "img/phones/motorola-bravo-with-motoblur.2.jpg");

        phone = new Phone("MOTOROLA BRAVO\u2122 with MOTOBLUR\u2122", 55, images,
                "MOTOROLA BRAVO\u2122 with MOTOBLUR\u2122 with its large 3.7-inch touchscreen and web-browsing capabilities is sure to make an impression.",
                1254.0);
        phoneRepository.save(phone);

        images = Arrays.asList("img/phones/motorola-xoom.0.jpg",
                "img/phones/motorola-xoom.1.jpg",
                "img/phones/motorola-xoom.2.jpg");

        phone = new Phone("MOTOROLA XOOM\u2122", 10, images,
                "MOTOROLA XOOM has a super-powerful dual-core processor and Android\u2122 3.0 (Honeycomb)",
                155.0);
        phoneRepository.save(phone);

        images = Arrays.asList("img/phones/samsung-galaxy-tab.0.jpg",
                "img/phones/samsung-galaxy-tab.1.jpg",
                "img/phones/samsung-galaxy-tab.2.jpg",
                "img/phones/samsung-galaxy-tab.3.jpg",
                "img/phones/samsung-galaxy-tab.4.jpg",
                "img/phones/samsung-galaxy-tab.5.jpg",
                "img/phones/samsung-galaxy-tab.6.jpg");

        phone = new Phone("Samsung Galaxy Tab\u2122", 10, images,
                "Feel Free to Tab\u2122. The Samsung Galaxy Tab\u2122, the tablet device that delivers enhanced capabilities with advanced mobility",
                1550.0);
        phoneRepository.save(phone);

        images = Arrays.asList("img/phones/samsung-galaxy-tab.0.jpg",
                "img/phones/samsung-galaxy-tab.1.jpg",
                "img/phones/samsung-galaxy-tab.2.jpg",
                "img/phones/samsung-galaxy-tab.3.jpg",
                "img/phones/samsung-galaxy-tab.4.jpg",
                "img/phones/samsung-galaxy-tab.5.jpg",
                "img/phones/samsung-galaxy-tab.6.jpg");

        phone = new Phone("SANYO ZIO", 10, images,
                "Zio uses CDMA2000 1xEV-DO rev. A and Wi-Fi technologies and features a 3.5-inch WVGA touch-screen display as a backdrop",
                951.0);
        phoneRepository.save(phone);
    }

    @Autowired
    PhoneRepository phoneRepository;
}