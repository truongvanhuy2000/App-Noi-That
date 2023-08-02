package com.huy.appnoithat.Entity;

import lombok.*;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PhongCachNoiThat {
    private int id;
    private String name;
    List<NoiThat> noiThatList;

    public void add(NoiThat noiThat) {
        noiThatList.add(noiThat);
    }

    @Override
    public String toString() {
        return "PhongCachNoiThat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", noiThatList=" + noiThatList +
                '}';
    }
}
