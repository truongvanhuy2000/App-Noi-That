package com.huy.appnoithat.Event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

import java.io.Serial;

public class RestEvent extends Event {
    @Serial
    private static final long serialVersionUID = 2011123457L;
    public static final EventType<RestEvent> REST_START = new EventType<>(Event.ANY, "REST_START");
    public static final EventType<RestEvent> REST_STOP = new EventType<>(Event.ANY, "REST_STOP");
    public RestEvent(Object o, EventTarget eventTarget, EventType<? extends Event> eventType) {
        super(o, eventTarget, eventType);
    }

    public RestEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
