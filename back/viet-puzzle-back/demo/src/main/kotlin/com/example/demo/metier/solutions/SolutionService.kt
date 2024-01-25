package com.example.demo.metier.solutions

import com.example.demo.persistence.solutions.Solution
import com.example.demo.persistence.solutions.SolutionRepository
import com.example.demo.persistence.solutions.State
import org.springframework.stereotype.Service

@Service
class SolutionService (
    val solutionRepository: SolutionRepository
){

    fun generate(): Long {
        deleteAll()
        val start = System.currentTimeMillis()
        val solutions = findSolutions()
        val end = System.currentTimeMillis()

        solutions.forEach {
            val s = Solution.from(it, State.CORRECT)
            create(s)
        }

        return end - start
    }

    fun read(id: Int): Solution {
        val entity: Solution = solutionRepository.findById(id).orElseThrow()
        return entity
    }

    fun create(solution: Solution, testIt: Boolean? = false): Solution {
        if (testIt == true) {
            solution.state = if (formula(solution.sequence) == 66) {
                State.CORRECT
            } else {
                State.INCORRECT
            }
        }
        return solutionRepository.save(solution)
    }

    fun update(solution: Solution): Solution {
        solution.state = if (formula(solution.sequence) == 66) {
            State.CORRECT
        } else {
            State.INCORRECT
        }
        return solutionRepository.save(solution)
    }

    fun delete(id: Int) {
        val solution = read(id)
        return solutionRepository.delete(solution)
    }

    fun deleteAll() {
        return solutionRepository.deleteAll()
    }

    /**
     * Tableau du casse-tête écrit sous la forme d'une équation
     * à 9 inconnues.
     */
    fun formula(sequence: List<Int>): Int {
        val (a, b, c, d, e, f, g, h, i) = sequence
        return a + (13 * b) / c + d + (12 * e) - f - (11 + g * h) / i - 10
    }

    fun findSolutions(): List<List<Int>> {
        val digits = (1..9).toMutableList()
        val solutions = mutableListOf<List<Int>>()

        val permutations = generatePermutations(digits)
        permutations.forEach { sequence ->
            val s = sequence.toList()
            if (formula(s) == 66) {
                if (!containsDuplicates(s)) {
                    solutions.add(s)
                }
            }
        }
        return solutions.toList()
    }

    fun containsDuplicates(solution: List<Int>): Boolean {
        for (i in solution.indices) {
            for (j in solution.indices) {
                if (i != j && solution[i] == solution[j]) {
                    return true
                }
            }
        }
        return false
    }

    fun generatePermutations(elements: MutableList<Int>): List<List<Int>> {
        val permutations = mutableListOf<List<Int>>()
        generatePermutations(elements, permutations, 0)
        return permutations
    }

    /**
     * Source : https://www.baeldung.com/cs/array-generate-all-permutations
     */
    private fun generatePermutations(
        elements: MutableList<Int>,
        permutations: MutableList<List<Int>>,
        index: Int
    ) {
        if (index == elements.size) {
            permutations.add(elements.toList())
            return
        }
        for (i in index until elements.size) {
            swap(elements, i, index)
            generatePermutations(elements, permutations, index + 1)
            swap(elements, i, index)
        }
    }

    private fun swap(elements: MutableList<Int>, i: Int, j: Int) {
        val temp = elements[i]
        elements[i] = elements[j]
        elements[j] = temp
    }

    operator fun <T> List<T>.component6() = this[5]
    operator fun <T> List<T>.component7() = this[6]
    operator fun <T> List<T>.component8() = this[7]
    operator fun <T> List<T>.component9() = this[8]
}