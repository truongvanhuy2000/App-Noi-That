package com.huy.appnoithat.Service.RestService;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.DataModel.Entity.Account;
import com.huy.appnoithat.DataModel.Entity.AccountInformation;
import com.huy.appnoithat.DataModel.Token;
import com.huy.appnoithat.Service.WebClient.JavaNetHttpClient;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.httpcache4j.uri.URIBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AccountRestService {
    final static Logger LOGGER = LogManager.getLogger(AccountRestService.class);
    private static AccountRestService instance;
    private final WebClientService webClientService;
    private static final String BASE_ENDPOINT = "/api";
    public static synchronized AccountRestService getInstance() {
        if (instance == null) {
            instance = new AccountRestService();
        }
        return instance;
    }

    public AccountRestService() {
        webClientService = new JavaNetHttpClient();
    }

    public Account getAccountInformation() {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("info");
        Optional<Account> response = this.webClientService.authorizedHttpGet(uriBuilder, Account.class);
        return response.orElse(null);
    }

    public Account findByUsername(String username) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("accounts", "search")
                .addParameter("username", username);
        Optional<Account> response = this.webClientService.authorizedHttpGet(uriBuilder, Account.class);
        return response.orElse(null);
    }

    public Account findById(int id) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("accounts", String.valueOf(id));
        Optional<Account> response = this.webClientService.authorizedHttpGet(uriBuilder, Account.class);
        return response.orElse(null);
    }
    public void save(Account account) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("accounts");
        Optional<String> response = this.webClientService.authorizedHttpPost(uriBuilder, account, String.class);
        response.orElseThrow(() -> {
            LOGGER.error("Can't save account");
            return new RuntimeException("Can't save account");
        });
    }
    public String update(Account account) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("accounts", String.valueOf(account.getId()));
        Optional<String> response = this.webClientService.authorizedHttpPutJson(uriBuilder, account, String.class);
        return response.orElse(null);
    }
    public void deleteById(int id) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("accounts", String.valueOf(id));
        this.webClientService.authorizedHttpDeleteJson(uriBuilder, String.class);
    }
    public void activateAccount(int id) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("accounts", "activate", String.valueOf(id));
        this.webClientService.authorizedHttpPutJson(uriBuilder, "", String.class);
    }
    public void deactivateAccount(int id) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("accounts", "deactivate", String.valueOf(id));
        this.webClientService.authorizedHttpPutJson(uriBuilder, "", String.class);
    }
    public List<Account> findAllEnabledAccount() {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("accounts", "enabled");
        Optional<List<Account>> response = this.webClientService.authorizedHttpGet(uriBuilder, Account.class, List.class);
        return response.orElse(null);
    }
    public List<Account> findAllNotEnabledAccount() {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("accounts", "notEnabled");
        Optional<List<Account>> response = this.webClientService.authorizedHttpGet(uriBuilder, Account.class, List.class);
        return response.orElse(null);
    }
    public void enableAccount(int id) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("accounts", "enable", String.valueOf(id));
        this.webClientService.authorizedHttpPutJson(uriBuilder, "", String.class);
    }
    public String sessionCheck() {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("index");
        Optional<String> response = this.webClientService.authorizedHttpGet(uriBuilder, String.class);
        return response.orElse(null);
    }
    public Token login(String username, String password) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("login");
        Account account = Account.builder().username(username).password(password).build();
        Optional<Token> response = webClientService.unauthorizedHttpPostJson(uriBuilder, account, Token.class);
        return response.orElse(null);
    }
    public void register(Account account) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("register");
        Optional<String> response = this.webClientService.unauthorizedHttpPostJson(uriBuilder, account, String.class);
        if (response.isPresent()) {
            PopupUtils.throwSuccessNotification("Đăng ký thành công, vui lòng chờ Admin duyệt");
        } else {
            LOGGER.error("Can't register new account");
            PopupUtils.throwSuccessNotification("Đăng ký thất bại");
        }
    }
    public boolean isUsernameValid(String username) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("register", "usernameValidation")
                .addParameter("username", username);
        Optional<String> response = this.webClientService.unauthorizedHttpGetJson(uriBuilder, String.class);
        return response.isPresent();
    }
    public boolean changePassword(String oldPassword, String newPassword) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("oldPassword", oldPassword);
        requestBody.put("newPassword", newPassword);
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("changePassword");
        Optional<String> response = this.webClientService.authorizedHttpPutJson(uriBuilder, requestBody, String.class);
        return response.isPresent();
    }
    public boolean updateInformation(AccountInformation accountInformation) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("updateInfo");
        Optional<String> response = this.webClientService.authorizedHttpPutJson(uriBuilder, accountInformation, String.class);
        return response.isPresent();
    }
    

}
