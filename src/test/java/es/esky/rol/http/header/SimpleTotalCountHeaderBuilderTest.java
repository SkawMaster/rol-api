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

import es.esky.rol.http.header.SimpleTotalCountHeaderBuilder;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

/**
 * @author Cristian Mateos López
 * @since 1.0.0
 */
public class SimpleTotalCountHeaderBuilderTest {

    private final static SimpleTotalCountHeaderBuilder totalCountHeaderBuilder = new SimpleTotalCountHeaderBuilder();

    @Test
    public void buildFromPage_ReturnCorrectlyHttpHeader() {
        final String expectedTotal = "11";
        
        Page<?> page = Mockito.mock(Page.class);
        Mockito.when(page.getTotalElements()).thenReturn(11L);

        String total = totalCountHeaderBuilder.buildTotal(page);

        assertThat(total, equalTo(expectedTotal));
    }
}