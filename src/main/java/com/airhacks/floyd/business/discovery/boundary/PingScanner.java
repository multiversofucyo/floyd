package com.airhacks.floyd.business.discovery.boundary;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 *
 * @author airhacks.com
 */
public class PingScanner {

    private Client client;
    private WebTarget pingTarget;

    @PostConstruct
    public void initClient() {
        this.client = ClientBuilder.newClient();
        this.pingTarget = this.client.target("http://{host}:{port}/ping");
    }

    public List<Integer> activePings(String host, int portFrom, int portTo) {
        return IntStream.range(portFrom, portTo).
                parallel().
                filter(p -> test(host, p)).
                boxed().
                collect(Collectors.toList());
    }

    public boolean test(String host, int port) {
        try {
            Response response = this.pingTarget.resolveTemplate("host", host).
                    resolveTemplate("port", port).request().get();
            return response.getStatus() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    static String buildUri(String host, int value) {
        return host + value + "/ping";
    }
}