package com.huy.appnoithat.Controller.NewTab;

import com.huy.appnoithat.Controller.LuaChonNoiThat.LuaChonNoiThatController;
import com.huy.appnoithat.Scene.LuaChonNoiThat.LuaChonNoiThatScene;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TabContent {
    private Tab tab;
    private LuaChonNoiThatScene luaChonNoiThatScene;
    private LuaChonNoiThatController luaChonNoiThatController;
}
