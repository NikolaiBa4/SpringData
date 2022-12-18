package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.constants.DayOfWeek;
import softuni.exam.models.entity.Forecast;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast,Long> {

    Optional<Set<Forecast>>
    findAllByDayOfWeekAndCity_PopulationLessThanOrderByMaxTemperatureDescIdAsc
            (DayOfWeek dayOfWeek,Long population);
}
