package com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoiThatItem {
    private int id;
    private String name;
}
