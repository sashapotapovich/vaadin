package com.vaadin.backup;

import com.demo.app.dto.ColumnDefinition;
import com.demo.app.entity.ColumnDefinitionsHolder;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;
import javax.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ui.database.DatabaseTypes;

@UIScope
@Data
@Component
public class TableCreationEditor extends VerticalLayout implements KeyNotifier {

    private Checkbox checkbox = new Checkbox();
    private TextField columnName = new TextField();
    private ComboBox<DatabaseTypes> type = new ComboBox<>("Type");
    private TextField size = new TextField("Size");
    private Button save = new Button();
    private Button delete = new Button();
    private HorizontalLayout actions = new HorizontalLayout();
    private HorizontalLayout fields = new HorizontalLayout(columnName, type, size);
    private Binder<ColumnDefinition> binder = new Binder<>(ColumnDefinition.class);
    private ColumnDefinition column;
    private ChangeHandler changeHandler;
    @Autowired
    private ColumnDefinitionsHolder columnDefinitionsHolder;

    @PostConstruct
    public void init() {
        actions.add(save, delete);
        type.setItems(DatabaseTypes.values());
        fields.setAlignItems(Alignment.BASELINE);
        add(fields, actions);
        columnName.setLabel("Column Name");
        binder.bindInstanceFields(this);
        setSpacing(true);
        save.setText("Save");
        save.setIcon(VaadinIcon.CHECK.create());
        save.getElement().getThemeList().add("primary");
        delete.setText("Delete");
        delete.setIcon(VaadinIcon.TRASH.create());
        delete.getElement().getThemeList().add("error");
        checkbox.setValue(false);

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> {
            column.setCheckbox(true);
            delete();
        });
        setVisible(false);
    }

    private void delete() {
        columnDefinitionsHolder.getTasks().removeIf(task -> task.isCheckbox());
        changeHandler.onChange();
    }

    private void save() {
        ColumnDefinition taskByName = columnDefinitionsHolder.getTaskByName(columnName.getValue());
        if (taskByName == null) {
            String dataType = type.getValue().name();
            if (size.getValue() != null && !size.getValue().isEmpty()){
                dataType +=  "(" + size.getValue() + ")";
            }
            columnDefinitionsHolder.addTask(ColumnDefinition.builder()
                                                            .checkbox(checkbox.getValue())
                                                            .columnName(columnName.getValue())
                                                            .dataType(dataType)
                                                            .build());
        }
        changeHandler.onChange();
    }

    public final void editColumn(ColumnDefinition c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        column = c;
        binder.setBean(column);
        setVisible(true);
        columnName.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

    public interface ChangeHandler {
        void onChange();
    }
}
