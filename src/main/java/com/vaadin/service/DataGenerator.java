package com.vaadin.service;

import com.vaadin.entity.StudentsGroup;
import com.vaadin.entity.Student;
import com.vaadin.repository.StudentGroupRepository;
import com.vaadin.repository.StudentRepository;
import java.util.Arrays;
import java.util.HashSet;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;

@Configuration
public class DataGenerator {
    
    @Bean
    public CommandLineRunner commandLineRunner(StudentRepository studentRepository, StudentGroupRepository studentGroupRepository){
        return (run) -> {
            StudentsGroup studentsGroup1 = new StudentsGroup("FPM", new HashSet<>());
            if (!studentGroupRepository.findOne(Example.of(studentsGroup1)).isPresent()) {
                StudentsGroup studentsGroup2 = new StudentsGroup("Mech-math", new HashSet<>());
                studentGroupRepository.saveAll(Arrays.asList(studentsGroup1, studentsGroup2));
                Student student1 = new Student("Asd", "DSA", studentsGroup1);
                Student student2 = new Student("Qwe", "QWE", studentsGroup1);
                Student student3 = new Student("Zxc", "ZXC", studentsGroup2);
                Student student4 = new Student("Wer", "Wer", studentsGroup2);
                studentRepository.saveAll(Arrays.asList(student1, student2, student3, student4));
            }
        };
    }
}
