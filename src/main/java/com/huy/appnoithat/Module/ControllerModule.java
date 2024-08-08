package com.huy.appnoithat.Module;

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
import com.huy.appnoithat.Service.DatabaseModify.*;
import com.huy.appnoithat.Service.FileNoiThatExplorer.FileNoiThatExplorerService;
import com.huy.appnoithat.Service.Login.LoginService;
import com.huy.appnoithat.Service.LuaChonNoiThat.NoiThatFileService;
import com.huy.appnoithat.Service.PersistenceStorage.StorageService;
import com.huy.appnoithat.Service.Register.RegisterService;
import com.huy.appnoithat.Service.RestService.PricingModelRestService;
import com.huy.appnoithat.Service.UserDetail.UserDetailService;
import com.huy.appnoithat.Service.UsersManagement.UsersManagementService;
import com.huy.appnoithat.Session.UserSessionManager;

public class ControllerModule extends AbstractModule {
    @Override
    protected void configure() {
        super.configure();
        install(new DatabaseModifyModule());
        install(new LuaChonNoiThatModule());
        install(new LoginModule());
        install(new HomeModule());
    }

    @Provides
    UsersManagementController usersManagementController(UsersManagementService usersManagementService) {
        return new UsersManagementController(usersManagementService);
    }

    private static class DatabaseModifyModule extends AbstractModule {
        @Provides
        @Singleton
        DatabaseModifyHangMucController databaseModifyHangMucController(DatabaseModifyHangMucService databaseModifyHangMucService,
                                                                        DatabaseModifyVatlieuService databaseModifyVatlieuService) {
            return new DatabaseModifyHangMucController(databaseModifyHangMucService, databaseModifyVatlieuService);
        }

        @Provides
        @Singleton
        DatabaseModifyNoiThatController databaseModifyNoiThatController(DatabaseModifyHangMucService databaseModifyHangMucService,
                                                                        DatabaseModifyNoiThatService databaseModifyNoiThatService) {
            return new DatabaseModifyNoiThatController(databaseModifyHangMucService, databaseModifyNoiThatService);
        }

        @Provides
        @Singleton
        DatabaseModifyPhongCachController databaseModifyPhongCachController(DatabaseModifyPhongCachService databaseModifyPhongCachService,
                                                                            DatabaseModifyNoiThatService databaseModifyNoiThatService) {
            return new DatabaseModifyPhongCachController(databaseModifyPhongCachService, databaseModifyNoiThatService);
        }

        @Provides
        @Singleton
        DatabaseModifyVatLieuController databaseModifyVatLieuController(DatabaseModifyVatlieuService databaseModifyVatlieuService,
                                                                        DatabaseModifyThongSoService databaseModifyThongSoService) {
            return new DatabaseModifyVatLieuController(databaseModifyVatlieuService, databaseModifyThongSoService);
        }

        @Provides
        @Singleton
        ChangeProductSpecificationController changeProductSpecificationController(DatabaseModifyThongSoService databaseModifyThongSoService) {
            return new ChangeProductSpecificationController(databaseModifyThongSoService);
        }
    }

    private static class LuaChonNoiThatModule extends AbstractModule {
        @Provides
        FileNoiThatExplorerController fileNoiThatExplorerController(FileNoiThatExplorerService fileNoiThatExplorerService) {
            return new FileNoiThatExplorerController(fileNoiThatExplorerService);
        }

        @Provides
        LuaChonNoiThatController luaChonNoiThatController(StorageService persistenceStorageService) {
            return new LuaChonNoiThatController(persistenceStorageService);
        }

        @Provides
        NewTabController newTabController(StorageService persistenceStorageService, NoiThatFileService noiThatFileService) {
            return new NewTabController(persistenceStorageService, noiThatFileService);
        }
    }

    private static class LoginModule extends AbstractModule {
        @Provides
        RegisterController registerController(RegisterService registerService, PricingModelRestService pricingModelRestService) {
            return new RegisterController(registerService, pricingModelRestService);
        }

        @Provides
        LoginController loginController(LoginService loginService) {
            return new LoginController(loginService);
        }
    }

    private static class HomeModule extends AbstractModule {
        @Provides
        GlobalSettingController globalSettingController(PricingModelRestService pricingModelRestService) {
            return new GlobalSettingController(pricingModelRestService);
        }

        @Provides
        UserDetailController userDetailController(UserDetailService userDetailService) {
            return new UserDetailController(userDetailService);
        }


        @Provides
        HomeController homeController(UserSessionManager sessionService, LoginService loginService) {
            return new HomeController(sessionService, loginService);
        }
    }
}
