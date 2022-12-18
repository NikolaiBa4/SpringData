package softuni.exam.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.constants.DayOfWeek;


import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "forecast")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastImportDto {

    @DecimalMin(value = "-50")
    @DecimalMax(value = "40")
    @NotNull
    @XmlElement(name = "min_temperature")
    private Double minTemperature;

    @DecimalMin(value = "-20")
    @DecimalMax(value = "60")
    @XmlElement(name = "max_temperature")
    @NotNull
    private Double maxTemperature;

    @NotNull
    @XmlElement
    private String sunset;

    @NotNull
    @XmlElement
    private String sunrise;

    @NotNull
    @XmlElement(name = "day_of_week")
    private DayOfWeek dayOfWeek;

    @NotNull
    @XmlElement
    private Long city;
}
