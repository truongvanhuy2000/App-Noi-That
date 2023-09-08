package com.huy.appnoithat.Service.SessionService;

import com.huy.appnoithat.Controller.HomeController;
import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;
import com.huy.appnoithat.Session.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserSessionService {
    final static Logger LOGGER = LogManager.getLogger(UserSessionService.class);
    WebClientService webClientService;
    public UserSessionService() {
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
    }
    public boolean isLogin() {
        if (!isSessionValid()) {
            return false;
        }
        return true;
    }
    public void cleanUserSession() {
        UserSession session = UserSession.getInstance();
        session.setAccount(null);
        session.setJwtToken(null);
    }
    public void setSession(Account account, String jwtToken) {
        UserSession session = UserSession.getInstance();
        session.setAccount(account);
        session.setJwtToken(jwtToken);
    }
    public void setToken(String jwtToken) {
        UserSession session = UserSession.getInstance();
        session.setJwtToken(jwtToken);
    }
    public void setLoginAccount(Account account) {
        UserSession session = UserSession.getInstance();
        session.setAccount(account);
    }
    public UserSession getSession() {
        if (!isLogin()) {
            LOGGER.error("User is not login");
            throw new RuntimeException("User is not login");
        }
        return UserSession.getInstance();
    }
    public boolean isSessionValid() {
        if (UserSession.getInstance().getJwtToken() == null) {
            loadSessionFromDisk();
        }
        String response = webClientService.authorizedHttpGetJson("/api/index", UserSession.getInstance().getJwtToken());
        return response != null;
    }
    // Haven't implemented yet
    public void loadSessionFromDisk() {
        UserSession.getInstance().setJwtToken(":))");
    }
}
