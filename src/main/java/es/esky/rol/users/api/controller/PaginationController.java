package es.esky.rol.users.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;
import org.springframework.web.util.UriComponentsBuilder;

@ControllerAdvice
public class PaginationController extends AbstractMappingJacksonResponseBodyAdvice {
    
    public static final String LINK_STANDARD_FMT = "<%s>; rel=\"%s\"";
    public static final String QUERY_PARAM_PAGE = "page";
    public static final String LINK_HEADER_FIRST = "first";
    public static final String LINK_HEADER_PREVIOUS = "prev";
    public static final String LINK_HEADER_NEXT = "next";
    public static final String LINK_HEADER_LAST = "last";
    
    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue value,
                                           MediaType mediaType,
                                           MethodParameter methodParameter,
                                           ServerHttpRequest request,
                                           ServerHttpResponse response) {
        Page<?> page = (Page<?>) value.getValue();
        
        response.getHeaders().add("X-Total-Count", String.valueOf(page.getTotalElements()));
        response.getHeaders().add(HttpHeaders.LINK, buildHttpHeaderLinks(request, page));
        
        value.setValue(page.getContent());
    }
    
    @Override
    public boolean supports(MethodParameter returnType, Class< ? extends HttpMessageConverter< ? >> converterType) {
        return super.supports(returnType, converterType) &&
            Page.class.isAssignableFrom(returnType.getParameterType());
    }
    
    private String buildHttpHeaderLinks(ServerHttpRequest request, Page<?> page) {
        List<String> headerLinks = new ArrayList<>();

        if (!page.isFirst()) {
          headerLinks.add(String.format(LINK_STANDARD_FMT, UriComponentsBuilder.fromHttpRequest(request)
              .replaceQueryParam(QUERY_PARAM_PAGE, 0)
              .build(), LINK_HEADER_FIRST));
        }
    
        if (page.hasPrevious()) {
          headerLinks.add(String.format(LINK_STANDARD_FMT, UriComponentsBuilder.fromHttpRequest(request)
                  .replaceQueryParam(QUERY_PARAM_PAGE, page.previousPageable().getPageNumber())
                  .build(), LINK_HEADER_PREVIOUS));
        }
    
        if (page.hasNext()) {
          headerLinks.add(String.format(LINK_STANDARD_FMT, UriComponentsBuilder.fromHttpRequest(request)
                  .replaceQueryParam(QUERY_PARAM_PAGE, page.nextPageable().getPageNumber())
                  .build(), LINK_HEADER_NEXT));
        }
    
        if (!page.isLast()) {
          headerLinks.add(String.format(LINK_STANDARD_FMT, UriComponentsBuilder.fromHttpRequest(request)
              .replaceQueryParam(QUERY_PARAM_PAGE, page.getTotalPages() - 1)
              .build(), LINK_HEADER_LAST));
        }
    
        return StringUtils.join(headerLinks, ", ");
    }
}
