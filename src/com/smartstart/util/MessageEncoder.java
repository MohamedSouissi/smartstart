/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.util;

import com.smartstart.entities.Message;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author diabl
 */
public class MessageEncoder implements Encoder.Text<Message> {


    public void init(final EndpointConfig config) {
    }


    public void destroy() {
    }


    public String encode(final Message message) throws EncodeException {
        return JsonUtil.formatMessage(message.getContent(), message.getMessage_from().getUsername());
    }

}

