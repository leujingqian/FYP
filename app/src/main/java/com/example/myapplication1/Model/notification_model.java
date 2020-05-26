package com.example.myapplication1.Model;

public class notification_model {
    private boolean isRead;
    private String _id;
    private String recipient_id;
    private String type;
    private String content;
    private String time_stamp;
    private int __v;
    private Sender sender_id;

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getRecipient_id() {
        return recipient_id;
    }

    public void setRecipient_id(String recipient_id) {
        this.recipient_id = recipient_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public Sender getSender_id() {
        return sender_id;
    }

    public void setSender_id(Sender sender_id) {
        this.sender_id = sender_id;
    }

    public notification_model(String content, String time_stamp) {
        this.content = content;
        this.time_stamp = time_stamp;
    }
}


