package com.github.reflectoring.whoknows.user.rest

import com.github.reflectoring.whoknows.user.UserController
import com.github.reflectoring.whoknows.user.UserEntity
import groovy.transform.CompileStatic
import org.springframework.hateoas.mvc.ResourceAssemblerSupport
import org.springframework.stereotype.Component

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn

@CompileStatic
@Component
class UserResourceAssembler extends ResourceAssemblerSupport<UserEntity, UserResource> {

    UserResourceAssembler() {
        super(UserController.class, UserResource.class)
    }

    @Override
    UserResource toResource(UserEntity entity) {
        def resource = new UserResource()
        resource.setUsername(entity.getUsername())
        resource.setFirstName(entity.getFirstName())
        resource.setLastName(entity.getLastName())
        resource.setEmail(entity.getEmail())

        resource.add(linkTo(((UserController) methodOn(UserController.class)).getUser(entity.getId())).withSelfRel())

        return resource
    }

    UserEntity toEntity(UserResource resource) {
        def entity = new UserEntity()
        entity.setUsername(resource.getUsername())
        entity.setFirstName(resource.getFirstName())
        entity.setLastName(resource.getLastName())
        entity.setEmail(resource.getEmail())

        return entity
    }
}
