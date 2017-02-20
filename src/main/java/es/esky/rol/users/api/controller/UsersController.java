package es.esky.rol.users.api.controller;

import es.esky.rol.arch.domain.PaginationUtils;
import es.esky.rol.arch.domain.ApiError;
import es.esky.rol.users.api.exception.UserNotFoundException;
import es.esky.rol.users.domain.User;
import es.esky.rol.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Cristian Mateos López
 */
@RestController
@RequestMapping(value = UsersController.BASE_URL)
class UsersController {
    static final String BASE_URL = "/users";

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity findByCriteria(Pageable page, PagedResourcesAssembler<User> resourcesAssembler) {
        Page<User> userPage = usersService.findByCriteria(page);
        Link link = linkTo(methodOn(UsersController.class).findByCriteria(page, resourcesAssembler)).withSelfRel();
        return PaginationUtils.buildResponse(userPage, resourcesAssembler, link);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> findByUsername(@PathVariable("username") String username) {
        User user = usersService.findByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByUsername(@PathVariable("username") String username) {
        usersService.deleteByUsername(username);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> save(@RequestBody @Validated User user) {
        User userCreated = usersService.save(user);
        return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> userNotFound(UserNotFoundException e) {
        ApiError error = new ApiError("", "");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
