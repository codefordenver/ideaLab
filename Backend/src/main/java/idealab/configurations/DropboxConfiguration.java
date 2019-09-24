package idealab.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:dropbox.properties")
public class DropboxConfiguration {

    @Value("${dropbox.ACCESS_TOKEN}")
    private String accessToken;

    public String getAccessToken(){
        return accessToken;
    }

    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }
}
