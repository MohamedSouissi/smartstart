/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.util;

import com.smartstart.entities.Message;
import java.io.StringReader;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author diabl
 */
public class MessageDecoder implements Decoder.Text<Message> {


    public void init(final EndpointConfig config) {
    }


    public void destroy() {
    }


    public Message decode(final String textMessage) throws DecodeException {
        Message message = new Message();
        JsonObject jsonObject = Json.createReader(new StringReader(textMessage)).readObject();
        message.setContent(jsonObject.getString("message"));
        message.getMessage_from().setUsername(jsonObject.getString("sender"));
        message.setDate_message(new Date());
        return message;
    }


    public boolean willDecode(final String s) {
        return true;
    }

}

