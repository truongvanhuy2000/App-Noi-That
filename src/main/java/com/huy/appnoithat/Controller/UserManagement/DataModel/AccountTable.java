package com.huy.appnoithat.Controller.UserManagement.DataModel;

import javafx.scene.image.ImageView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountTable {
    private int id;
    private String username;
    private String password;
    private boolean active;
    private ImageView activeImage;
    private String expiredDate;
}
