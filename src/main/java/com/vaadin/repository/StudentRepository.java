package com.vaadin.repository;

import com.vaadin.entity.Student;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, Long> {
    
    Student findByFirstNameLikeAndLastNameLike(String firstName, String lastName);
    
    List<Student> findAllByStudentsGroup_GroupNameLike(String groupName);
    
    List<Student> findAllByFirstNameContainsOrFirstNameContains(String firstName, String firstName2);
    
    List<Student> findAllByLastNameContains(String lastName);
    
    
}
