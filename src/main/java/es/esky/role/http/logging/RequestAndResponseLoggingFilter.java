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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

/**
 * @author Cristian Mateos López
 * @since 1.0.0
 */
public class RequestAndResponseLoggingFilter extends OncePerRequestFilter {
	private static final String UNKNOWN_PAYLOAD = "[unknown]";
	private static final String EMPTY_PAYLOAD = "[empty]";

	private static final Logger logger = LoggerFactory.getLogger(RequestAndResponseLoggingFilter.class);

	private RequestLogDataPrinter requestLogDataPrinter;
	private ResponseLogDataPrinter responseLogDataPrinter;

	public RequestAndResponseLoggingFilter(RequestLogDataPrinter requestLogDataPrinter, ResponseLogDataPrinter responseLogDataPrinter) {
		this.requestLogDataPrinter = requestLogDataPrinter;
		this.responseLogDataPrinter = responseLogDataPrinter;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (!(request instanceof ContentCachingRequestWrapper)) {
			request = new ContentCachingRequestWrapper(request);
		}

		if (!(response instanceof ContentCachingResponseWrapper)) {
			response = new ContentCachingResponseWrapper(response);
		}

		logRequest(request);
		filterChain.doFilter(request, response);
		logResponse(response);

		updateResponse(response);
	}

	private void updateResponse(HttpServletResponse response) throws IOException {
		ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper
				.class);
		wrapper.copyBodyToResponse();
	}

	private void logRequest(HttpServletRequest request) {
		RequestLogData data = buildRequestData(request);
		logger.debug(requestLogDataPrinter.print(data));
	}

	private void logResponse(HttpServletResponse response) {
		ResponseLogData data = buildResponseData(response);
		logger.debug(responseLogDataPrinter.print(data));
	}

	private RequestLogData buildRequestData(HttpServletRequest request) {
		ServerHttpRequest httpRequest = new ServletServerHttpRequest(request);

		RequestLogData data = new RequestLogData();
		data.setUri(httpRequest.getURI());
		data.setMethod(httpRequest.getMethod());
		data.setHeaders(httpRequest.getHeaders());
		data.setBody(buildRequestPayload(request));

		return data;
	}

	private ResponseLogData buildResponseData(HttpServletResponse response) {
		ResponseLogData data = new ResponseLogData();

		data.setStatus(HttpStatus.valueOf(response.getStatus()));
		data.setBody(buildResponsePayload(response));

		return data;
	}

	private String buildRequestPayload(HttpServletRequest request) {
		ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
		if (wrapper != null) {
			return buildPayload(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
		}
		return EMPTY_PAYLOAD;
	}

	private String buildResponsePayload(HttpServletResponse response) {
		ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
		if (wrapper != null) {
			return buildPayload(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
		}
		return EMPTY_PAYLOAD;
	}

	private String buildPayload(byte[] buffer, String characterEncoding) {
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
