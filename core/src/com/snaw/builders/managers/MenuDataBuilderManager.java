package com.snaw.builders.managers;

import com.snaw.builders.builtFile.MenuData;
import com.snaw.builders.menu.MenuBuilder;
import com.snaw.menu.Menu;
import com.snaw.snawfile.SnawTagElement;
import com.snaw.snawfile.SnawTagset;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MenuDataBuilderManager extends BuilderManager<MenuData> {

    @Override
    public MenuData build(SnawTagset tagset) {
        List<String> renderingLevels = new LinkedList<>();
        for (SnawTagElement renderingLevel : tagset.getOneOffTagset("RENDERING LEVELS").getElements("LEVEL")) {
            if (renderingLevel.getValue().contains("{")) {
                renderingLevels.add(renderingLevel.getValue().substring(0, renderingLevel.getValue().indexOf('{')));
                if (renderingLevel.getValue().endsWith("{Default}")) {
                    defaultRenderingLevel = renderingLevel.getValue().substring(0, renderingLevel.getValue().indexOf('{'));
                }
            } else {
                renderingLevels.add(renderingLevel.getValue());
            }
        }

        Map<String, Menu> menuMap = new HashMap<>();
        for (SnawTagset menuTagset : tagset.getOneOffTagset("MENUS").getChildren("MENU")) {
            Menu menu = new MenuBuilder(this).build(menuTagset);
            menuMap.put(menu.getId(), menu);
        }

        return new MenuData(renderingLevels, menuMap, mainMenu);
    }
}
