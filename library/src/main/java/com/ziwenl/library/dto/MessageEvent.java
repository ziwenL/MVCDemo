package com.ziwenl.library.dto;

/**
 * Author : zhouyx
 * Date   : 2016/8/16
 * EventBus通信类
 */
public class MessageEvent {

    private int eventCode = -1;
    private Object data;

    public MessageEvent(int eventCode) {
        this(eventCode, null);
    }

    public MessageEvent(int eventCode, Object data) {
        this.eventCode = eventCode;
        this.data = data;
    }

    public int getEventCode() {
        return eventCode;
    }

    public Object getData() {
        return data;
    }

}
