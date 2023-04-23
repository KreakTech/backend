package me.kreaktech.unility.repository;

import me.kreaktech.unility.constants.Enum;
import me.kreaktech.unility.entity.UniversityFetch;
import org.springframework.data.jpa.repository.JpaRepository;
import java.sql.Timestamp;
import java.util.Optional;

public interface UniversityFetchRepository extends JpaRepository<UniversityFetch, Integer> {
	Optional<UniversityFetch> findByUniversityNameAndLanguage(String name, Enum.Language language);
}