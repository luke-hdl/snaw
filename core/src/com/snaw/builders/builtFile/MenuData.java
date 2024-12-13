package com.snaw.builders.builtFile;

import com.snaw.menu.Menu;

import java.util.List;
import java.util.Map;

public class MenuData {
    private List<String> renderingLevels;
    private Menu mainMenu;
    private Map<String, Menu> menus;

    public MenuData(List<String> renderingLevels, Map<String, Menu> menus, Menu mainMenu) {
        this.renderingLevels = renderingLevels;
        this.mainMenu = mainMenu;
        this.menus = menus;
    }

    public Menu getMainMenu() {
        return mainMenu;
    }

    public List<String> getRenderingLevels() {
        return renderingLevels;
    }

    public Map<String, Menu> getMenus() {
        return menus;
    }

}
