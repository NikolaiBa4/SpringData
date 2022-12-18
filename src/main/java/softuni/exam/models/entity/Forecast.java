package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.constants.DayOfWeek;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="forecasts")
public class Forecast extends BaseEntity{

    @Column(nullable = false)
    @Min(value = -50)
    @Max(value = 40)
    private Double minTemperature;

    @Column(nullable = false)
    @Min(value = -20)
    @Max(value = 60)
    private Double maxTemperature;

    @Column(nullable = false)
    private LocalTime sunrise;

    @Column(nullable = false)
    private LocalTime sunset;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @ManyToOne
    private City city;
}
