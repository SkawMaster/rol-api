package es.esky.rol.arch.error;

import es.esky.rol.arch.error.factory.ErrorFactory;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Cristian Mateos López
 */
@RestController
@RequestMapping(value = ErrorController.ERROR_MAPPING)
public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController {

    final static String ERROR_MAPPING = "${server.error.path:${error.path:/error}}";

    private ErrorAttributes errorAttributes;
    private ErrorProperties errorProperties;
    private ErrorFactory errorFactory;

    public ErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties, ErrorFactory errorFactory) {
        Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
        this.errorAttributes = errorAttributes;
        Assert.notNull(errorProperties, "ErrorProperties must not be null");
        this.errorProperties = errorProperties;
        Assert.notNull(errorFactory, "ErrorFactory must not be null");
        this.errorFactory = errorFactory;
    }

    @Override
    public String getErrorPath() {
        return errorProperties.getPath();
    }

    @RequestMapping
    public Serializable error(HttpServletRequest request) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Map<String, Object> errorAttributesMap = errorAttributes.getErrorAttributes(requestAttributes, false);
        return errorFactory.create(errorAttributesMap);
    }
}
