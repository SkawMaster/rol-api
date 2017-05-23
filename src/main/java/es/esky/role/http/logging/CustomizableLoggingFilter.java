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

package es.esky.role.http.logging;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

/**
 * Log the current request and response data.
 *
 * Can customize request and response log info overriding
 * {@link CustomizableLoggingFilter#logRequest(RequestLogData)} and
 * {@link CustomizableLoggingFilter#logResponse(ResponseLogData)}.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
public class CustomizableLoggingFilter extends OncePerRequestFilter {
	private static final String UNKNOWN_PAYLOAD = "[unknown]";
	private static final String EMPTY_PAYLOAD = "[empty]";

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		HttpServletRequest wrapperRequest = request;
		HttpServletResponse wrapperResponse = response;

		if (!(wrapperRequest instanceof ContentCachingRequestWrapper)) {
			wrapperRequest = new ContentCachingRequestWrapper(request);
		}

		if (!(wrapperResponse instanceof ContentCachingResponseWrapper)) {
			wrapperResponse = new ContentCachingResponseWrapper(response);
		}

		RequestLogData requestLogData = buildRequestLogData(wrapperRequest);
		logRequest(requestLogData);

		// Continue the chain.
		filterChain.doFilter(wrapperRequest, wrapperResponse);

		ResponseLogData responseLogData = buildResponseLogData(wrapperResponse);
		logResponse(responseLogData);

		WebUtils.getNativeResponse(wrapperResponse, ContentCachingResponseWrapper.class).copyBodyToResponse();
	}

	/**
	 * Log the request data.
	 *
	 * @param requestLogData Data of the current request.
	 * @since 1.0.0
	 */
	protected void logRequest(RequestLogData requestLogData) {
		logger.debug("Request in-bound: " + requestLogData);
	}

	/**
	 * Log the response data.
	 *
	 * @param responseLogData Data of the current response.
	 * @since 1.0.0
	 */
	protected void logResponse(ResponseLogData responseLogData) {
		logger.debug("Response out-bound: " + responseLogData);
	}

	private RequestLogData buildRequestLogData(HttpServletRequest request) {
		ServerHttpRequest httpRequest = new ServletServerHttpRequest(request);

		URI uri = httpRequest.getURI();
		HttpMethod method = httpRequest.getMethod();
		HttpHeaders headers = httpRequest.getHeaders();
		String body = extractRequestPayload(request);

		return new RequestLogData(uri, method, headers, body);
	}

	private ResponseLogData buildResponseLogData(HttpServletResponse response) {
		HttpStatus status = HttpStatus.valueOf(response.getStatus());
		String body = extractResponsePayload(response);

		return new ResponseLogData(status, body);
	}

	private String extractRequestPayload(HttpServletRequest request) {
		ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
		if (wrapper != null) {
			return extractPayload(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
		}
		return EMPTY_PAYLOAD;
	}

	private String extractResponsePayload(HttpServletResponse response) {
		ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
		if (wrapper != null) {
			return extractPayload(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
		}
		return EMPTY_PAYLOAD;
	}

	private String extractPayload(byte[] buffer, String characterEncoding) {
		if (buffer.length > 0) {
			int length = Math.min(buffer.length, 5120);
			try {
				return new String(buffer, 0, length, characterEncoding);
			} catch (UnsupportedEncodingException ex) {
				return UNKNOWN_PAYLOAD;
			}
		}
		return EMPTY_PAYLOAD;
	}
}
