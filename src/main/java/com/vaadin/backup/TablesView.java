package com.vaadin.backup;

import com.demo.app.controller.TableViewController;
import com.demo.app.dto.TableTransfer;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@UIScope
@Route(value = "viewTable", layout = MenuView.class)
public class TablesView extends VerticalLayout implements RouterLayout {

    public static final String ID = "viewTable";

    @Autowired
    private TableViewController tableViewController;
    private ComboBox<String> comboBox;
    private HorizontalLayout layout = new HorizontalLayout();
    private Button fetch;
    private Button showData = new Button("Show Data");
    private Grid<TableTransfer> grid = new Grid<>(TableTransfer.class);
    private List<TableTransfer> tableTransfer = new ArrayList<>();

    @PostConstruct
    public void init() {
        comboBox = new ComboBox<>("Tables", tableViewController.getTableNames());
        fetch = new Button("Fetch Table Names", (event -> {
            comboBox.setItems(tableViewController.fetchTableNames());
        }));
        fetch.setIcon(VaadinIcon.PLUS.create());
        showData.addClickListener(event -> {
            String tableName = comboBox.getValue();
            tableTransfer = tableViewController.fetchDataForTable(tableName);
            grid.setItems(tableTransfer);
        });
        layout.add(comboBox, fetch, showData);
        layout.setAlignItems(Alignment.BASELINE);
        add(layout, grid);
    }

}
