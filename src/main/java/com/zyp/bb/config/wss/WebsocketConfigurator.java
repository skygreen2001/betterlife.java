package com.zyp.bb.config.wss;


import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;
import java.util.List;
import java.util.Map;


public class WebsocketConfigurator extends Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        Map<String, List<String>> headers = request.getHeaders();
//        System.out.println(headers);
        if (headers!=null) {
            if (headers.get("userId")!=null && headers.get("userId").size()>0) {
                sec.getUserProperties().put("userId", headers.get("userId").get(0));
            }
            if (headers.get("at")!=null && headers.get("at").size()>0) {
                sec.getUserProperties().put("at", headers.get("at").get(0));
            }
        }
    }
}