package com.quantium.web.bean.social;

import com.quantium.web.dao.annotation.Index;
import com.quantium.web.dao.annotation.Row;
import com.quantium.web.dao.annotation.Table;

/**
 * Created by FREEMAN on 23.10.14.
 */
@Table("user_{userId}_messages")
public class Message {
    @Index
    @Row(name = "messageId", unsigned = true)
    private int messageId;

    public int getMessageId() {
        return messageId;
    }
    public Message setMessageId(int messageId) {
        this.messageId = messageId;
        return this;
    }
}
