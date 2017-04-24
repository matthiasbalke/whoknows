package com.github.reflectoring.whoknows.user

import com.github.reflectoring.whoknows.user.rest.UserResource
import com.github.reflectoring.whoknows.user.rest.UserResourceAssembler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController {

    private UserResourceAssembler assembler
    private UserRepository repository

    @Autowired
    UserController(UserResourceAssembler assembler, UserRepository repository) {
        this.assembler = assembler
        this.repository = repository
    }

    @GetMapping(value = "{userId}")
    ResponseEntity<UserResource> getUser(@PathVariable Long userId) {

        def user = repository.findOne(userId)

        if (user == null) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(assembler.toResource(user))
    }
}
