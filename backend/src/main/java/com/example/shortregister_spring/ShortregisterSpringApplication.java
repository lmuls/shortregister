package com.example.shortregister_spring;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ShortregisterSpringApplication {

//    public Hibernate5Module hibernateModule() {
//        Hibernate5Module module = new Hibernate5Module();
////        module.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
////        module.enable(Hibernate5Module.Feature.REPLACE_PERSISTENT_COLLECTIONS);
//        return module;
//    }
//
//    @Autowired
//    void configureObjectMapper(final ObjectMapper mapper) {
//        mapper.registerModule(new ParameterNamesModule())
//                .registerModule(hibernateModule());
//    }


    public static void main(String[] args) {
        SpringApplication.run(ShortregisterSpringApplication.class, args);
    }
}
