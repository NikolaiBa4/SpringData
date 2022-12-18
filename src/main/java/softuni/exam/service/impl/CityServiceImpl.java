package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CityImportDto;
import softuni.exam.models.entity.City;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.constants.Messages.INVALID_CITY;
import static softuni.exam.constants.Messages.VALID_CITY;
import static softuni.exam.constants.Paths.CITY_PATH;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    private final CountryRepository countryRepository;

    private final ModelMapper modelMapper;

    private final Gson gson;

    private final ValidationUtil validationUtil;

    public CityServiceImpl(CityRepository cityRepository,
                               CountryRepository countryRepository,
                               ModelMapper modelMapper,
                               Gson gson,
                               ValidationUtil validationUtil) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }
    @Override
    public boolean areImported() {
        return this.cityRepository.count()>0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(CITY_PATH);
    }

    @Override
    public String importCities() throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        List<CityImportDto>cities=
                Arrays.stream(gson.fromJson(readCitiesFileContent(), CityImportDto[].class)).toList();
        for (CityImportDto city:cities) {

            boolean isValid= validationUtil.isValid(city);

            if (this.cityRepository.findFirstByCityName(city.getCityName()).isPresent()
                    || countryRepository.findById(city.getCountry()).isEmpty()){

                isValid=false;
            }

            if (isValid) {

                City cityToSave=this.modelMapper.map(city, City.class);

                cityToSave.setCountry(this.countryRepository.findById(city.getCountry()).get());

                this.cityRepository.save(cityToSave);

                stringBuilder.append(String.format(VALID_CITY,city.getCityName(),city.getPopulation()));
            }else {

                stringBuilder.append(INVALID_CITY);
            }

            stringBuilder.append(System.lineSeparator());

        };
        return stringBuilder.toString();

    }
}
