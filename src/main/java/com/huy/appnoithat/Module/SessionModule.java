package com.huy.appnoithat.Module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.huy.appnoithat.DataModel.Entity.Account;
import com.huy.appnoithat.DataModel.Token;
import com.huy.appnoithat.Handler.SessionExpiredHandler;
import com.huy.appnoithat.Session.UserSession;
import com.huy.appnoithat.Session.UserSessionManager;
import com.huy.appnoithat.Session.UserSessionManagerImpl;

public class SessionModule extends AbstractModule {
    @Provides
    SessionExpiredHandler sessionExpiredHandler(UserSessionManager userSessionManager) {
        return new SessionExpiredHandler(userSessionManager);
    }

    @Provides
    @Singleton
    UserSession userSession() {
        return new UserSession(Account.empty(), Token.empty());
    }

    @Provides
    @Singleton
    UserSessionManager userSessionManager(UserSession userSession) {
        return new UserSessionManagerImpl();
    }
}
