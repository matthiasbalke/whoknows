package com.github.reflectoring.whoknows.skill

import org.hibernate.validator.constraints.Length

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "SKILLS")
class SkillEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id

    @Length(min = 1, max = 255)
    @NotNull
    private String name

}
