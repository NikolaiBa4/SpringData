package softuni.exam.util;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

public interface XmlParser {

    <T> T fromFile(File filePath, Class<T> object) throws JAXBException, FileNotFoundException;

    <T> void toFile(T object, File file) throws JAXBException;
}
