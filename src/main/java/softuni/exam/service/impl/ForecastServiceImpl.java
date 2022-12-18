package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.DayOfWeek;
import softuni.exam.models.dto.ForecastImportDto;
import softuni.exam.models.dto.ForecastWrapperDto;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.*;
import static softuni.exam.constants.Paths.FORECASTS_PATH;


@Service
public class ForecastServiceImpl implements ForecastService {

    public final String PRINT_FORMAT =
            "City: %s:%n -min temperature: %.2f%n --max temperature: %.2f%n ---sunrise: %s:%n ----sunset: %s:%n";
   private final ForecastRepository forecastRepository;

   private final ModelMapper modelMapper;

   private final ValidationUtil validationUtil;

   private final CityRepository cityRepository;

   private final XmlParser xmlParser;

   @Autowired
    public ForecastServiceImpl(ForecastRepository forecastRepository
           , ModelMapper modelMapper
           , ValidationUtil validationUtil
           , CityRepository cityRepository
           , XmlParser xmlParser) {
        this.forecastRepository = forecastRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.cityRepository = cityRepository;
        this.xmlParser = xmlParser;
    }


    @Override
    public boolean areImported() {

        return this.forecastRepository.count()>0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {

        return Files.readString(FORECASTS_PATH);
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {

       StringBuilder stringBuilder =new StringBuilder();

       final File file=FORECASTS_PATH.toFile();

        ForecastWrapperDto forecastWrapperDto=xmlParser.fromFile(file, ForecastWrapperDto.class);

        List<ForecastImportDto>forecasts=forecastWrapperDto.getForecasts();

        for (ForecastImportDto forecast : forecasts) {

            boolean isValid= validationUtil.isValid(forecast);

            if (this.cityRepository.findFirstById(forecast.getCity()).isEmpty()){

                isValid=false;
            }

            if (isValid) {

                City refCity= this.cityRepository.findFirstById(forecast.getCity()).get();

                Forecast forecastToSave=this.modelMapper.map(forecast, Forecast.class);

                forecastToSave.setCity(refCity);

                this.forecastRepository.saveAndFlush(forecastToSave);

                stringBuilder.append
                        (String.format
                                (VALID_FORECAST,forecast.getDayOfWeek(),forecast.getMaxTemperature()));

            }else {

                stringBuilder.append(INVALID_FORECAST);
            }

            stringBuilder.append(System.lineSeparator());

        }
        return stringBuilder.toString();
    }

    @Override
    public String exportForecasts() {
        final Set<Forecast> forecasts = this.forecastRepository
                .findAllByDayOfWeekAndCity_PopulationLessThanOrderByMaxTemperatureDescIdAsc(DayOfWeek.SUNDAY, 150000L)
                .orElseThrow(NoSuchElementException::new);


        return forecasts.stream()
                .map(forecast -> String.format(PRINT_FORMAT,
                        forecast.getCity().getCityName(),
                        forecast.getMinTemperature(),
                        forecast.getMaxTemperature(),
                        forecast.getSunrise(),
                        forecast.getSunset()))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
