package com.huy.appnoithat.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PhongCachNoiThat {
    private int id;
    private String name;
    List<NoiThat> noiThatList;
}
