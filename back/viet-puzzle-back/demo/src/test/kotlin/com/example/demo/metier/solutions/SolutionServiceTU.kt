package com.example.demo.metier.solutions

import com.example.demo.persistence.solutions.Solution
import com.example.demo.persistence.solutions.SolutionRepository
import com.example.demo.persistence.solutions.State
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class SolutionServiceTU {

    @Autowired
    private lateinit var solutionService: SolutionService

    @Mock
    private lateinit var solutionRepository: SolutionRepository

    @BeforeEach
    fun setUp() {
        val s1 = Solution(
            id = 1,
            sequence = listOf(1, 2, 3, 4, 7, 8, 9, 6, 5),
            state = State.CORRECT
        )
        solutionRepository.save(s1)
        Mockito
            .`when`(solutionRepository.findById(1))
            .thenReturn(Optional.of(s1))  // FIXME : Fonctionne sur une BDD PostgreSQL
    }

    @Test
    fun generateSolutions() {
        val solutions = solutionService.findSolutions()
        Assertions.assertEquals(2956, solutions.size)
    }

    @Test
    fun readSolution() {
        val solution = solutionService.read(1)  // FIXME : "No value". Comment mocker des données avec H2 ?
        Assertions.assertNotNull(solution)
        Assertions.assertEquals(1, solution.id)
        Assertions.assertEquals(State.CORRECT, solution.state)
    }

    @Test
    fun createSolution() {
        val sequence = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        val solution = Solution.from(sequence, State.CORRECT)
        val createdSolution = solutionService.create(solution)
        Assertions.assertNotEquals(0, createdSolution.id)
        Assertions.assertEquals(sequence, createdSolution.sequence)
        Assertions.assertEquals(State.INCORRECT, createdSolution.state)
    }

    @Test
    fun updateSolution() {
        val solution = solutionService.read(1)
        Assertions.assertEquals(State.CORRECT, solution.state)

        solution.sequence = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        val updatedSolution = solutionService.update(solution)
        Assertions.assertEquals(solution, updatedSolution)
        Assertions.assertEquals(State.INCORRECT, updatedSolution.state)
    }

    @Test
    fun deleteSolution() {
        val solution = solutionService.read(1)
        Assertions.assertNotEquals(0, solution.id)

        solutionService.delete(solution.id!!)
        val deletedSolution = solutionService.read(1)
        Assertions.assertNull(deletedSolution)
    }

    @Test
    fun containsDuplicates() {
        val solution = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        Assertions.assertFalse(solutionService.containsDuplicates(solution))

        val duplicatedSolution = solution.plus(1)
        Assertions.assertTrue(solutionService.containsDuplicates(duplicatedSolution))
    }

    @Test
    fun generatePermutations() {
        val numbers = (1..9).toList()
        val permutations = solutionService.generatePermutations(numbers.toMutableList())
        Assertions.assertEquals(362880, permutations.size)

        for (permutation in permutations) {
            Assertions.assertNotEquals(permutation.component1(), permutation.component2())
            Assertions.assertNotEquals(permutation.component2(), permutation.component3())
            Assertions.assertNotEquals(permutation.component3(), permutation.component4())
        }
    }
}