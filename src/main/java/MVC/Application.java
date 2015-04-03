package MVC;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.MultipartConfigElement;
import javax.sql.DataSource;

import MVC.persistence.entities.Phone;
import MVC.persistence.repositories.PhoneRepository;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CharacterEncodingFilter;


@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@SpringBootApplication
public class Application extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }


    @Autowired
    private Environment environment;	
	 
	  
	    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "true";
	    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "mvc";
	    private int test;
	 
	 
	    @Bean
	    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
	        entityManagerFactoryBean.setDataSource(dataSource());
	        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
	        entityManagerFactoryBean.setPackagesToScan(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN);
	 
	        entityManagerFactoryBean.setJpaProperties(hibernateProp());
	 
	        return entityManagerFactoryBean;
	    }
	 
	    @Bean
	    public JpaTransactionManager transactionManager() {
	        JpaTransactionManager transactionManager = new JpaTransactionManager();
	        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
	 
	        return transactionManager;
	    }
	 
	    @Bean
	    public DataSource dataSource() {
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	 
	        dataSource.setDriverClassName(environment.getRequiredProperty("db.driver"));
	        dataSource.setUrl(environment.getRequiredProperty("db.url"));
	        dataSource.setUsername(environment.getRequiredProperty("db.username"));
	        dataSource.setPassword(environment.getRequiredProperty("db.password"));
	 
	        return dataSource;
	    }
	 
	    private Properties hibernateProp() {
	        Properties properties = new Properties();
	        properties.put("hibernate.dialect", environment.getRequiredProperty("db.dialect"));
	        properties.put("hibernate.show_sql", PROPERTY_NAME_HIBERNATE_SHOW_SQL);
	        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("db.hibernate"));
	        return properties;
	    }

	    @Bean
	    public FilterRegistrationBean filterRegistrationBean() {
	        FilterRegistrationBean registrationBean = new FilterRegistrationBean();

	        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
	        registrationBean.setFilter(characterEncodingFilter);

	        characterEncodingFilter.setEncoding("UTF-8");
	        characterEncodingFilter.setForceEncoding(true);
	        registrationBean.setOrder(Integer.MIN_VALUE);
	        registrationBean.addUrlPatterns("/*");
	        return registrationBean;
	    }

	 //   private static final Logger log = Logger.getLogger(Application.class);
	    
    public static void main(String[] args) throws Throwable {
    	ConfigurableApplicationContext context=   SpringApplication.run(Application.class, args);
    }

}


@Component
class AccountCommandLineRunner implements CommandLineRunner{

    private static Date createNewDate(int day, int month, int year) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return formatter.parse("" + day + "/" + month + "/" + year);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
	@Override
	public void run(String... arg0) throws Exception {


        List images = Arrays.asList("img/phones/dell-streak-7.0.jpg",
                "img/phones/dell-streak-7.1.jpg",
                "img/phones/dell-streak-7.2.jpg",
                "img/phones/dell-streak-7.3.jpg",
                "img/phones/dell-streak-7.4.jpg");

        Phone phone = new Phone("Dell Streak 7",7, images,
                "Introducing Dell™ Streak 7",
                 565.0);
        phoneRepository.save(phone);


        images = Arrays.asList("img/phones/dell-venue.0.jpg",
                "img/phones/dell-venue.1.jpg",
                "img/phones/dell-venue.2.jpg",
                "img/phones/dell-venue.3.jpg",
                "img/phones/dell-venue.4.jpg",
                "img/phones/dell-venue.5.jpg");

        phone = new Phone("Dell Venue",20, images,
                "Smart Phone providing instant access to everything you love",
                750.0);
        phoneRepository.save(phone);


        images = Arrays.asList("img/phones/droid-2-global-by-motorola.0.jpg",
                "img/phones/droid-2-global-by-motorola.1.jpg",
                "img/phones/droid-2-global-by-motorola.2.jpg");

        phone = new Phone("Motorola",2, images,
                "With Quad Band GSM, the DROID 2 Global can send email",
                350.0);
        phoneRepository.save(phone);


        images = Arrays.asList("img/phones/droid-pro-by-motorola.0.jpg",
                "img/phones/droid-pro-by-motorola.1.jpg");

        phone = new Phone("Pro by Motorola",2, images,
                "Access your work directory, email or calendar with DROID Pro by Motorola",
                350.0);
        phoneRepository.save(phone);

        images = Arrays.asList("img/phones/lg-axis.0.jpg",
                "img/phones/lg-axis.1.jpg",
                "img/phones/lg-axis.2.jpg");

        phone = new Phone("LG Axis",5, images,
                "Android plus QWERTY is a powerful duo",
                450.0);
        phoneRepository.save(phone);

        images = Arrays.asList("img/phones/nexus-s.0.jpg",
                "img/phones/nexus-s.1.jpg",
                "img/phones/nexus-s.2.jpg",
                "img/phones/nexus-s.3.jpg");

        phone = new Phone("Nexus S",5, images,
                "Nexus S is the next generation of Nexus devices",
                550.0);
        phoneRepository.save(phone);




/*//		ArrayList list = (ArrayList) Arrays.asList("jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(","));
//		
//		for(int i=0; i<list.size();i++) {		
//		
//		Account account = accountRepository.save(new Account((String) list.get(i),
//				"password"));
*/		/*
		bookmarkRepository.save(new Bookmark(account,
				"http://bookmark.com/1/" + (String) list.get(i), "A description"));
		bookmarkRepository.save(new Bookmark(account,
				"http://bookmark.com/2/" + (String) list.get(i), "A description"));*/
		}
    @Autowired PhoneRepository phoneRepository;

	}


