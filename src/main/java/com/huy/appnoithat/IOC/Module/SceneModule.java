package com.huy.appnoithat.IOC.Module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.huy.appnoithat.Controller.DatabaseModify.*;
import com.huy.appnoithat.Controller.FileNoiThatExplorer.FileNoiThatExplorerController;
import com.huy.appnoithat.Controller.GlobalSettingController;
import com.huy.appnoithat.Controller.HomeController;
import com.huy.appnoithat.Controller.LoginController;
import com.huy.appnoithat.Controller.LuaChonNoiThat.LuaChonNoiThatController;
import com.huy.appnoithat.Controller.NewTab.NewTabController;
import com.huy.appnoithat.Controller.Register.RegisterController;
import com.huy.appnoithat.Controller.UserDetailController;
import com.huy.appnoithat.Controller.UserManagement.UsersManagementController;
import com.huy.appnoithat.Scene.DatabaseModify.*;
import com.huy.appnoithat.Scene.GlobalSettingScene;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Scene.Login.LoginScene;
import com.huy.appnoithat.Scene.Login.QRScene;
import com.huy.appnoithat.Scene.Login.RegisterScene;
import com.huy.appnoithat.Scene.LuaChonNoiThat.FileNoiThatExplorerScene;
import com.huy.appnoithat.Scene.LuaChonNoiThat.LuaChonNoiThatScene;
import com.huy.appnoithat.Scene.LuaChonNoiThat.NewTabScene;
import com.huy.appnoithat.Scene.UseManagement.ListAccountWaitToApproveScene;
import com.huy.appnoithat.Scene.UseManagement.UserManagementAddAccountScene;
import com.huy.appnoithat.Scene.UseManagement.UserManagementEditorScene;
import com.huy.appnoithat.Scene.UseManagement.UserManagementScene;
import com.huy.appnoithat.Scene.UserDetailScene;

public class SceneModule extends AbstractModule {
    @Override
    protected void configure() {
        super.configure();
        install(new DatabaseModifyModule());
        install(new LuaChonNoiThatModule());
        install(new LoginModule());
        install(new HomeModule());
        install(new UserManagementModule());
    }

    private static class UserManagementModule extends AbstractModule {
        @Provides
        UserManagementScene userManagementScene(UsersManagementController usersManagementController) {
            return new UserManagementScene(usersManagementController);
        }

        @Provides
        UserManagementEditorScene userManagementEditorScene() {
            return new UserManagementEditorScene();
        }

        @Provides
        UserManagementAddAccountScene userManagementAddAccountScene() {
            return new UserManagementAddAccountScene();
        }

        @Provides
        ListAccountWaitToApproveScene listAccountWaitToApproveScene() {
            return new ListAccountWaitToApproveScene();
        }
    }

    private static class LoginModule extends AbstractModule {
        @Provides
        LoginScene loginScene(LoginController loginController) {
            return new LoginScene(loginController);
        }

        @Provides
        QRScene qrScene() {
            return new QRScene();
        }

        @Provides
        RegisterScene registerScene(RegisterController registerController) {
            return new RegisterScene(registerController);
        }
    }

    private static class HomeModule extends AbstractModule {
        @Provides
        GlobalSettingScene globalSettingScene(GlobalSettingController controller) {
            return new GlobalSettingScene(controller);
        }

        @Provides
//        @Singleton
        HomeScene homeScene(HomeController homeController) {
            return new HomeScene(homeController);
        }

        @Provides
        UserDetailScene userDetailScene(UserDetailController userDetailController) {
            return new UserDetailScene(userDetailController);
        }
    }

    private static class DatabaseModifyModule extends AbstractModule {
        @Provides
        ChangeProductSpecificationScene changeProductSpecificationScene(ChangeProductSpecificationController controller) {
            return new ChangeProductSpecificationScene(controller);
        }

        @Provides
        DatabaseModifyHangMucScene databaseModifyHangMucScene(DatabaseModifyHangMucController controller) {
            return new DatabaseModifyHangMucScene(controller);
        }

        @Provides
        DatabaseModifyNoiThatScene databaseModifyNoiThatScene(DatabaseModifyNoiThatController controller) {
            return new DatabaseModifyNoiThatScene(controller);
        }

        @Provides
        DatabaseModifyPhongCachScene databaseModifyPhongCachScene(DatabaseModifyPhongCachController controller) {
            return new DatabaseModifyPhongCachScene(controller);
        }

        @Provides
        DatabaseModifyVatLieuScene databaseModifyVatLieuScene(DatabaseModifyVatLieuController controller) {
            return new DatabaseModifyVatLieuScene(controller);
        }
    }

    private static class LuaChonNoiThatModule extends AbstractModule {
        @Provides
//        @Singleton
        FileNoiThatExplorerScene fileNoiThatExplorerScene(FileNoiThatExplorerController controller) {
            return new FileNoiThatExplorerScene(controller);
        }

        @Provides
        LuaChonNoiThatScene luaChonNoiThatScene(LuaChonNoiThatController luaChonNoiThatController) {
            return new LuaChonNoiThatScene(luaChonNoiThatController);
        }

        @Provides
        NewTabScene newTabScene(NewTabController newTabController) {
            return new NewTabScene(newTabController);
        }
    }
}
