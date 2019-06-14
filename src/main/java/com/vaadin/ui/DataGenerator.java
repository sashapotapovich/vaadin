/*
package com.vaadin.ui;

import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataGenerator {
    
    @Bean
    public CommandLineRunner commandLineRunner(StudentRepository repository){
        return (run) -> {
            Student student1 = new Student("Asd", "DSA");
            Student student2 = new Student("Qwe", "QWE");
            Student student3 = new Student("Zxc", "ZXC");
            Student student4 = new Student("Wer", "Wer");
            repository.saveAll(Arrays.asList(student1, student2, student3, student4));
        };
    }
}
*/
