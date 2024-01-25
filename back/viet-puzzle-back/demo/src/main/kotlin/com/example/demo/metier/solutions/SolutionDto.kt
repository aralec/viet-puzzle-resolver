package com.example.demo.metier.solutions

import com.example.demo.persistence.solutions.State
import com.fasterxml.jackson.annotation.JsonProperty

class SolutionDto {
    @field:JsonProperty("id")
    var id: Int? = null

    @field:JsonProperty("sequence")
    var sequence: List<Int> = listOf()

    @field:JsonProperty("state")
    var state: State? = null

}