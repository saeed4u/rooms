package com.ut.iot.rooms.data;

import java.lang.System;

/**
 * Created by Saeed on 2019-10-19.
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J \u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\b0\nJ\u0010\u0010\u000b\u001a\u00020\f2\b\u0010\t\u001a\u0004\u0018\u00010\u0001R\u001c\u0010\u0003\u001a\u0010\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\u00010\u00010\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/ut/iot/rooms/data/EventBus;", "", "()V", "publisher", "Lio/reactivex/subjects/PublishSubject;", "kotlin.jvm.PlatformType", "listen", "Lio/reactivex/Observable;", "T", "event", "Ljava/lang/Class;", "publish", "", "app_debug"})
public final class EventBus {
    private static final io.reactivex.subjects.PublishSubject<java.lang.Object> publisher = null;
    public static final com.ut.iot.rooms.data.EventBus INSTANCE = null;
    
    @org.jetbrains.annotations.NotNull()
    public final <T extends java.lang.Object>io.reactivex.Observable<T> listen(@org.jetbrains.annotations.NotNull()
    java.lang.Class<T> event) {
        return null;
    }
    
    public final void publish(@org.jetbrains.annotations.Nullable()
    java.lang.Object event) {
    }
    
    private EventBus() {
        super();
    }
}