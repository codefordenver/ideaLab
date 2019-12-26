package idealab.api.operations;

import idealab.api.dto.response.GenericResponse;
import idealab.api.exception.ErrorType;
import idealab.api.exception.IdeaLabApiException;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.el.PropertyNotFoundException;

import static idealab.api.exception.ErrorType.VALIDATION_ERROR;


@Service
public class PropertyOperations {

    public static final String DROPBOX_FILENAME = "dropbox.properties";
    private static final String PROPERTY_NAME = "dropbox.ACCESS_TOKEN";

    public String dropboxFilePath;

    @Autowired
    public PropertyOperations(@Value("${dropbox.ABSOLUTE_FILE_PATH}") String dropboxFilePath) {
        this.dropboxFilePath = dropboxFilePath;
    }

    public GenericResponse updateDropboxToken(String token) {
        return updateToken(token, PROPERTY_NAME, dropboxFilePath, DROPBOX_FILENAME);
    }

    // NOTE: This test will NOT actually save over the test properties file. That's because it requires an
    // absolute path which will be different per machine. However, this does run through the logic so
    // it's important to keep this.
    public GenericResponse testUpdateDropboxToken(String token, String propertyName, String filePath) {
        return updateToken(token, propertyName, filePath, "");
    }

    private GenericResponse updateToken(String token, String propertyName, String filePath, String fileName) {
        if(token == null || token.isEmpty())
            throw new IdeaLabApiException(VALIDATION_ERROR, "Token is null or blank");

        if(dropboxFilePath == null)
            throw new PropertyNotFoundException("Dropbox file path is null");

        PropertiesConfiguration config = null;
        try {
            config = new PropertiesConfiguration(filePath + fileName);
            config.setProperty(propertyName, token);
            config.save();
        } catch (ConfigurationException e) {
            throw new IdeaLabApiException(ErrorType.GENERAL_ERROR, "Dropbox token not updated: " + e.getMessage());
        }

        return new GenericResponse(true, "Token updated successfully", HttpStatus.ACCEPTED);
    }
}
