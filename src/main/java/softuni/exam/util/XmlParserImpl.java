package softuni.exam.util;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Component
public class XmlParserImpl implements XmlParser{


    @Override
    public <T> T fromFile(File filePath, Class<T> object) throws JAXBException, FileNotFoundException {

        JAXBContext jaxbContext= JAXBContext.newInstance(object);

        Unmarshaller unmarshaller=jaxbContext.createUnmarshaller();

        FileReader fileReader=new FileReader(filePath);

      return  (T)unmarshaller.unmarshal(fileReader);
    }

    public <T> void toFile(T object, File file) throws JAXBException
    {
        JAXBContext ctx = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.marshal(object, file);
    }
}
