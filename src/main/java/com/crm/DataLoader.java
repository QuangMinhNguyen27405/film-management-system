package com.crm;

import com.crm.entity.*;
import com.crm.repository.*;
import com.crm.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private FilmServiceImpl filmServiceImpl;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private StoreService storeService;

    @Autowired
    private StaffServiceImpl staffServiceImpl;

    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private InventoryServiceImpl inventoryServiceImpl;

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadActors();
        loadLanguages();
        loadRoles();
        loadCustomers();
        loadFilms();
        loadStore();
        loadStaffs();
        loadInventorys();
    }

    private void loadCategories() {
        if (categoryService.fetchCategory().isEmpty()) {
            Category action = new Category();
            action.setName("Action");

            Category comedy = new Category();
            comedy.setName("Comedy");

            Category drama = new Category();
            drama.setName("Drama");

            Category horror = new Category();
            horror.setName("Horror");

            Category science = new Category();
            science.setName("Science-Fiction");

            Category animation = new Category();
            animation.setName("Animation");

            Category fantasy = new Category();
            fantasy.setName("Fantasy");

            Category adventure = new Category();
            adventure.setName("Adventure");

            Category thriller = new Category();
            thriller.setName("Thriller");

            categoryService.createCategory(action);
            categoryService.createCategory(comedy);
            categoryService.createCategory(drama);
            categoryService.createCategory(science);
            categoryService.createCategory(horror);
            categoryService.createCategory(animation);
            categoryService.createCategory(fantasy);
            categoryService.createCategory(adventure);
            categoryService.createCategory(thriller);
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
        if(customerService.fetchCustomers().isEmpty()){

            Customer admin = new Customer();

            List<Role> roles = new ArrayList<>();
            Role role = roleRepository.findByName("ROLE_ADMIN");
            roles.add(role);

            admin.setFirstName("admin");
            admin.setLastName("controll");
            admin.setEmail("admin@email.com");
            admin.setPassword("Steve@123");
            admin.setRoles(roles);
            admin.setActive(true);

            customerService.createCustomer(admin);
        }
    }

    public void loadFilms() {
        if(filmServiceImpl.fetchFilms().isEmpty()){

            Film filmTheCroods = new Film();
            filmTheCroods.setTitle("The Croods");

            // Create actors
            List<Actor> actorList = new ArrayList<>();

            Actor actor1 = new Actor();
            actor1.setFirstName("Nicolas");
            actor1.setLastName("Cage");

            Actor actor2 = new Actor();
            actor2.setFirstName("Emma");
            actor2.setLastName("Stone");

            actorList.add(actor1);
            actorList.add(actor2);

            filmTheCroods.setActors(actorList);

            // Create Categories
            List<Category> categoryList1 = new ArrayList<>();
            Optional<Category> category1 = categoryRepository.findByName("Animation");
            Optional<Category> category2 = categoryRepository.findByName("Comedy");
            if(category1.isPresent() && category2.isPresent()){
                categoryList1.add(category1.get());
                categoryList1.add(category2.get());
            }
            filmTheCroods.setCategories(categoryList1);

            // Create Language
            Optional<Language> dbLanguage = languageRepository.findByName("English");
            dbLanguage.ifPresent(filmTheCroods::setLanguage);

            // Description
            filmTheCroods.setDescription("The Croods is a prehistoric comedy adventure that follows the world’s first family" +
                                " as they embark on a journey of a lifetime when the cave that has always shielded them" +
                                " from danger is destroyed");

            // Release Year
            filmTheCroods.setReleaseYear(2013);

            // Rental Rate
            filmTheCroods.setRentalRate(BigDecimal.valueOf(1.12));

            // Replacement Cost
            filmTheCroods.setReplacementCost(BigDecimal.valueOf(10));

            // Full Text
            filmTheCroods.setFullText("Family, Adventure, Comedy");

            // Picture URL
            filmTheCroods.setPictureUrl("/dummy/dummy-cro.jpg");

            // Logo URL
            filmTheCroods.setLogoUrl("/dummy/logo-cro.jpg");

            // Special Features
            filmTheCroods.setSpecialFeatures("Deleted Scenes, Behind the Scenes");

            // Length
            filmTheCroods.setLength(123);

            // Rating
            filmTheCroods.setRating(3L);

            filmServiceImpl.createFilm(filmTheCroods);

            Film filmTheHobbit = new Film();
            filmTheHobbit.setTitle("The Hobbit");

            // Create actors
            List<Actor> actorListHobbit = new ArrayList<>();

            Actor actor3 = new Actor();
            actor3.setFirstName("Martin");
            actor3.setLastName("Freeman");

            Actor actor4 = new Actor();
            actor4.setFirstName("Ian");
            actor4.setLastName("McKellen");

            actorListHobbit.add(actor3);
            actorListHobbit.add(actor4);

            filmTheHobbit.setActors(actorListHobbit);

            // Create Categories
            List<Category> categoryList2 = new ArrayList<>();
            Optional<Category> category3 = categoryRepository.findByName("Action");
            Optional<Category> category4 = categoryRepository.findByName("Adventure");
            Optional<Category> category5 = categoryRepository.findByName("Fantasy");
            if(category3.isPresent() && category4.isPresent() && category5.isPresent()){
                categoryList2.add(category3.get());
                categoryList2.add(category4.get());
                categoryList2.add(category5.get());
            }
            filmTheHobbit.setCategories(categoryList2);

            // Create Language
            Optional<Language> dbLanguageHobbit = languageRepository.findByName("English");
            dbLanguageHobbit.ifPresent(filmTheHobbit::setLanguage);

            // Description
            filmTheHobbit.setDescription("The Hobbit is a series of three epic fantasy adventure films directed by Peter Jackson. The films are based on the 1937 novel 'The Hobbit' by J. R. R. Tolkien, with the story set in Middle-earth sixty years before the events of 'The Lord of the Rings'.");

            // Release Year
            filmTheHobbit.setReleaseYear(2012);

            // Rental Rate
            filmTheHobbit.setRentalRate(BigDecimal.valueOf(2.00));

            // Replacement Cost
            filmTheHobbit.setReplacementCost(BigDecimal.valueOf(20));

            // Full Text
            filmTheHobbit.setFullText("Fantasy, Adventure, Epic");

            // Picture URL
            filmTheHobbit.setPictureUrl("/dummy/dummy-hob.jpg");

            // Logo URL
            filmTheHobbit.setLogoUrl("/dummy/logo-hob.jpg");

            // Special Features
            filmTheHobbit.setSpecialFeatures("Extended Edition, Behind the Scenes");

            // Length
            filmTheHobbit.setLength(169);

            // Rating
            filmTheHobbit.setRating(5L);

            filmServiceImpl.createFilm(filmTheHobbit);

            // Film Maleficent
            Film filmMaleficent = new Film();
            filmMaleficent.setTitle("Maleficent");

            // Create actors
            List<Actor> actorListMaleficent = new ArrayList<>();

            Actor actorMaleficent1 = new Actor();
            actorMaleficent1.setFirstName("Angelina");
            actorMaleficent1.setLastName("Jolie");

            Actor actorMaleficent2 = new Actor();
            actorMaleficent2.setFirstName("Elle");
            actorMaleficent2.setLastName("Fanning");

            actorListMaleficent.add(actorMaleficent1);
            actorListMaleficent.add(actorMaleficent2);

            filmMaleficent.setActors(actorListMaleficent);

            // Create Categories
            List<Category> categoryListMaleficent = new ArrayList<>();
            Optional<Category> categoryM1 = categoryRepository.findByName("Action");
            Optional<Category> categoryM2 = categoryRepository.findByName("Adventure");
            Optional<Category> categoryM3 = categoryRepository.findByName("Fantasy");
            if (categoryM1.isPresent() && categoryM2.isPresent() && categoryM3.isPresent()) {
                categoryListMaleficent.add(categoryM1.get());
                categoryListMaleficent.add(categoryM2.get());
                categoryListMaleficent.add(categoryM3.get());
            }
            filmMaleficent.setCategories(categoryListMaleficent);

            // Create Language
            Optional<Language> dbLanguageMaleficent = languageRepository.findByName("English");
            dbLanguageMaleficent.ifPresent(filmMaleficent::setLanguage);

            // Description
            filmMaleficent.setDescription("Maleficent is a 2014 American fantasy film directed by Robert Stromberg, starring Angelina Jolie as the title character. The film is a live-action reimagining of Disney's 1959 animated film Sleeping Beauty.");

            // Release Year
            filmMaleficent.setReleaseYear(2014);

            // Rental Rate
            filmMaleficent.setRentalRate(BigDecimal.valueOf(2.50));

            // Replacement Cost
            filmMaleficent.setReplacementCost(BigDecimal.valueOf(25));

            // Full Text
            filmMaleficent.setFullText("Fantasy, Adventure, Action");

            // Picture URL
            filmMaleficent.setPictureUrl("/dummy/dummy-male.jpg");

            // Logo URL
            filmMaleficent.setLogoUrl("/dummy/logo-male.jpg");

            // Special Features
            filmMaleficent.setSpecialFeatures("Deleted Scenes, Director's Commentary");

            // Length
            filmMaleficent.setLength(97);

            // Rating
            filmMaleficent.setRating(4L);

            filmServiceImpl.createFilm(filmMaleficent);


            // Film Life of Pi
            Film filmLifeOfPi = new Film();
            filmLifeOfPi.setTitle("Life of Pi");

            // Create actors
            List<Actor> actorListLifeOfPi = new ArrayList<>();

            Actor actorLifeOfPi1 = new Actor();
            actorLifeOfPi1.setFirstName("Suraj");
            actorLifeOfPi1.setLastName("Sharma");

            Actor actorLifeOfPi2 = new Actor();
            actorLifeOfPi2.setFirstName("Irrfan");
            actorLifeOfPi2.setLastName("Khan");

            actorListLifeOfPi.add(actorLifeOfPi1);
            actorListLifeOfPi.add(actorLifeOfPi2);

            filmLifeOfPi.setActors(actorListLifeOfPi);

            // Create Categories
            List<Category> categoryListLifeOfPi = new ArrayList<>();
            Optional<Category> categoryLP1 = categoryRepository.findByName("Adventure");
            Optional<Category> categoryLP2 = categoryRepository.findByName("Drama");
            Optional<Category> categoryLP3 = categoryRepository.findByName("Fantasy");
            if (categoryLP1.isPresent() && categoryLP2.isPresent() && categoryLP3.isPresent()) {
                categoryListLifeOfPi.add(categoryLP1.get());
                categoryListLifeOfPi.add(categoryLP2.get());
                categoryListLifeOfPi.add(categoryLP3.get());
            }
            filmLifeOfPi.setCategories(categoryListLifeOfPi);

            // Create Language
            Optional<Language> dbLanguageLifeOfPi = languageRepository.findByName("English");
            dbLanguageLifeOfPi.ifPresent(filmLifeOfPi::setLanguage);

            // Description
            filmLifeOfPi.setDescription("Life of Pi is a 2012 adventure-drama film directed by Ang Lee. Based on the novel by Yann Martel, it tells the story of a young man who survives a shipwreck and is stranded on a lifeboat with a Bengal tiger.");

            // Release Year
            filmLifeOfPi.setReleaseYear(2012);

            // Rental Rate
            filmLifeOfPi.setRentalRate(BigDecimal.valueOf(2.75));

            // Replacement Cost
            filmLifeOfPi.setReplacementCost(BigDecimal.valueOf(28));

            // Full Text
            filmLifeOfPi.setFullText("Adventure, Drama, Fantasy");

            // Picture URL
            filmLifeOfPi.setPictureUrl("/dummy/dummy-pi.jpg");

            // Logo URL
            filmLifeOfPi.setLogoUrl("/dummy/logo-pi.jpg");

            // Special Features
            filmLifeOfPi.setSpecialFeatures("3D Edition, Making of");

            // Length
            filmLifeOfPi.setLength(127);

            // Rating
            filmLifeOfPi.setRating(4L);

            filmServiceImpl.createFilm(filmLifeOfPi);

            // Film Exists
            Film filmExists = new Film();
            filmExists.setTitle("Exists");

            // Create actors
            List<Actor> actorListExists = new ArrayList<>();

            Actor actorExists1 = new Actor();
            actorExists1.setFirstName("Chris");
            actorExists1.setLastName("Osborn");

            Actor actorExists2 = new Actor();
            actorExists2.setFirstName("Dora");
            actorExists2.setLastName("Madison");

            actorListExists.add(actorExists1);
            actorListExists.add(actorExists2);

            filmExists.setActors(actorListExists);

            // Create Categories
            List<Category> categoryListExists = new ArrayList<>();
            Optional<Category> categoryE1 = categoryRepository.findByName("Horror");
            Optional<Category> categoryE2 = categoryRepository.findByName("Thriller");
            if (categoryE1.isPresent() && categoryE2.isPresent()) {
                categoryListExists.add(categoryE1.get());
                categoryListExists.add(categoryE2.get());
            }
            filmExists.setCategories(categoryListExists);

            // Create Language
            Optional<Language> dbLanguageExists = languageRepository.findByName("English");
            dbLanguageExists.ifPresent(filmExists::setLanguage);

            // Description
            filmExists.setDescription("Exists is a 2014 found footage horror film directed by Eduardo Sánchez. It centers on a group of friends who encounter the legendary Bigfoot while on a camping trip in the remote Texas woods.");

            // Release Year
            filmExists.setReleaseYear(2014);

            // Rental Rate
            filmExists.setRentalRate(BigDecimal.valueOf(2.00));

            // Replacement Cost
            filmExists.setReplacementCost(BigDecimal.valueOf(20));

            // Full Text
            filmExists.setFullText("Horror, Thriller, Found Footage");

            // Picture URL
            filmExists.setPictureUrl("/dummy/dummy-exi.jpg");

            // Logo URL
            filmExists.setLogoUrl("/dummy/logo-exi.jpg");

            // Special Features
            filmExists.setSpecialFeatures("Deleted Scenes, Making of");

            // Length
            filmExists.setLength(81);

            // Rating
            filmExists.setRating(3L);

            filmServiceImpl.createFilm(filmExists);

            // Film Robocop
            Film filmRobocop = new Film();
            filmRobocop.setTitle("Robocop");
            
            // Create actors
            List<Actor> actorListRobocop = new ArrayList<>();
            
            Actor actorRobocop1 = new Actor();
            actorRobocop1.setFirstName("Joel");
            actorRobocop1.setLastName("Kinnaman");
            
            Actor actorRobocop2 = new Actor();
            actorRobocop2.setFirstName("Gary");
            actorRobocop2.setLastName("Oldman");
            
            actorListRobocop.add(actorRobocop1);
            actorListRobocop.add(actorRobocop2);
            
            filmRobocop.setActors(actorListRobocop);
            
            // Create Categories
            List<Category> categoryListRobocop = new ArrayList<>();
            Optional<Category> categoryR1 = categoryRepository.findByName("Action");
            Optional<Category> categoryR2 = categoryRepository.findByName("Science-Fiction");
            if (categoryR1.isPresent() && categoryR2.isPresent()) {
                categoryListRobocop.add(categoryR1.get());
                categoryListRobocop.add(categoryR2.get());
            }
            filmRobocop.setCategories(categoryListRobocop);
            
            // Create Language
            Optional<Language> dbLanguageRobocop = languageRepository.findByName("English");
            dbLanguageRobocop.ifPresent(filmRobocop::setLanguage);
            
            // Description
            filmRobocop.setDescription("RoboCop is a 2014 American cyberpunk superhero film directed by José Padilha. It is a remake of the 1987 film of the same name and stars Joel Kinnaman as the title character.");
            
            // Release Year
            filmRobocop.setReleaseYear(2014);
            
            // Rental Rate
            filmRobocop.setRentalRate(BigDecimal.valueOf(2.50));
            
            // Replacement Cost
            filmRobocop.setReplacementCost(BigDecimal.valueOf(25));
            
            // Full Text
            filmRobocop.setFullText("Action, Science-Fiction, Cyberpunk");
            
            // Picture URL
            filmRobocop.setPictureUrl("/dummy/dummy-robo.jpg");

            // Logo URL
            filmRobocop.setLogoUrl("/dummy/logo-robo.jpg");
            
            // Special Features
            filmRobocop.setSpecialFeatures("Director's Cut, Behind the Scenes");
            
            // Length
            filmRobocop.setLength(117);
            
            // Rating
            filmRobocop.setRating(3L);
            
            filmServiceImpl.createFilm(filmRobocop);
            
            
            // Film Drive Hard
            Film filmDriveHard = new Film();
            filmDriveHard.setTitle("Drive Hard");
            
            // Create actors
            List<Actor> actorListDriveHard = new ArrayList<>();
            
            Actor actorDriveHard1 = new Actor();
            actorDriveHard1.setFirstName("John");
            actorDriveHard1.setLastName("Cusack");
            
            Actor actorDriveHard2 = new Actor();
            actorDriveHard2.setFirstName("Thomas");
            actorDriveHard2.setLastName("Jane");
            
            actorListDriveHard.add(actorDriveHard1);
            actorListDriveHard.add(actorDriveHard2);
            
            filmDriveHard.setActors(actorListDriveHard);
            
            // Create Categories
            List<Category> categoryListDriveHard = new ArrayList<>();
            Optional<Category> categoryDH1 = categoryRepository.findByName("Action");
            Optional<Category> categoryDH2 = categoryRepository.findByName("Comedy");
            if (categoryDH1.isPresent() && categoryDH2.isPresent()) {
                categoryListDriveHard.add(categoryDH1.get());
                categoryListDriveHard.add(categoryDH2.get());
            }
            filmDriveHard.setCategories(categoryListDriveHard);
            
            // Create Language
            Optional<Language> dbLanguageDriveHard = languageRepository.findByName("English");
            dbLanguageDriveHard.ifPresent(filmDriveHard::setLanguage);
            
            // Description
            filmDriveHard.setDescription("Drive Hard is a 2014 action comedy film directed by Brian Trenchard-Smith. It stars John Cusack and Thomas Jane in a story about a former race car driver who is kidnapped by a mysterious thief.");
            
            // Release Year
            filmDriveHard.setReleaseYear(2014);
            
            // Rental Rate
            filmDriveHard.setRentalRate(BigDecimal.valueOf(2.25));
            
            // Replacement Cost
            filmDriveHard.setReplacementCost(BigDecimal.valueOf(22));
            
            // Full Text
            filmDriveHard.setFullText("Action, Comedy, Heist");
            
            // Picture URL
            filmDriveHard.setPictureUrl("/dummy/dummy-dri.jpg");

            // Logo Url
            filmDriveHard.setLogoUrl("/dummy/logo-dri.jpg");
            
            // Special Features
            filmDriveHard.setSpecialFeatures("Bloopers, Deleted Scenes");
            
            // Length
            filmDriveHard.setLength(92);
            
            // Rating
            filmDriveHard.setRating(3L);
            
            filmServiceImpl.createFilm(filmDriveHard);
            
            
            // Film The Colony
            Film filmTheColony = new Film();
            filmTheColony.setTitle("The Colony");
            
            // Create actors
            List<Actor> actorListTheColony = new ArrayList<>();
            
            Actor actorTheColony1 = new Actor();
            actorTheColony1.setFirstName("Laurence");
            actorTheColony1.setLastName("Fishburne");
            
            Actor actorTheColony2 = new Actor();
            actorTheColony2.setFirstName("Bill");
            actorTheColony2.setLastName("Paxton");
            
            actorListTheColony.add(actorTheColony1);
            actorListTheColony.add(actorTheColony2);
            
            filmTheColony.setActors(actorListTheColony);
            
            // Create Categories
            List<Category> categoryListTheColony = new ArrayList<>();
            Optional<Category> categoryTC1 = categoryRepository.findByName("Action");
            Optional<Category> categoryTC2 = categoryRepository.findByName("Science-Fiction");
            Optional<Category> categoryTC3 = categoryRepository.findByName("Thriller");
            if (categoryTC1.isPresent() && categoryTC2.isPresent() && categoryTC3.isPresent()) {
                categoryListTheColony.add(categoryTC1.get());
                categoryListTheColony.add(categoryTC2.get());
                categoryListTheColony.add(categoryTC3.get());
            }
            filmTheColony.setCategories(categoryListTheColony);
            
            // Create Language
            Optional<Language> dbLanguageTheColony = languageRepository.findByName("English");
            dbLanguageTheColony.ifPresent(filmTheColony::setLanguage);
            
            // Description
            filmTheColony.setDescription("The Colony is a 2013 Canadian science fiction horror film directed by Jeff Renfroe. It stars Laurence Fishburne and Bill Paxton in a story about survivors of an ice age living underground and facing threats from feral humans.");
            
            // Release Year
            filmTheColony.setReleaseYear(2013);
            
            // Rental Rate
            filmTheColony.setRentalRate(BigDecimal.valueOf(2.00));
            
            // Replacement Cost
            filmTheColony.setReplacementCost(BigDecimal.valueOf(20));
            
            // Full Text
            filmTheColony.setFullText("Action, Science-Fiction, Horror");
            
            // Picture URL
            filmTheColony.setPictureUrl("/dummy/dummy-col.jpg");

            // Logo URL
            filmTheColony.setLogoUrl("/dummy/logo-col.jpg");
            
            // Special Features
            filmTheColony.setSpecialFeatures("Director's Commentary, Making of");
            
            // Length
            filmTheColony.setLength(95);
            
            // Rating
            filmTheColony.setRating(3L);
            
            filmServiceImpl.createFilm(filmTheColony);

            // Film Tin Tin
            Film filmTintin = new Film();
            filmTintin.setTitle("The Adventures of Tintin");

            // Create actors
            List<Actor> actorListTintin = new ArrayList<>();

            Actor actorTintin1 = new Actor();
            actorTintin1.setFirstName("Jamie");
            actorTintin1.setLastName("Bell");

            Actor actorTintin2 = new Actor();
            actorTintin2.setFirstName("Andy");
            actorTintin2.setLastName("Serkis");

            actorListTintin.add(actorTintin1);
            actorListTintin.add(actorTintin2);

            filmTintin.setActors(actorListTintin);

            // Create Categories
            List<Category> categoryListTintin = new ArrayList<>();
            Optional<Category> dbAdventureTintin = categoryRepository.findByName("Adventure");
            Optional<Category> dbAnimationTintin = categoryRepository.findByName("Animation");
            dbAdventureTintin.ifPresent(categoryListTintin::add);
            dbAnimationTintin.ifPresent(categoryListTintin::add);
            filmTintin.setCategories(categoryListTintin);

            // Create Language
            Optional<Language> dbLanguageTintin = languageRepository.findByName("English");
            dbLanguageTintin.ifPresent(filmTintin::setLanguage);

            // Description
            filmTintin.setDescription("The Adventures of Tintin is a 3D motion capture computer-animated action-adventure film based on The Adventures of Tintin, the comic book series created by Belgian cartoonist Hergé.");

            // Release Year
            filmTintin.setReleaseYear(2011);

            // Rental Rate
            filmTintin.setRentalRate(BigDecimal.valueOf(1.50));

            // Replacement Cost
            filmTintin.setReplacementCost(BigDecimal.valueOf(20));

            // Full Text
            filmTintin.setFullText("Family, Adventure, Animation");

            // Picture URL
            filmTintin.setPictureUrl("/dummy/dummy-tin.jpg");

            // Logo URL
            filmTintin.setLogoUrl("/dummy/logo-tin.jpg");

            // Special Features
            filmTintin.setSpecialFeatures("Behind the Scenes, Deleted Scenes");

            // Length
            filmTintin.setLength(107);

            // Rating
            filmTintin.setRating(4L);

            // Save the film
            filmServiceImpl.createFilm(filmTintin);
        }
    }

    public void loadStore(){
        if(storeService.fetchStores().isEmpty()){

            Store store1 = new Store();

            Address store1Address = new Address();
            store1Address.setAddress("1980 Woodward Ave, Tally, Florida");

            store1.setAddress(store1Address);

            Store store2 = new Store();

            Address store2Address = new Address();
            store2Address.setAddress("17 Duy Tan");

            store2.setAddress(store2Address);

            storeService.createStore(store1);
            storeService.createStore(store2);
        }
    }

    public void loadStaffs() {
        if (staffServiceImpl.fetchStaffs().isEmpty()) {
            // Ensure stores are managed entities
            Optional<Store> store1Optional = storeRepository.findByAddress("1980 Woodward Ave, Tally, Florida");
            Optional<Store> store2Optional = storeRepository.findByAddress("17 Duy Tan");

            if (store1Optional.isEmpty()) {
                Store store1 = new Store();
                store1.setAddress(new Address("1980 Woodward Ave, Tally, Florida"));
                store1 = storeRepository.save(store1); // Persist and get managed entity
                store1Optional = Optional.of(store1);
            }

            if (store2Optional.isEmpty()) {
                Store store2 = new Store();
                store2.setAddress(new Address("17 Duy Tan"));
                store2 = storeRepository.save(store2); // Persist and get managed entity
                store2Optional = Optional.of(store2);
            }

            // Create and persist staff1
            Staff staff1 = new Staff();
            staff1.setFirstName("Hoang");
            staff1.setLastName("Vu");
            staff1.setEmail("Hoangice@email.com");
            staff1.setUsername("hoangvu");
            staff1.setActive(true);
            staff1.setAddress(new Address("19 Duy Tan"));
            staff1.setPictureUrl("/dummy/person-1.jpg");
            store2Optional.ifPresent(staff1::setStore); // Assign store2 to staff1
            staffServiceImpl.createStaff(staff1);

            // Create and persist staff2
            Staff staff2 = new Staff();
            staff2.setFirstName("John");
            staff2.setLastName("Doe");
            staff2.setEmail("johndoe@email.com");
            staff2.setUsername("johndoe");
            staff2.setActive(true);
            staff2.setAddress(new Address("21 Duy Tan"));
            staff2.setPictureUrl("/dummy/person-2.jpg");
            store2Optional.ifPresent(staff2::setStore); // Assign store2 to staff2
            staffServiceImpl.createStaff(staff2);

            // Create and persist staff3
            Staff staff3 = new Staff();
            staff3.setFirstName("Alice");
            staff3.setLastName("Smith");
            staff3.setEmail("alicesmith@email.com");
            staff3.setUsername("alicesmith");
            staff3.setActive(true);
            staff3.setAddress(new Address("789 Oak Avenue"));
            staff3.setPictureUrl("/dummy/person-3.jpg");
            store1Optional.ifPresent(staff3::setStore); // Assign store1 to staff3
            staffServiceImpl.createStaff(staff3);

            // Create and persist staff4
            Staff staff4 = new Staff();
            staff4.setFirstName("Emma");
            staff4.setLastName("Johnson");
            staff4.setEmail("emmajohnson@email.com");
            staff4.setUsername("emmajohnson");
            staff4.setActive(true);
            staff4.setAddress(new Address("45 Elm Street"));
            staff4.setPictureUrl("/dummy/person-4.jpg");
            store1Optional.ifPresent(staff4::setStore); // Assign store1 to staff4
            staffServiceImpl.createStaff(staff4);

            // Create and persist staff5
            Staff staff5 = new Staff();
            staff5.setFirstName("Michael");
            staff5.setLastName("Brown");
            staff5.setEmail("michaelbrown@email.com");
            staff5.setUsername("michaelbrown");
            staff5.setActive(true);
            staff5.setPictureUrl("/dummy/person-1.jpg");
            staff5.setAddress(new Address("123 Maple Road"));
            store2Optional.ifPresent(staff5::setStore); // Assign store2 to staff5
            staffServiceImpl.createStaff(staff5);
        }
    }

    private void loadInventorys(){
        if(inventoryServiceImpl.fetchInventory().isEmpty()){
            List<Film> filmList = filmServiceImpl.fetchFilms();

            for (int i = 1; i <= 30; i++) {
                Inventory inventory = new Inventory();
                inventory.setFilm(filmList.get(i % filmList.size())); // Assign films cyclically
                inventoryServiceImpl.createInventory(inventory);
            }
        }
    }
}
