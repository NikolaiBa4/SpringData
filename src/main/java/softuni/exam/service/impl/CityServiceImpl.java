package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CityImportDto;
import softuni.exam.models.entity.City;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.constants.Messages.INVALID_CITY;
import static softuni.exam.constants.Messages.VALID_CITY_FORMAT;
import static softuni.exam.constants.Paths.CITY_PATH;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtils validationUtils;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, CountryRepository countryRepository, ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(CITY_PATH);
    }

    @Override
    public String importCities() throws IOException {

        final StringBuilder stringBuilder = new StringBuilder();


        final List<CityImportDto> cities = Arrays.stream(gson.fromJson(readCitiesFileContent(), CityImportDto[].class))
                .toList();

        for (CityImportDto city : cities) {
            boolean isValid = this.validationUtils.isValid(city);

            if (this.cityRepository.findFirstByCityName(city.getCityName()).isPresent()) {

                stringBuilder.append(INVALID_CITY);
                continue;
            }

            if (isValid) {

                if (this.countryRepository.findById(city.getCountry()).isPresent()) {
                    City cityToSave = this.modelMapper.map(city, City.class);

                    cityToSave.setCountry(this.countryRepository.findById(city.getCountry()).get());

                    stringBuilder.append(String.format(VALID_CITY_FORMAT, city.getCityName(), city.getPopulation()));
                    this.cityRepository.save(cityToSave);
                } else {
                    stringBuilder.append(INVALID_CITY).append(System.lineSeparator());
                }

            } else {
                stringBuilder.append(INVALID_CITY);
            }
        }

        return stringBuilder.toString();
    }
}