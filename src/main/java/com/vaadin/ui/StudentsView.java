package com.vaadin.ui;

import com.vaadin.entity.Student;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.repository.StudentRepository;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;

@Slf4j
@UIScope
@SpringComponent
@Route(value = "students", layout = MenuView.class)
@Secured("ROLE_ADMIN")
public class StudentsView extends VerticalLayout implements RouterLayout {
    public static final String ID = "students";
    private static TextField groupFilter = new TextField("Group Filter", "");
    private StudentRepository studentRepository;
    private Button clearFilters = new Button("Show All Students");
    private HorizontalLayout filters = new HorizontalLayout(clearFilters);
    private Grid<Student> grid;

    public StudentsView(StudentRepository repository) {
        this.studentRepository = repository;
    }

    @PostConstruct
    public void init() {
        groupFilter.addValueChangeListener(listener -> {
            if (!groupFilter.getValue().isEmpty()) {
                clearFilters.setVisible(true);
            }
            List<Student> students = studentRepository.findAllByStudentsGroup_GroupNameLike(groupFilter.getValue());
            grid.setItems(students);
        });
        filters.setAlignItems(Alignment.BASELINE);
        clearFilters.setVisible(false);
        clearFilters.addClickListener(action -> {
            groupFilter.setValue("");
            grid.setItems(studentRepository.findAll());
            clearFilters.setVisible(false);
        });
        grid = new Grid<>(Student.class, false);
        grid.addColumn(Student::getId).setHeader("ID").setFlexGrow(0).setSortable(true);
        grid.addColumn(Student::getFirstName).setHeader("First Name").setSortable(true);
        grid.addColumn(Student::getLastName).setHeader("Last Name").setSortable(true);
        grid.addColumn(student -> student.getStudentsGroup().getGroupName()).setHeader("Group").setSortable(true);
        add(filters, grid);
        grid.setItems(studentRepository.findAll());
    }

    public static void setFilter(String filter) {
        groupFilter.setValue(filter);
    }

}
