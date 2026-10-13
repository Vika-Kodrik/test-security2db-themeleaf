package ru.Kodrik.security.repository;

import ru.Kodrik.security.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
