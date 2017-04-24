package com.github.reflectoring.whoknows.user

import org.springframework.data.repository.CrudRepository

interface UserRepository extends CrudRepository<UserEntity, Long> {
}
