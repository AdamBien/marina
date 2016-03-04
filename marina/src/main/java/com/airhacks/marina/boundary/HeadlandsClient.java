package com.airhacks.marina.boundary;

import java.util.function.Function;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * A remote headlands client which communicates with the headlands node via HTTP
 * API and exposes the values via injection. This class is meant to be used
 * indirectly via injection of cache values.
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
    @CacheEntry(cache = "-")
    public String getString(InjectionPoint ip) {
        Annotated annotated = ip.getAnnotated();
        CacheEntry cacheEntry = annotated.getAnnotation(CacheEntry.class);
        Response response = resolve(cacheEntry).
                request().get();
        if (response.getStatus() == 204 && !cacheEntry.defaultValue().isEmpty()) {
            return cacheEntry.defaultValue();
        }
        return response.readEntity(String.class);
    }

    @Produces
    @CacheEntry(cache = "-")
    public Function<String, String> getCache(InjectionPoint ip) {
        Annotated annotated = ip.getAnnotated();
        CacheEntry cacheEntry = annotated.getAnnotation(CacheEntry.class);
        WebTarget target = this.resolve(cacheEntry.host(), cacheEntry.port(), cacheEntry.cache());
        return (key) -> target.path(key).request().get(String.class);

    }

    WebTarget resolve(CacheEntry cacheEntry) {
        return this.resolve(cacheEntry.host(), cacheEntry.port(),
                cacheEntry.cache(), cacheEntry.key());
    }

    WebTarget resolve(String host, int port, String cache, String key) {
        return resolve(host, port, cache).path(key);
    }

    WebTarget resolve(String host, int port, String cache) {
        StringBuilder builder = new StringBuilder();
        String hostUri = builder.append("http://").
                append(host).
                append(":").
                append(port).toString();
        return this.client.target(hostUri + "/headlands/resources/caches/{cache}/entries/").
                resolveTemplate("cache", cache);

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
