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

import es.esky.rol.http.ApiHttpHeaders;
import es.esky.rol.http.header.SimpleTotalCountHeaderBuilder;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;

/**
 * @author Cristian Mateos López
 * @since 1.0.0
 */
public class SimpleTotalCountHeaderBuilderTest {

    private final static SimpleTotalCountHeaderBuilder totalCountHeaderBuilder = new SimpleTotalCountHeaderBuilder();

    @Test
    public void buildFromPage_ReturnCorrectlyHttpHeader() {
        Page<?> page = Mockito.mock(Page.class);
        Mockito.when(page.getTotalElements()).thenReturn(11L);

        HttpHeaders header = totalCountHeaderBuilder.buildFrom(page);

        assertThat(header, Matchers.hasKey(ApiHttpHeaders.TOTAL_COUNT));
        assertThat(header.get(ApiHttpHeaders.TOTAL_COUNT), hasSize(1));
        assertThat(header.get(ApiHttpHeaders.TOTAL_COUNT), hasItem("11"));
    }
}