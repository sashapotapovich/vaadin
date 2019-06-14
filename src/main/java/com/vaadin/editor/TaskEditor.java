package com.vaadin.editor;

import com.vaadin.entity.Task;
import com.vaadin.entity.TasksHolder;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
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

@UIScope
@Data
@Component
public class TaskEditor extends VerticalLayout implements KeyNotifier {

    private Checkbox checkbox = new Checkbox();
    private TextField taskItem = new TextField();
    private Button save = new Button();
    private Button delete = new Button();
    private HorizontalLayout actions = new HorizontalLayout();
    private Binder<Task> binder = new Binder<>(Task.class);
    private Task task;
    private ChangeHandler changeHandler;
    @Autowired
    private TasksHolder tasksHolder;

    @PostConstruct
    public void init() {
        actions.add(save, delete);
        add(taskItem, actions);
        taskItem.setLabel("To Do Item");
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
            task.setCheckbox(true);
            delete();
        });
        setVisible(false);
    }

    void delete() {
        tasksHolder.getTasks().removeIf(task -> task.isCheckbox());
        changeHandler.onChange();
    }

    void save() {
        Task taskByName = tasksHolder.getTaskByName(taskItem.getValue());
        if (taskByName == null) {
            tasksHolder.addTask(Task.builder()
                                    .checkbox(checkbox.getValue())
                                    .taskItem(taskItem.getValue())
                                    .build());
        }
        changeHandler.onChange();
    }

    public final void editTask(Task c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        task = c;
        binder.setBean(task);
        setVisible(true);
        taskItem.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

    public interface ChangeHandler {
        void onChange();
    }
}
