package softuni.exam.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CityImportDto {

    @Size(min = 2,max = 60)
    @NotNull
    private String cityName;

    @Min(value = 500)
    @NotNull
    private Long population;

    @Size(min = 2)
    private String description;

    @NotNull
    private Long country;
}
