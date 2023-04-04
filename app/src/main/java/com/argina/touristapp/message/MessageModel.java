package com.argina.touristapp.message;

public class MessageModel {

    private int id;
    private String clientMob,messageBody;

    public MessageModel(int id, String clientMob, String messageBody) {
        this.id = id;
        this.clientMob = clientMob;
        this.messageBody = messageBody;
    }


    public int getId() {
        return id;
    }

    public String getClientMob() {
        return clientMob;
    }

    public String getMessageBody() {
        return messageBody;
    }
}
