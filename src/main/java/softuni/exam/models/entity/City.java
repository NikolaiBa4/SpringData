package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="cities")
public class City extends BaseEntity{

    @Column(nullable = false,unique = true)
    @Size(min = 2,max = 60)
    private String cityName;

    @Column(columnDefinition ="TEXT")
    @Size(min = 2)
    private String description;

    @Column(nullable = false)
    @Min(value = 500)
    private Long population;

    @ManyToOne
    private Country country;
}
