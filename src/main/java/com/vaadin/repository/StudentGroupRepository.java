package com.vaadin.repository;

import com.vaadin.entity.StudentsGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentGroupRepository extends JpaRepository<StudentsGroup, Long> {
}
