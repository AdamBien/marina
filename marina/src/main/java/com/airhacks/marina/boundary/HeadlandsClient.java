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

    @PostConstruct
    public void init() {
        this.client = ClientBuilder.newClient();
    }

    @Produces
    @CacheEntry(cache = "-", key = "-")
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
        StringBuilder builder = new StringBuilder();
        String uri = builder.append("http://").
                append(host).
                append(":").
                append(port).
                append("/headlands/resources/caches/").
                append(cache).
                append("/entries/").
                append(key).
                toString();
        return this.client.target(uri);
    }

    @Produces
    @CacheEntry(cache = "-", key = "-")
    public long getLong(InjectionPoint ip) {
        String stringValue = getString(ip);
        if (stringValue == null) {
            return 0;
        }
        return Long.parseLong(stringValue);
    }

    @Produces
    @CacheEntry(cache = "-", key = "-")
    public int getInteger(InjectionPoint ip) {
        String stringValue = getString(ip);
        if (stringValue == null) {
            return 0;
        }
        return Integer.parseInt(stringValue);
    }

    @Produces
    @CacheEntry(cache = "-", key = "-")
    public boolean getBoolean(InjectionPoint ip) {
        String stringValue = getString(ip);
        if (stringValue == null) {
            return false;
        }
        return Boolean.parseBoolean(stringValue);
    }

}
