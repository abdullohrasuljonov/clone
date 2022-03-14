package uz.pdp.codingbat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.codingbat.entity.Example;

import java.util.List;

public interface ExampleRepository extends JpaRepository<Example,Integer> {

    boolean existsByTextAndTaskId(String text, Integer task_id);

    List<Example> findAllByTaskId(Integer task_id);
}
