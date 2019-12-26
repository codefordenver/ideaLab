package idealab.api.operations;

import static idealab.api.exception.ErrorType.VALIDATION_ERROR;

import java.io.File;

import javax.el.PropertyNotFoundException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import idealab.api.dto.response.GenericResponse;
import idealab.api.exception.ErrorType;
import idealab.api.exception.IdeaLabApiException;


@Service
public class PropertyOperations {
    
    private static final String DROPBOX_PROPERTY_NAME = "dropbox.ACCESS_TOKEN";

    private File propertiesFilePath = new File(getClass().getClassLoader().getResource("application.properties").getFile());

    public PropertyOperations() {
    }

    /**
     * This updates the dropbox token.
     * @param token
     * @return
     */
    public GenericResponse updateDropboxToken(String token) {
        String errorMessage = "Dropbox token not updated: ";
        boolean didItWork = updateToken(token, DROPBOX_PROPERTY_NAME, errorMessage);

        // This will always return true or else it will throw an exception
        return new GenericResponse(didItWork, "Token updated successfully", HttpStatus.ACCEPTED);
    }

    // NOTE: This test will NOT actually save over the test properties file. That's because it requires an
    // absolute path which will be different per machine. However, this does run through the logic so
    // it's important to keep this.
    public GenericResponse testUpdateDropboxToken(String token, String propertyName) {
        boolean didItWork = updateToken(token, propertyName, "Fake error");
        if (didItWork) {
            return new GenericResponse(true, "Token updated successfully", HttpStatus.ACCEPTED);
        } else {
            return new GenericResponse();
        }
    }

    private boolean updateToken(String newPropertyValue, String propertyName, String errorMessage) {
        if(newPropertyValue == null || newPropertyValue.isEmpty())
            throw new IdeaLabApiException(VALIDATION_ERROR, "Token is null or blank");

        if(propertiesFilePath == null)
            throw new PropertyNotFoundException("Application properties file path is null");

        PropertiesConfiguration config = null;
        try {
            config = new PropertiesConfiguration(propertiesFilePath);
            config.setProperty(propertyName, newPropertyValue);
            config.save();
        } catch (ConfigurationException e) {
            throw new IdeaLabApiException(ErrorType.GENERAL_ERROR, errorMessage + e.getMessage());
        }

        return true;
    }
}
