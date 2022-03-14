package uz.pdp.codingbat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.codingbat.entity.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Integer> {

    boolean existsByNameAndLanguageId(String name, Integer language_id);

    List<Task> findAllByLanguageId(Integer language_id);
}
