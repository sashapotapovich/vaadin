package com.vaadin.ui;

import com.vaadin.entity.CurrentUser;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UIScope
@SpringComponent
@Route(value = "welcome", layout = MenuView.class)
public class WelcomeView extends VerticalLayout implements RouterLayout {

    public static final String ID = "welcome";
    
    private CurrentUser currentUser;
    private VerticalLayout verticalLayout;
    private H2 h2;
    
    public WelcomeView(CurrentUser currentUser){
        this.currentUser = currentUser;
    }
    
    @PostConstruct
    public void init(){
        verticalLayout = new VerticalLayout();
        h2 = new H2("Welcome back " + currentUser.getUser().getFirstName() + " " + currentUser.getUser().getLastName() + "!");
        verticalLayout.add(h2);
        verticalLayout.setAlignItems(Alignment.CENTER);
        add(verticalLayout);
    }
}
