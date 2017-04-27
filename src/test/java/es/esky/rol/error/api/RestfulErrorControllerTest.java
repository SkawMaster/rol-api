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

package es.esky.rol.error.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.web.ErrorProperties;

import es.esky.rol.error.domain.ApiError;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(MockitoJUnitRunner.class)
public class RestfulErrorControllerTest {
    
    private final static String EXPECTED_PATH = "dummy";
    
    @InjectMocks
    private RestfulErrorController restfulErrorController;
    
    @Mock
    private ErrorProperties errorProperties;
    
    @Test
    public void getErrorPath_returnErrorPropertiesPath() {
        when(errorProperties.getPath()).thenReturn(EXPECTED_PATH);
        
        String path = restfulErrorController.getErrorPath();
        
        assertThat(path, equalTo(EXPECTED_PATH));
    }
    
    @Test
    public void error_returnApiUnauthenticationError() {
        ApiError error = restfulErrorController.error();
        
        assertThat(error.getCode(), equalTo("unauthorized"));
        assertThat(error.getMessage(), equalTo("Authentication error"));
    }
}
