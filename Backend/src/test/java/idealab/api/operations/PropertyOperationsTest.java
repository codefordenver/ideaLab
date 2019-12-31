package idealab.api.operations;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations="classpath:test_application.properties")
public class PropertyOperationsTest {

    @Value("${dropbox.TEST_ABSOLUTE_FILE_PATH}")
    private String filePath;

    PropertyOperations propertyOperations;

    @Before
    public void before() {
        propertyOperations = new PropertyOperations();
    }

    @Test
    public void updateDropboxToken() throws Exception {
        propertyOperations.testUpdateDropboxToken("RANDOM", "dropbox.ACCESS_TOKEN");
    }

}
