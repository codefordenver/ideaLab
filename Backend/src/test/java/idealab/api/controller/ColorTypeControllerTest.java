package idealab.api.controller;

import static idealab.api.util.TestUtil.requestAsJsonString;
import static idealab.api.util.TestUtil.stringToGenericResponse;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import idealab.api.dto.request.ColorTypeUpdateRequest;
import idealab.api.dto.response.DataResponse;
import idealab.api.dto.response.GenericResponse;
import idealab.api.model.ColorType;
import idealab.api.model.Status;
import idealab.api.operations.ColorTypeOperations;


@RunWith(MockitoJUnitRunner.class)
@EnableAutoConfiguration
public class ColorTypeControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ColorTypeOperations operations;

    @InjectMocks
    private ColorTypeController controller;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller).build();
    }
    
    @Test
    public void getAllAvailableColors() throws Exception {
        // given
        ColorType color = new ColorType(1, "yellow");

        List<ColorType> colorList = Arrays.asList(color);

        DataResponse<ColorType> expectedResponse = new DataResponse<ColorType>();
        expectedResponse.setSuccess(true);
        expectedResponse.setMessage("Successfully returned print jobs by " + Status.ARCHIVED.getName() + " status");
        expectedResponse.setData(colorList);
        expectedResponse.setHttpStatus(HttpStatus.ACCEPTED);

        Mockito.when(operations.getActiveColors()).thenReturn(expectedResponse);

        // act
        String jsonString = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/colors/active")
                        .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isAccepted())
        .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        DataResponse<ColorType> actualResponse = mapper.readValue(jsonString, new TypeReference<DataResponse<ColorType>>() {});

        // assert
        verify(operations, times(1)).getActiveColors();
        assertEquals(actualResponse.getMessage(), expectedResponse.getMessage());
    }

    @Test
    public void updateColorTypeStatusSuccess() throws Exception {
        ColorTypeUpdateRequest request = new ColorTypeUpdateRequest();
        request.setAvailability(true);

        Integer colorId = 3;

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setSuccess(true);
        genericResponse.setMessage("Print Job Updated");
        genericResponse.setHttpStatus(HttpStatus.ACCEPTED);

        String inputJson = requestAsJsonString(request);

        when(operations.updateColorAvailability(colorId, request)).thenReturn(genericResponse);

        String returnJson = mockMvc.perform(put("/api/colors/3/availability")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn().getResponse().getContentAsString();

        GenericResponse returnedResponse = stringToGenericResponse(returnJson);
        assert(returnedResponse.equals(genericResponse));
    }
}
