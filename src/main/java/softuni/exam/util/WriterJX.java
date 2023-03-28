package softuni.exam.util;

import com.google.gson.Gson;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;



public class WriterJX {

    private static Gson gson;

    public WriterJX(Gson gson) {
        this.gson = gson;
    }

    public static <T> void writeJsonIntoFile(List<T> objects, Path filePath) throws IOException {
        final FileWriter fileWriter = new FileWriter(filePath.toFile());

        gson.toJson(objects, fileWriter);

        fileWriter.flush();
        fileWriter.close();
    }

    public static <T> void writeXmlIntoFile(T data, Path filePath) throws JAXBException {
        final File file = filePath.toFile();

        final JAXBContext context = JAXBContext.newInstance(data.getClass());
        final Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        marshaller.marshal(data, file);
    }

    public static <T> void writeJsonIntoFile(T object, Path filePath) throws IOException {
        final FileWriter fileWriter = new FileWriter(filePath.toFile());

        gson.toJson(object, fileWriter);

        fileWriter.flush();
        fileWriter.close();
    }
}

