package io.wumf.wumf.realmObject;

/**
 * Created by max on 31.03.16.
 */
public enum EventType {
    ADD,
    REMOVE,
    UPDATE;

    public static int toInt(EventType eventType) {
        return eventType.ordinal();
    }

    public static EventType fromInt(int value) {
        return values()[value];
    }

}
