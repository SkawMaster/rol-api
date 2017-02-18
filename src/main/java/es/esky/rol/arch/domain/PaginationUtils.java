package es.esky.rol.arch.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Cristian Mateos López
 */
public class PaginationUtils {
    private PaginationUtils() {
    }

    public static <T> ResponseEntity<List<T>> buildResponse(Page<T> page, PagedResourcesAssembler<T> resourcesAssembler, Link link) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(page.getTotalElements()));
        PagedResources<Resource<T>> links = resourcesAssembler.toResource(page, link);
        List<Link> linkList = new ArrayList<>();
        if (links.hasLink(Link.REL_PREVIOUS)) {
            linkList.add(links.getLink(Link.REL_PREVIOUS));
            linkList.add(links.getLink(Link.REL_FIRST));
        }
        if (links.hasLink(Link.REL_NEXT)) {
            linkList.add(links.getLink(Link.REL_NEXT));
            linkList.add(links.getLink(Link.REL_LAST));
        }
        if (!linkList.isEmpty()) {
            headers.add(HttpHeaders.LINK, linkList.stream().map(Link::toString).collect(Collectors.joining(",")));
        }

        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
