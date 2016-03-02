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

import javax.ws.rs.client.WebTarget;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author airhacks.com
 */
public class HeadlandsClientTest {

    HeadlandsClient cut;

    @Before
    public void init() {
        this.cut = new HeadlandsClient();
        this.cut.init();
    }

    @Test
    public void resolveTemplate() {
        WebTarget target = this.cut.resolve("localhost", 8080, "configuration", "msg");
        String actualUri = target.getUri().toString();
        assertThat(actualUri, is("http://localhost:8080/headlands/resources/caches/configuration/entries/msg"));
    }

}
