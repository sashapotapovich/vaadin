package com.vaadin.backup;

import com.demo.app.controller.JoinTableController;
import com.demo.app.controller.TableViewController;
import com.demo.app.dto.TableTransfer;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
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
@Route(value = "joinTables", layout = MenuView.class)
public class JoinTablesView extends VerticalLayout implements RouterLayout {
    
    public static final String ID = "joinTables";

    @Autowired
    private TableViewController tableViewController;
    @Autowired
    private JoinTableController joinTableController;
    private Button fetch;
    private ComboBox<String> comboBoxFirstPannel;
    private ComboBox<String> comboBoxSecondPannel;
    private HorizontalLayout actionsLayout = new HorizontalLayout();
    private ListBox<String> listBoxFirstPannel = new ListBox<>();
    private ListBox<String> listBoxSecondPanel = new ListBox<>();
    private Button showData = new Button("Show Data");
    private Grid<TableTransfer> grid = new Grid<>(TableTransfer.class);
    private List<TableTransfer> tableTransfer = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        comboBoxFirstPannel = new ComboBox<>("Table First Panel", tableViewController.getTableNames());
        comboBoxSecondPannel = new ComboBox<>("Tables Second panel", tableViewController.getTableNames());
        fetch = new Button("Fetch Table Names", (event -> {
            tableViewController.fetchTableNames();
            comboBoxFirstPannel.setItems(tableViewController.getTableNames());
            comboBoxSecondPannel.setItems(tableViewController.getTableNames());
        }));
        comboBoxFirstPannel.addValueChangeListener(event -> {
            listBoxFirstPannel.setItems(joinTableController.fetchColumnNamesFromTable(comboBoxFirstPannel.getValue())); 
        });
        comboBoxSecondPannel.addValueChangeListener(event -> {
            listBoxSecondPanel.setItems(joinTableController.fetchColumnNamesFromTable(comboBoxSecondPannel.getValue()));
        });
        fetch.setIcon(VaadinIcon.PLUS.create());
        showData.addClickListener(event -> {
            String firstTableName = comboBoxFirstPannel.getValue();
            String secondTableName = comboBoxSecondPannel.getValue();
            String firstColumnName = listBoxFirstPannel.getValue();
            String secondColumnName = listBoxSecondPanel.getValue();
            tableTransfer = joinTableController.fetchDataFromJoinedTables(firstTableName, secondTableName,firstColumnName,
                                                                          secondColumnName);
            grid.setItems(tableTransfer);
        });
        HorizontalLayout lists = new HorizontalLayout();
        lists.add(listBoxFirstPannel, listBoxSecondPanel);
        VerticalLayout layout = new VerticalLayout();
        layout.add(showData, grid);
        actionsLayout.add(comboBoxFirstPannel, comboBoxSecondPannel, fetch);
        actionsLayout.setAlignItems(Alignment.BASELINE);
        add(actionsLayout, lists, layout);
    }
}
