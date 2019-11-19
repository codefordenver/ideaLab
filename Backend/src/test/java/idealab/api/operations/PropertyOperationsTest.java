package idealab.api.operations;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class PropertyOperationsTest {

    PropertyOperations propertyOperations;

    @Before
    public void before() {
        propertyOperations = new PropertyOperations();
    }

    @Test
    public void test() throws Exception {
        propertyOperations.updateDropboxToken("RANDOM");
    }

}
