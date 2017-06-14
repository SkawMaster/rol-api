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
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

/**
 * Log the current request and response data.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
public class BaseLoggingFilter extends OncePerRequestFilter {
	private static final String UNKNOWN_PAYLOAD = "[unknown]";
	private static final String EMPTY_PAYLOAD = "[empty]";

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		HttpServletRequest requestWrapper = request;
		HttpServletResponse responseWrapper = response;

		if (!(request instanceof ContentCachingRequestWrapper)) {
			requestWrapper = new ContentCachingRequestWrapper(request);
		}

		if (!(response instanceof ContentCachingResponseWrapper)) {
			responseWrapper = new ContentCachingResponseWrapper(response);
		}

		logger.debug(buildRequestLog(requestWrapper));

		filterChain.doFilter(requestWrapper, responseWrapper);

		logger.debug(buildResponseLog(responseWrapper));

		WebUtils.getNativeResponse(responseWrapper, ContentCachingResponseWrapper.class).copyBodyToResponse();
	}

	/**
	 * Build log message for request in bound.
	 *
	 * @param request Request in bound.
	 * @return Log message.
	 * @since 1.0.0
	 */
	protected String buildRequestLog(HttpServletRequest request) {
		ServerHttpRequest httpRequest = new ServletServerHttpRequest(request);

		StringBuilder sb = new StringBuilder("Server has received a request").append(StringUtils.LF)
				.append(" > ").append(httpRequest.getMethod()).append(" ").append(httpRequest.getURI()).append
						(StringUtils.LF);

		Map<String, String> headers = httpRequest.getHeaders().toSingleValueMap();
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			sb.append(" > ").append(entry.getKey()).append(": ").append(entry.getValue()).append(StringUtils.LF);
		}

		sb.append(extractRequestPayload(request));

		return sb.toString();
	}

	/**
	 * Build log message for response out bound.
	 *
	 * @param response Response out bound.
	 * @return Log message.
	 * @since 1.0.0
	 */
	protected String buildResponseLog(HttpServletResponse response) {
		StringBuilder sb = new StringBuilder("Server responded with a response").append(StringUtils.LF)
				.append(" > ").append(response.getStatus()).append(StringUtils.LF);

		Collection<String> keys = response.getHeaderNames();
		for (String key : keys) {
			sb.append(" > ").append(key).append(": ").append(response.getHeader(key)).append(StringUtils.LF);
		}

		sb.append(extractResponsePayload(response));

		return sb.toString();
	}

	private String extractRequestPayload(HttpServletRequest request) {
		ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);

		if (wrapper == null) {
			return UNKNOWN_PAYLOAD;
		}

		if (wrapper.getContentLength() > 0) {
			try {
				return request.getReader().lines().collect(Collectors.joining(StringUtils.LF));
			} catch (IOException e) {
				return UNKNOWN_PAYLOAD;
			}
		}
		return EMPTY_PAYLOAD;
	}

	private String extractResponsePayload(HttpServletResponse response) {
		ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);

		if (wrapper == null) {
			return UNKNOWN_PAYLOAD;
		}

		if (wrapper.getContentAsByteArray().length > 0) {
			byte[] buffer = wrapper.getContentAsByteArray();
			int length = Math.min(buffer.length, 5120);
			try {
				return new String(buffer, 0, length, wrapper.getCharacterEncoding());
			} catch (UnsupportedEncodingException ex) {
				return UNKNOWN_PAYLOAD;
			}
		}
		return EMPTY_PAYLOAD;
	}
}
