package uz.pdp.codingbat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.codingbat.entity.Language;

public interface LanguageRepository extends JpaRepository<Language,Integer> {

    boolean existsByName(String name);
}
