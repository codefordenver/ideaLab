package idealab.api.operations;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.springframework.stereotype.Service;


@Service
public class PropertyOperations {

    public static final String DROPBOX_FILENAME = "dropbox.properties";

    public void updateDropboxToken(String token) throws Exception {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties().setFileName(DROPBOX_FILENAME));
        Configuration config = builder.getConfiguration();
        config.setProperty("dropbox.ACCESS_TOKEN", token);
        builder.save();
    }
}
