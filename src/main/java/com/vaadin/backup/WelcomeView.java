package com.vaadin.backup;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UIScope
@Route(value = "welcome", layout = MenuView.class)
public class WelcomeView extends VerticalLayout implements RouterLayout {

    public static final String ID = "welcome";

    @PostConstruct
    public void init() {

    }
}
