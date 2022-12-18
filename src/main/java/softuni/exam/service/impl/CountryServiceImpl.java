package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountryImportDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.constants.Messages.INVALID_COUNTRY;
import static softuni.exam.constants.Messages.VALID_COUNTRY;
import static softuni.exam.constants.Paths.COUNTRY_PATH;

@Service
public class CountryServiceImpl implements CountryService {

    private  final CountryRepository countryRepository;

    private final ModelMapper modelMapper;

    private final Gson gson;

    private final ValidationUtil validationUtil;

    public CountryServiceImpl(CountryRepository countryRepository
            , ModelMapper modelMapper
            , Gson gson
            , ValidationUtil validationUtil) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return countryRepository.count()>0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(COUNTRY_PATH);
    }

    @Override
    public String importCountries() throws IOException {

        StringBuilder stringBuilder=new StringBuilder();

        List<CountryImportDto>countries=
                Arrays.stream(gson.fromJson(readCountriesFromFile(),CountryImportDto[].class)).toList();

        for (CountryImportDto country : countries) {

            boolean isValid= validationUtil.isValid(country);

            if (this.countryRepository.findFirstByCountryName(country.getCountryName()).isPresent()){

                isValid=false;
            }

            if (isValid) {

                Country countryToSave=this.modelMapper.map(country,Country.class);

                this.countryRepository.saveAndFlush(countryToSave);

                stringBuilder.append(String.format(VALID_COUNTRY,country.getCountryName(),country.getCurrency()));

            }else {

                stringBuilder.append(INVALID_COUNTRY);
            }

            stringBuilder.append(System.lineSeparator());
        }

        return stringBuilder.toString();
    }

}
