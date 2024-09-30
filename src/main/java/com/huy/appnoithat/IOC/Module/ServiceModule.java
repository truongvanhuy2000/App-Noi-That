package com.huy.appnoithat.IOC.Module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.huy.appnoithat.Service.DatabaseModify.*;
import com.huy.appnoithat.Service.FileNoiThatExplorer.FileNoiThatExplorerService;
import com.huy.appnoithat.Service.FileNoiThatExplorer.LocalFileNoiThatExplorerService;
import com.huy.appnoithat.Service.Login.LoginService;
import com.huy.appnoithat.Service.LuaChonNoiThat.CacheNoiThatRequestService;
import com.huy.appnoithat.Service.LuaChonNoiThat.CacheService;
import com.huy.appnoithat.Service.LuaChonNoiThat.FileExport.FileExportService;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import com.huy.appnoithat.Service.LuaChonNoiThat.NoiThatFileService;
import com.huy.appnoithat.Service.PersistenceStorage.PersistenceStorageService;
import com.huy.appnoithat.Service.PersistenceStorage.StorageService;
import com.huy.appnoithat.Service.Register.RegisterService;
import com.huy.appnoithat.Service.RestService.*;
import com.huy.appnoithat.Service.UserDetail.UserDetailService;
import com.huy.appnoithat.Service.UsersManagement.UsersManagementService;
import com.huy.appnoithat.Session.UserSessionManager;

public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        super.configure();
        install(new DatabaseModifyModule());
        install(new LoginModule());
        install(new LuaChonNoiThatModule());
    }

    @Provides
    UsersManagementService usersManagementService(AccountRestService accountRestService) {
        return new UsersManagementService(accountRestService);
    }

    @Provides
    StorageService persistenceStorageService(ObjectMapper objectMapper,
                                             LapBaoGiaRestService lapBaoGiaRestService) {
        PersistenceStorageService.CachedData cachedData = new PersistenceStorageService.CachedData();
        return new PersistenceStorageService(objectMapper, lapBaoGiaRestService, cachedData);
    }

    @Provides
    UserDetailService userDetailService(AccountRestService accountRestService) {
        return new UserDetailService(accountRestService);
    }

    private static class DatabaseModifyModule extends AbstractModule {

        @Provides
        DatabaseModifyHangMucService databaseModifyHangMucService(HangMucRestService hangMucRestService) {
            return new DatabaseModifyHangMucService(hangMucRestService);
        }

        @Provides
        DatabaseModifyVatlieuService databaseModifyVatlieuService(VatLieuRestService vatLieuRestService) {
            return new DatabaseModifyVatlieuService(vatLieuRestService);
        }

        @Provides
        DatabaseModifyNoiThatService databaseModifyNoiThatService(NoiThatRestService noiThatRestService) {
            return new DatabaseModifyNoiThatService(noiThatRestService);
        }

        @Provides
        DatabaseModifyPhongCachService databaseModifyPhongCachService(PhongCachRestService phongCachRestService,
                                                                      BangNoiThatRestService bangNoiThatRestService) {
            return new DatabaseModifyPhongCachService(phongCachRestService, bangNoiThatRestService);
        }

        @Provides
        DatabaseModifyThongSoService databaseModifyThongSoService(ThongSoRestService thongSoRestService) {
            return new DatabaseModifyThongSoService(thongSoRestService);
        }
    }

    private static class LoginModule extends AbstractModule {
        @Provides
        LoginService loginService(UserSessionManager userSessionManager, AccountRestService accountRestService) {
            return new LoginService(userSessionManager, accountRestService);
        }

        @Provides
        RegisterService registerService(AccountRestService accountRestService) {
            return new RegisterService(accountRestService);
        }
    }

    private static class LuaChonNoiThatModule extends AbstractModule {
        @Provides
        NoiThatFileService noiThatFileService(ObjectMapper mapper, FileNoiThatExplorerService fileNoiThatExplorerService) {
            return new NoiThatFileService(mapper, fileNoiThatExplorerService);
        }

        @Provides
        FileNoiThatExplorerService fileNoiThatExplorerService(StorageService persistenceStorageService) {
            return new LocalFileNoiThatExplorerService(persistenceStorageService);
        }

        @Provides
        LuaChonNoiThatService luaChonNoiThatService(
                FileExportService fileExportService, PhongCachRestService phongCachRestService,
                NoiThatRestService noiThatRestService, HangMucRestService hangMucRestService,
                VatLieuRestService vatLieuRestService, CacheNoiThatRequestService cacheNoiThatRequestService
        ) {
            return new LuaChonNoiThatService(fileExportService, phongCachRestService, noiThatRestService,
                    hangMucRestService, vatLieuRestService, cacheNoiThatRequestService);
        }

        @Provides
        @Singleton
        CacheService cacheNoiThatRequestService() {
            return new CacheNoiThatRequestService();
        }
    }

}
