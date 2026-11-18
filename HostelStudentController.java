
package com.kiet.hostelmangamentsystem;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class HostelStudentController {

    private final HostelStudentRepository repository;

    public HostelStudentController(HostelStudentRepository repository) {
        this.repository = repository;
    }

    // POST: Add Student
    @PostMapping
    public ResponseEntity<HostelStudent> addStudent(
            @RequestBody HostelStudent student) {

        HostelStudent saved = repository.save(student);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // GET: Get All Students
    @GetMapping
    public ResponseEntity<List<HostelStudent>> getAllStudents() {

        return ResponseEntity.ok(repository.findAll());
    }

    // GET: Get Student By ID
    @GetMapping("/{id}")
    public ResponseEntity<HostelStudent> getStudentById(
            @PathVariable int id) {

        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT: Update Student
    @PutMapping("/{id}")
    public ResponseEntity<HostelStudent> updateStudent(
            @PathVariable int id,
            @RequestBody HostelStudent newStudent) {

        return repository.findById(id).map(student -> {

            student.setStudentName(newStudent.getStudentName());
            student.setRollNumber(newStudent.getRollNumber());
            student.setHostelName(newStudent.getHostelName());
            student.setRoomNumber(newStudent.getRoomNumber());
            student.setFee(newStudent.getFee());

            HostelStudent updated = repository.save(student);

            return ResponseEntity.ok(updated);

        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Delete Student
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(
            @PathVariable int id) {

        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student not found with ID: " + id);
        }

        repository.deleteById(id);

        return ResponseEntity.ok("Student deleted successfully!");
    }
}
