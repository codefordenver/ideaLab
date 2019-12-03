package idealab.api.operations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import idealab.api.dto.response.DataResponse;
import idealab.api.dto.response.GenericResponse;
import idealab.api.exception.IdeaLabApiException;
import idealab.api.model.ColorType;
import idealab.api.model.Employee;
import idealab.api.repositories.ColorTypeRepo;

@RunWith(SpringJUnit4ClassRunner.class)
public class ColorTypeOperationsTest {
    private ColorTypeOperations operations;

    @Mock
    ColorTypeRepo colorTypeRepo;

    @Before
    public void setup() {
        operations = new ColorTypeOperations(colorTypeRepo);
    }
    
    @Test
    public void getAllAvailableColors() throws Exception {
        // given
        ColorType color = new ColorType(1, "yellow");

        List<ColorType> colorList = Arrays.asList(color);

        when(colorTypeRepo.findByAvailable(true)).thenReturn(colorList);
        
        // when
        DataResponse<ColorType> result = operations.getActiveColors();
        
        // assert
        verify(colorTypeRepo, times(1)).findByAvailable(true);
        assertEquals(result.getData().get(0).getId(), color.getId());
    }

    @Test(expected = IdeaLabApiException.class)
    public void getActiveColors_shouldThrow_IdeaLabApiException_when_colors_dont_exist() {
        // given
        when(colorTypeRepo.findByAvailable(true)).thenReturn(null);

        // when
        operations.getActiveColors();
    }
}
