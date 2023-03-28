package softuni.exam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.repository.ForecastRepository;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface ForecastService {

    boolean areImported();

    String readForecastsFromFile() throws IOException, JAXBException;

    String importForecasts() throws IOException, JAXBException;

    String exportForecasts() throws JAXBException, FileNotFoundException;
}