package com.huy.appnoithat.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PhongCachNoiThat {
    private int id;
    private String name;
    List<NoiThat> noiThatList;

    public void add(NoiThat noiThat) {
        noiThatList.add(noiThat);
    }
}
