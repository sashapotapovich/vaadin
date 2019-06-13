package com.vaadin.backup;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AbstractAppRouterLayout;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import ui.security.SecurityUtils;


@Theme(value = Lumo.class, variant = "dark")
@PWA(name = "App", shortName = "Hi there")
public class MenuView extends AbstractAppRouterLayout {


    public MenuView() {
    }

    @Override
    protected void configure(AppLayout appLayout, AppLayoutMenu menu) {

        if (SecurityUtils.isUserLoggedIn()) {
            if (SecurityUtils.isAccessGranted(CreateTableView.class))
                setMenuItem(menu, new AppLayoutMenuItem(VaadinIcon.TABLE.create(), "Create Table", CreateTableView.ID));
            
            setMenuItem(menu, new AppLayoutMenuItem(VaadinIcon.FILE_TABLE.create(), "View Tables", TablesView.ID));
            
            setMenuItem(menu, new AppLayoutMenuItem(VaadinIcon.FILE_TABLE.create(), "View Join Tables", JoinTablesView.ID));

            setMenuItem(menu, new AppLayoutMenuItem(VaadinIcon.ARROW_RIGHT.create(), "Logout", e ->
                    UI.getCurrent().getPage().executeJavaScript("location.assign('logout')")));
        }
        getElement().addEventListener("search-focus", e -> {
            appLayout.getElement().getClassList().add("hide-navbar");
        });

        getElement().addEventListener("search-blur", e -> {
            appLayout.getElement().getClassList().remove("hide-navbar");
        });
    }

    private void setMenuItem(AppLayoutMenu menu, AppLayoutMenuItem menuItem) {
        menuItem.getElement().setAttribute("theme", "icon-on-top");
        menu.addMenuItem(menuItem);
    }

}
