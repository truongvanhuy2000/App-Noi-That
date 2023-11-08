package com.huy.appnoithat.Service.Event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.appnoithat.Configuration.Config;
import com.huy.appnoithat.DataModel.Event.NoiThatUpdate;
import com.huy.appnoithat.Service.LuaChonNoiThat.CacheNoiThatRequestService;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.LinkOption;
import java.util.function.Consumer;

public class DBUpdateEventService {
    final static Logger LOGGER = LogManager.getLogger(LuaChonNoiThatService.class);
    private static final String PATH = "/api/bangnoithat/event/DBModification";
    private static DBUpdateEventService instance;
    private CacheNoiThatRequestService cacheNoiThatRequestService;

    ObjectMapper objectMapper = new ObjectMapper();
    public static synchronized DBUpdateEventService getInstance() {
        if (instance == null) {
            instance = new DBUpdateEventService();
        }
        return instance;
    }
    private DBUpdateEventService() {
        cacheNoiThatRequestService = CacheNoiThatRequestService.getInstance();
    }
    public void start() {
//        new Thread(() -> {
//            AddHeaderOnRequestFilter addHeaderOnRequestFilter = new AddHeaderOnRequestFilter();
//            Client client = ClientBuilder.newBuilder().register(addHeaderOnRequestFilter).build();
//            WebTarget target = client.target(Config.WEB_CLIENT.BASE_URL + PATH);
//            try (SseEventSource eventSource = SseEventSource.target(target).build()) {
//                eventSource.register(onEvent, onError, onComplete);
//                eventSource.open();
//            } catch (Exception e) {
//                LOGGER.error(e.getMessage());
//                throw new RuntimeException();
//            }
//            client.close();
//        }).start();
    }
//    private final Consumer<InboundSseEvent> onEvent = (inboundSseEvent) -> {
//        String data = inboundSseEvent.readData();
//        System.out.println(data);
//        try {
//            NoiThatUpdate noiThatUpdate = objectMapper.readValue(data, NoiThatUpdate.class);
//            cacheNoiThatRequestService.clearCache();
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    };
//
//    //Error
//    private final Consumer<Throwable> onError = (throwable) -> {
//        throw new RuntimeException(throwable);
//    };
//
//    //Connection close and there is nothing to receive
//    private final Runnable onComplete = () -> {
//        System.out.println("Done!");
//    };
}
