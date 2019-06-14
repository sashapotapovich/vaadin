package com.vaadin.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UIScope
@SpringComponent
@Route(value = "students", layout = MenuView.class)
public class StudentsView extends VerticalLayout implements RouterLayout {

    public static final String ID = "students";

    private StudentRepository studentRepository;
    /*private ComboBox<String> comboBox;
    private HorizontalLayout layout = new HorizontalLayout();
    private Button fetch;
    private Button showData = new Button("Show Data");*/
    private Grid<Student> grid;

    public StudentsView(StudentRepository repository) {
        this.studentRepository = repository;
    }

    @PostConstruct
    public void init() {
        /*fetch = new Button("Fetch Table Names");
        fetch.setIcon(VaadinIcon.PLUS.create());
        showData.addClickListener(event -> {
            grid.setItems(studentRepository.findAll());
        });*/
        grid = new Grid<>(Student.class, true);
        
        /*layout.add(showData);
        layout.setAlignItems(Alignment.BASELINE);
        add(layout, grid);*/
        add(grid);
        grid.setItems(studentRepository.findAll());
    }

}
