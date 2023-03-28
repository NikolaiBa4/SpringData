package softuni.exam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.repository.CityRepository;

import java.io.IOException;

public interface CityService {

    boolean areImported();

    String readCitiesFileContent() throws IOException;

    String importCities() throws IOException;
}
