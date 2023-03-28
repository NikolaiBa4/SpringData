package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City,Long> {

    Optional<City>findFirstByCityName(String cityName);

    Optional<City>findFirstById(Long id);

}
