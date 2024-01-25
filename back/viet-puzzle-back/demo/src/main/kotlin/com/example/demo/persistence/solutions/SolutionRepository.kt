package com.example.demo.persistence.solutions

import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface SolutionRepository: CrudRepository<Solution, Int>