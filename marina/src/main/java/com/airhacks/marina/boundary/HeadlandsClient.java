package com.airhacks.marina.boundary;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author airhacks.com
 */
@ApplicationScoped
public class HeadlandsClient {

    private Client client;
    private WebTarget tut;

    @PostConstruct
    public void init() {
        this.client = ClientBuilder.newClient();
        this.tut = this.client.target("http://{host}:{port}/headlands/resources/caches/{cache}/entries/{key}");

    }

    @Produces
    public String getString(InjectionPoint ip) {
        Annotated annotated = ip.getAnnotated();
        CacheEntry cacheEntry = annotated.getAnnotation(CacheEntry.class);
        return resolve(cacheEntry).
                request().get(String.class);
    }

    WebTarget resolve(CacheEntry cacheEntry) {
        return this.resolve(cacheEntry.host(), cacheEntry.port(),
                cacheEntry.cache(), cacheEntry.key());
    }

    WebTarget resolve(String host, int port, String cache, String key) {
        return this.tut.resolveTemplate("host", host).
                resolveTemplate("port", port).
                resolveTemplate("cache", cache).
                resolveTemplate("key", key);
    }

    @Produces
    public long getLong(InjectionPoint ip) {
        String stringValue = getString(ip);
        if (stringValue == null) {
            return 0;
        }
        return Long.parseLong(stringValue);
    }

    @Produces
    public int getInteger(InjectionPoint ip) {
        String stringValue = getString(ip);
        if (stringValue == null) {
            return 0;
        }
        return Integer.parseInt(stringValue);
    }

    @Produces
    public boolean getBoolean(InjectionPoint ip) {
        String stringValue = getString(ip);
        if (stringValue == null) {
            return false;
        }
        return Boolean.parseBoolean(stringValue);
    }

}
