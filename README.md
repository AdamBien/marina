# marina

Headlands Data Injector

# installation

```xml
<dependency>
  <groupId>com.airhacks</groupId>
  <artifactId>marina</artifactId>
  <version>0.0.1</version>
</dependency>
```

# description

marina is a client for the RESTful interface of the [headlands](http://github.com/AdamBien/headlands) project.

The values are injected from the specified cache with the `@CacheEntry` qualifier:


An headlands instance running on host "headlands" on port "8080" is asked for the value of the key "msg" from the cache "configuration":

```java
    @Inject
    @CacheEntry(host = "headlands", port = 8080, cache = "configuration", key = "msg")
    Instance<String> dynamicMessage;
```
An headlands instance running on host "headlands" on port "8080" is asked for the value of the key "notexisting" from the cache "configuration". In case the key is not found, the default value "doesnotexist" is returned.

```java
    @Inject
    @CacheEntry(host = "headlands", port = 8080, cache = "configuration", key = "notexisting", defaultValue = "doesnotexist")
    String notexistingWithDefault;

```

An headlands instance running on host "headlands" on port "8080" is asked for the cache "configuration" abstracted as `Function<String,String>`. The call Function#apply(key) will return the current value from the cache.

```java
    @Inject
    @CacheEntry(host = "headlands", port = 8080, cache = "configuration")
    Function<String, String> configurationCache;
```
