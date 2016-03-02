package com.airhacks.hello.boundary;

import com.airhacks.marina.boundary.CacheEntry;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

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
    @Path("not-existing-with-default")
    public String notExistingWithDefault() {
        return notexistingWithDefault;
    }

}
