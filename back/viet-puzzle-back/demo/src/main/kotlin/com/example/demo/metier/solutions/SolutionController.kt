package com.example.demo.metier.solutions

import com.example.demo.persistence.solutions.Solution
import org.modelmapper.ModelMapper
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/solutions")
@CrossOrigin("*")
@Transactional(rollbackFor = [Exception::class], propagation = Propagation.REQUIRED)
class SolutionController (
    private val service: SolutionService,
    private val mapper: ModelMapper
){
    @GetMapping("/generate")
    fun generate(): ResponseEntity<Long> {
        val duration = service.generate()
        return ResponseEntity.ok(duration)
    }

    @GetMapping("/search")
    fun search(): ResponseEntity<List<SolutionDto>> {
        val entities = service.solutionRepository.findAll()
        return ResponseEntity.ok(entities.map{ mapper.map(it, SolutionDto::class.java)})
    }

    @PostMapping
    fun create(@RequestBody solutionDto: SolutionDto): ResponseEntity<SolutionDto> {
        val entity = service.create(
            solution = mapper.map(solutionDto, Solution::class.java),
            testIt = true
        )
        return ResponseEntity.ok(mapper.map(entity, SolutionDto::class.java))
    }

    @DeleteMapping
    fun deleteAll(): ResponseEntity<Void> {
        service.deleteAll()
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    fun read(@PathVariable("id") id: Int): ResponseEntity<SolutionDto> {
        val entity = service.read(id)
        return ResponseEntity.ok(mapper.map(entity, SolutionDto::class.java))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: Int, @RequestBody solutionDto: SolutionDto): ResponseEntity<SolutionDto> {
        val entity = service.update(mapper.map(solutionDto, Solution::class.java))
        return ResponseEntity.ok(mapper.map(entity, SolutionDto::class.java))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Int): ResponseEntity<Void> {
        this.service.delete(id)
        return ResponseEntity.ok().build()
    }
}