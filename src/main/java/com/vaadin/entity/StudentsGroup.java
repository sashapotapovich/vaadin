package com.vaadin.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "studentsgroup")
public class StudentsGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    @Column(unique = true)
    private String groupName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "studentsGroup")
    private Set<Student> students;

    public StudentsGroup() {
    }

    public StudentsGroup(@NotBlank @Size(max = 255) String groupName, Set<Student> students) {
        this.groupName = groupName;
        this.students = students;
    }
}
