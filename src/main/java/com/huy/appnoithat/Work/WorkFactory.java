package com.huy.appnoithat.Work;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class WorkFactory {
    private static final Queue<OpenFileWork> OPEN_FILE_WORKS = new LinkedList<>();
    private static final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private static ScheduledFuture currentScheduledFuture;

    public static synchronized void addNewOpenFileWork(OpenFileWork openFileWork) {
        OPEN_FILE_WORKS.add(openFileWork);
    }

    private static OpenFileWork getWork() {
        return OPEN_FILE_WORKS.poll();
    }

    public static synchronized void startGetWork(Consumer<OpenFileWork> workConsumer) {
        if (currentScheduledFuture != null) {
            currentScheduledFuture.cancel(true);
        }
        currentScheduledFuture = scheduledExecutorService.scheduleAtFixedRate(() -> {
            OpenFileWork openFileWork = WorkFactory.getWork();
            if (openFileWork != null) {
                workConsumer.accept(openFileWork);
            }
        }, 0, 2, TimeUnit.SECONDS);
    }
}
