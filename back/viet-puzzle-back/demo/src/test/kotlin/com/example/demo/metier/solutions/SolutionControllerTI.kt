package com.example.demo.metier.solutions

import com.example.demo.persistence.solutions.State

import com.example.demo.persistence.solutions.Solution
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class SolutionControllerTI {

    // TODO : Mocker des données

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should generate a solution`() {
        mockMvc.perform(get("/solutions/generate"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun `should return all solutions`() {
        val solutions = listOf(
            Solution(id = 1, sequence = listOf(1, 2, 3, 4, 7, 8, 9, 6, 5), state = State.CORRECT),
            Solution(id = 2, sequence = listOf(1, 7, 3, 4, 7, 8, 9, 6, 5), state = State.INCORRECT)
        )
        mockMvc.perform(get("/solutions"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun `should create a solution`() {
        val solutionDto = SolutionDto().apply {
            id = 1
            sequence = listOf(1, 2, 3, 4, 7, 8, 9, 6, 5)
            state = State.CORRECT
        }
        mockMvc.perform(
            post("/solutions").contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun `should delete all solutions`() {
        // TODO
    }

    @Test
    fun `should read a solution`() {
        // Arrange
        val solution = Solution(id = 1)
        mockMvc.perform(get("/solutions/1"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun `should update a solution`() {
        // Arrange
        val solutionDto = SolutionDto()
        mockMvc.perform(
            put("/solutions/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }
}