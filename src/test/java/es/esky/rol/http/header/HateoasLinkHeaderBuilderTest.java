/*
 * Copyright 2016-2017 the original author or authors.
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

package es.esky.rol.http.header;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class HateoasLinkHeaderBuilderTest {
    
    private HateoasLinkHeaderBuilder linkHeaderBuilder;

    @Before
    public void setup() {
        UriComponentsBuilder builder = ServletUriComponentsBuilder.fromHttpUrl("http://dummy.dum/dum");
        linkHeaderBuilder = new HateoasLinkHeaderBuilder(builder);
    }
    
    @Test
    public void buildFirst_ReturnFirstPageLink() {
        /*final String expectedLink = "aaa";
        String link = linkHeaderBuilder.buildFirst();
        System.out.println(link);
        assertThat(link, equalTo(expectedLink));*/
    }
}
