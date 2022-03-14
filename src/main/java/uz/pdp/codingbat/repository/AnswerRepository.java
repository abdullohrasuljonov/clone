package uz.pdp.codingbat.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.codingbat.entity.Answer;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer,Integer> {

   List<Answer> findAllByUserId(Integer user_id);
}
