/*
 * Copyright 2016 Adam Bien.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.airhacks.marina.boundary;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

/**
 * A required qualifier.
 *
 * @author airhacks.com
 */
@Qualifier
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheEntry {

    /**
     *
     * @return a key to fetch a value from the cache
     */
    @Nonbinding
    String key() default "";

    /**
     *
     * @return a value to be returned at cache miss
     */
    @Nonbinding
    String defaultValue() default "";

    /**
     *
     * @return the name of the headlands cache
     */
    @Nonbinding
    String cache();

    /**
     *
     * @return host name or IP address
     */
    @Nonbinding
    String host() default "headlands";

    /**
     *
     * @return port number of the remote service
     */
    @Nonbinding
    int port() default 8080;

}
