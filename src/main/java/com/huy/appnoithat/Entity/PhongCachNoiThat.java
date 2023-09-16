package com.huy.appnoithat.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.appnoithat.Entity.Common.CommonItemInterface;
import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PhongCachNoiThat implements CommonItemInterface {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("noiThat")
    List<NoiThat> noiThatList;
    public void add(NoiThat noiThat) {
        noiThatList.add(noiThat);
    }
    @Override
    public String toString() {
        return this.name;
    }
}
