package com.airhacks.hello.boundary;

import com.airhacks.marina.boundary.CacheEntry;
import java.util.function.Function;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * @author airhacks.com
 */
@RequestScoped
@Path("hello")
public class HelloResource {

    @Inject
    @CacheEntry(host = "headlands", port = 8080, cache = "configuration", key = "msg")
    Instance<String> dynamicMessage;

    @Inject
    @CacheEntry(host = "headlands", port = 8080, cache = "configuration", key = "notexisting")
    String notexisting;

    @Inject
    @CacheEntry(host = "headlands", port = 8080, cache = "configuration", key = "notexisting", defaultValue = "doesnotexist")
    String notexistingWithDefault;

    @Inject
    @CacheEntry(host = "headlands", port = 8080, cache = "configuration")
    Function<String, String> configurationCache;

    @GET
    @Path("dynamic")
    public String message() {
        return dynamicMessage.get();
    }

    @GET
    @Path("not-existing")
    public String notExisting() {
        return notexisting;
    }

    @GET
    @Path("/configuration/{key}")
    public String configuration(@PathParam("key") String key) {
        return configurationCache.apply(key);
    }

    @GET
    @Path("not-existing-with-default")
    public String notExistingWithDefault() {
        return notexistingWithDefault;
    }

}
