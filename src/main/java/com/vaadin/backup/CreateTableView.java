package com.vaadin.backup;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.PostConstruct;
import javax.swing.table.TableModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@UIScope
@Route(value = "createTable", layout = MenuView.class)
public class CreateTableView extends VerticalLayout implements RouterLayout {

    public static final String ID = "createTable";

    @Autowired
    private TableCreationEditor tableCreationEditor;
    @Autowired
    private TableController tableController;
    private TextField tableName = new TextField();
    private Grid<ColumnDefinition> grid = new Grid<>(ColumnDefinition.class, true);
    private Binder<ColumnDefinition> binder = new Binder<>(ColumnDefinition.class);
    private Button addNewBtn = new Button();
    private Button create = new Button("Create Table", VaadinIcon.DATABASE.create());
    private Button delete = new Button();
    @Autowired
    private ColumnDefinitionsHolder columnDefinitionsHolder;
    private TableModel tableModel = new TableModel("", new ArrayList<ColumnDefinition>());
    private Checkbox headerCheckbox = new Checkbox();
    private HorizontalLayout actions = new HorizontalLayout();
    private Label notificationText = new Label();
    private Button notificationButton = new Button("Ok", VaadinIcon.BELL.create());
    private VerticalLayout notificationLayout = new VerticalLayout(notificationText, notificationButton);
    private Notification notification = new Notification(notificationLayout);


    @PostConstruct
    public void init() {
        addNewBtn.setText("Add Column");
        addNewBtn.setIcon(VaadinIcon.PLUS.create());
        delete.setText("Delete");
        delete.setIcon(VaadinIcon.TRASH.create());
        delete.addClickListener(e -> delete());
        delete.getElement().getThemeList().add("error");

        headerCheckbox.setValue(false);

        notification.setPosition(Notification.Position.MIDDLE);
        notificationButton.addClickListener(event -> notification.close());

        grid.setItems(columnDefinitionsHolder.getTasks());

        grid.addComponentColumn((ColumnDefinition columnDefinition) -> {
            Checkbox inlineCheckbox = new Checkbox(columnDefinition.isCheckbox());
            binder.readBean(columnDefinition);
            binder.forMemberField(inlineCheckbox).bind("checkbox");
            headerCheckbox.addValueChangeListener(e -> {
                inlineCheckbox.setValue(headerCheckbox.getValue());
            });
            inlineCheckbox.addValueChangeListener(event -> {
                try {
                    binder.writeBean(columnDefinition);
                    columnDefinition.setCheckbox(inlineCheckbox.getValue());
                } catch (ValidationException e) {
                    e.printStackTrace();
                }
            });
            return inlineCheckbox;
        }).setResizable(true).setId("CH2");
        grid.removeColumnByKey("checkbox");

        grid.getHeaderRows().get(0).getCells().forEach(headerCell -> headerCell.setComponent(headerCheckbox));
        String[] headers = { "Column Name", "Data Type" };
        AtomicInteger i = new AtomicInteger();
        grid.getColumns().forEach(columnDefinitionColumn -> {
            if (i.get() < 2)
                columnDefinitionColumn.setHeader(headers[i.getAndIncrement()]);
        });
        grid.setHeightByRows(true);
        grid.asSingleSelect().addValueChangeListener(task -> tableCreationEditor.editColumn(task.getValue()));

        addNewBtn.addClickListener(e -> tableCreationEditor.editColumn(ColumnDefinition.builder()
                                                                                       .checkbox(false).columnName("")
                                                                                       .build()));

        create.addClickListener(event -> {
            if (tableName.getValue() != null && !tableName.getValue().isEmpty()) {
                if (!columnDefinitionsHolder.getTasks().isEmpty()) {
                    tableModel.setTableName(tableName.getValue());
                    tableModel.setColumns(columnDefinitionsHolder.getTasks());
                    try {
                        tableController.createTable(tableModel);
                        notificationText.setText("Table " + tableName.getValue() + " was successfully created");
                    } catch (SQLException e) {
                        log.error(e.getLocalizedMessage());
                        notificationText.setText(e.getLocalizedMessage());
                    }
                } else {
                    notificationText.setText("Add columns to the table");
                }
            } else {
                notificationText.setText("Table Name could not be empty!");
            }
            notification.open();
        });

        tableName.setPlaceholder("Table Name");

        actions.add(tableName, addNewBtn, delete, create);
        add(actions, grid, tableCreationEditor);
        tableCreationEditor.setChangeHandler(() -> {
            tableCreationEditor.setVisible(false);
            listTasks();
        });
    }

    void listTasks() {
        grid.setItems(columnDefinitionsHolder.getTasks());
    }

    void delete() {
        columnDefinitionsHolder.getTasks().removeIf(task -> task.isCheckbox());
        headerCheckbox.setValue(false);
        listTasks();
    }

}
