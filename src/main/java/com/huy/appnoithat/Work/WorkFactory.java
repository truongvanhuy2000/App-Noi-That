package com.huy.appnoithat.Work;

import java.util.LinkedList;
import java.util.Queue;

public class WorkFactory {
    private static final Queue<OpenFileWork> OPEN_FILE_WORKS = new LinkedList<>();
    public static synchronized void addNewOpenFileWork(OpenFileWork openFileWork) {
        OPEN_FILE_WORKS.add(openFileWork);
    }
    public static synchronized OpenFileWork getWork() {
        return OPEN_FILE_WORKS.poll();
    }
}
