package com.vaadin.ui;

import com.vaadin.entity.StudentsGroup;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.repository.StudentGroupRepository;
import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UIScope
@SpringComponent
@Route(value = "group", layout = MenuView.class)
public class GroupView extends VerticalLayout implements RouterLayout {

    public static final String ID = "group";

    private StudentGroupRepository studentGroupRepository;

    private Grid<StudentsGroup> grid;

    public GroupView(StudentGroupRepository studentGroupRepository) {
        this.studentGroupRepository = studentGroupRepository;
    }

    @PostConstruct
    public void init() {
        grid = new Grid<>(StudentsGroup.class, false);
        grid.addColumn(StudentsGroup::getId).setHeader("ID").setFlexGrow(0);
        grid.addColumn(StudentsGroup::getGroupName).setHeader("Group Name");
        add(grid);
        grid.addItemClickListener(listener -> {
            String groupName = listener.getItem().getGroupName();
            StudentsView.setFilter(groupName);
            UI.getCurrent().navigate(StudentsView.class);
        });
        grid.setItems(studentGroupRepository.findAll());
    }
    
}
