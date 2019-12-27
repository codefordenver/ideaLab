package idealab.api.operations;

import static idealab.api.exception.ErrorType.VALIDATION_ERROR;

import java.io.File;

import javax.el.PropertyNotFoundException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import idealab.api.dto.response.GenericResponse;
import idealab.api.exception.ErrorType;
import idealab.api.exception.IdeaLabApiException;

@Service
public class PropertyOperations {

    private static final String DROPBOX_PROPERTY_NAME = "dropbox.ACCESS_TOKEN";
    private static final String EMAIL_USERNAME_PROPERTY_NAME = "spring.mail.username";
    private static final String EMAIL_PW_PROPERTY_NAME = "spring.mail.password";

    private File propertiesFilePath = new File(
            getClass().getClassLoader().getResource("application.properties").getFile());

    public PropertyOperations() {
    }

    /**
     * This updates the dropbox token.
     * 
     * @param token
     * @return
     */
    public GenericResponse updateDropboxToken(String token) {
        String errorMessage = "Dropbox token not updated: ";
        String successMessage = "Dropbox token updated successfully";
        return updateAnApplicationProperty(token, DROPBOX_PROPERTY_NAME, errorMessage, successMessage);
    }

    // NOTE: This test will NOT actually save over the test properties file. That's
    // because it requires an
    // absolute path which will be different per machine. However, this does run
    // through the logic so
    // it's important to keep this.
    public GenericResponse testUpdateDropboxToken(String token, String propertyName) {
        return updateAnApplicationProperty(token, propertyName, "Fake error", "Fake success");
    }

    @SuppressWarnings("unused")
    /**
     * Updates the email and password for the gmail account.
     * 
     * @param email
     * @param password
     * @return
     */
    public GenericResponse updateEmailInfo(String email, String password) {
        String emailAddressErrorMessage = "Email address not updated: ";
        String emailPWErrorMessage = "Email password not updated: ";

        GenericResponse usernameSuccess = updateAnApplicationProperty(email, EMAIL_USERNAME_PROPERTY_NAME, emailAddressErrorMessage,
                "");
        GenericResponse passwordSuccess = updateAnApplicationProperty(password, EMAIL_PW_PROPERTY_NAME, emailPWErrorMessage, "");

        return new GenericResponse(true, "Email and password successfully updated.", HttpStatus.ACCEPTED);
    }

    /**
     * Allows you to update a property in the applications.properties file
     * 
     * @param newPropertyValue - the new value of the property
     * @param propertyName     - the application.properties property name
     * @param errorMessage     - the error message you want returned
     * @param successMessage   - the success message you want returned
     * @return
     */
    private GenericResponse updateAnApplicationProperty(String newPropertyValue, String propertyName,
            String errorMessage, String successMessage) {
        if (newPropertyValue == null || newPropertyValue.isEmpty())
            throw new IdeaLabApiException(VALIDATION_ERROR, "Token is null or blank");
        
        if (propertiesFilePath == null)
            throw new PropertyNotFoundException("Application properties file path is null");

        PropertiesConfiguration config = null;
        try {
            config = new PropertiesConfiguration(propertiesFilePath);
            config.setProperty(propertyName, newPropertyValue);
            config.save();
        } catch (ConfigurationException e) {
            throw new IdeaLabApiException(ErrorType.GENERAL_ERROR, errorMessage + e.getMessage());
        }

        return new GenericResponse(true, successMessage, HttpStatus.ACCEPTED);
    }
}
