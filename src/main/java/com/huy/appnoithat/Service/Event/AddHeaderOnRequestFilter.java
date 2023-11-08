//package com.huy.appnoithat.Service.Event;
//
//import com.huy.appnoithat.Service.SessionService.UserSessionService;
//import jakarta.ws.rs.client.ClientRequestContext;
//import jakarta.ws.rs.client.ClientRequestFilter;
//
//public class AddHeaderOnRequestFilter implements ClientRequestFilter {
//    @Override
//    public void filter(ClientRequestContext requestContext) {
//        UserSessionService userSessionService = new UserSessionService();
//        requestContext.getHeaders().add("Authorization", "Bearer " + userSessionService.getToken());
//        // Add more headers as needed
//    }
//}
