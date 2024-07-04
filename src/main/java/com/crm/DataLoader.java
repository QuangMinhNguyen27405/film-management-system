package com.crm;

import com.crm.entity.*;
import com.crm.repository.*;
import com.crm.service.impl.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadActors();
        loadLanguages();
        loadRoles();
        loadCustomers();
    }

    private void loadCategories() {
        if (categoryRepository.count() == 0) {
            Category action = new Category();
            action.setName("Action");

            Category comedy = new Category();
            comedy.setName("Comedy");

            Category drama = new Category();
            drama.setName("Drama");

            categoryRepository.save(action);
            categoryRepository.save(comedy);
            categoryRepository.save(drama);
        }
    }

    private void loadActors() {
        if (actorRepository.count() == 0) {
            Actor leo = new Actor();
            leo.setFirstName("Leonardo");
            leo.setLastName("DiCaprio");

            Actor brad = new Actor();
            brad.setFirstName("Brad");
            brad.setLastName("Pitt");

            Actor meryl = new Actor();
            meryl.setFirstName("Meryl");
            meryl.setLastName("Streep");

            actorRepository.save(leo);
            actorRepository.save(brad);
            actorRepository.save(meryl);
        }
    }

    public void loadLanguages() {
        if (languageRepository.count() == 0) {
            List<Language> languages = new ArrayList<>();

            Language english = new Language();
            english.setName("English");
            languages.add(english);

            Language spanish = new Language();
            spanish.setName("Spanish");
            languages.add(spanish);

            Language french = new Language();
            french.setName("French");
            languages.add(french);

            Language german = new Language();
            german.setName("German");
            languages.add(german);

            Language italian = new Language();
            italian.setName("Italian");
            languages.add(italian);

            languageRepository.saveAll(languages);
        }
    }

    public void loadRoles(){
        if(roleRepository.count() == 0){
            List<Role> roles = new ArrayList<>();

            Role admin = new Role();
            admin.setName("ROLE_ADMIN");
            roles.add(admin);

            Role user = new Role();
            user.setName("ROLE_USER");
            roles.add(user);

            roleRepository.saveAll(roles);

        }
    }

    public void loadCustomers(){
        if(customerRepository.count() == 0){

            Customer admin = new Customer();

            List<Role> roles = new ArrayList<>();
            Role role = roleRepository.findByName("ROLE_ADMIN");
            roles.add(role);

            admin.setFirstName("admin");
            admin.setLastName("controll");
            admin.setEmail("admin@email.com");
            admin.setPassword("Steve@123");
            admin.setRoles(roles);

            customerService.createCustomer(admin);
        }
    }
}
