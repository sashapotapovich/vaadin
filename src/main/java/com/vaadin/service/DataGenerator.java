/*
package com.vaadin.service;

import com.vaadin.entity.Student;
import com.vaadin.entity.StudentsGroup;
import com.vaadin.repository.StudentGroupRepository;
import com.vaadin.repository.StudentRepository;
import com.vaadin.repository.TestCaseRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;

@Slf4j
@Configuration
public class DataGenerator {

    @Bean
    public CommandLineRunner commandLineRunner(StudentRepository studentRepository, StudentGroupRepository studentGroupRepository,
                                               TestCaseRepository testCaseRepository) {
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
            */
/*TestModule testModule1 = new TestModule("New Test Case 1 Question \"Short Description\"",
                                                    Arrays.asList(new Answer("ssss", false),
                                                                         new Answer("dddd", true),
                                                                         new Answer("gggg", false),
                                                                         new Answer("sdad", false)));
            TestModule testModule2 = new TestModule("New Test Case 2 Question \"Short Description\"",
                                                    Arrays.asList(new Answer("ssss", false),
                                                                         new Answer("dddd", true),
                                                                         new Answer("gggg", false),
                                                                         new Answer("sdad", false)));
            TestModule testModule3 = new TestModule("New Test Case 3 Question \"Short Description\"",
                                                    Arrays.asList(new Answer("ssss", false),
                                                                         new Answer("dddd", true),
                                                                         new Answer("gggg", false),
                                                                         new Answer("sdad", false)));
            TestModule testModule4 = new TestModule("New Test Case 4 Question \"Short Description\"",
                                                    Arrays.asList(new Answer("ssss", false),
                                                                         new Answer("dddd", true),
                                                                         new Answer("gggg", false),
                                                                         new Answer("sdad", false)));
            List<TestModule> tests = new ArrayList<>();
            tests.add(testModule1);
            tests.add(testModule2);
            tests.add(testModule3);
            tests.add(testModule4);
            Gson gson = new Gson();
            String s = gson.toJson(tests.toArray(new TestModule[0]), TestModule[].class);
            log.error(s);
            TestCase testCase = new TestCase("Simple test for students", 65, s);
            testCaseRepository.saveAndFlush(testCase);*//*

        };
    }
}
*/
