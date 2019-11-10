package idealab.api.operations;

import static ch.qos.logback.core.encoder.ByteArrayUtil.hexStringToByteArray;
import static idealab.api.exception.ErrorType.DROPBOX_UPLOAD_FILE_ERROR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import idealab.api.dto.request.PrintJobDeleteRequest;
import idealab.api.dto.request.PrintJobNewRequest;
import idealab.api.dto.request.PrintJobUpdateRequest;
import idealab.api.dto.request.PrintModelUpdateRequest;
import idealab.api.dto.request.UpdatePrintJobPropertiesRequest;
import idealab.api.dto.response.DataResponse;
import idealab.api.dto.response.GenericResponse;
import idealab.api.exception.IdeaLabApiException;
import idealab.api.model.ColorType;
import idealab.api.model.CustomerInfo;
import idealab.api.model.Employee;
import idealab.api.model.PrintJob;
import idealab.api.model.Queue;
import idealab.api.model.Status;
import idealab.api.repositories.ColorTypeRepo;
import idealab.api.repositories.CustomerInfoRepo;
import idealab.api.repositories.EmployeeRepo;
import idealab.api.repositories.PrintJobRepo;
import idealab.api.service.EmailHashUtil;
import idealab.api.service.FileService;

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
