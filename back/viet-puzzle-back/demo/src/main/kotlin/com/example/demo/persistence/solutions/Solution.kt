package com.example.demo.persistence.solutions

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Solution(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int? = null,
    var sequence: List<Int> = listOf(),
    @Enumerated(EnumType.STRING) var state: State? = State.INCORRECT
) {
    companion object {
        fun from(sequence: List<Int>, state: State): Solution {
            return Solution().apply {
                this.sequence = sequence
                this.state = state
            }
        }
    }
}
