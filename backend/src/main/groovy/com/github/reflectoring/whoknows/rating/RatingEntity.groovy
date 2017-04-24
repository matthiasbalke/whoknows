package com.github.reflectoring.whoknows.rating

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "USER_SKILL_RATINGS")
class RatingEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;



}
