package com.github.reflectoring.whoknows.rating

import org.springframework.data.repository.CrudRepository

interface RatingRepository extends CrudRepository<RatingEntity, Long> {
}
