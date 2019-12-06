package idealab.api.operations;

import idealab.api.dto.request.EmailMessageUpdateRequest;
import idealab.api.dto.response.EmailMessageResponse;
import idealab.api.exception.IdeaLabApiException;
import idealab.api.model.EmailMessage;
import idealab.api.model.Status;
import idealab.api.repositories.EmailMessageRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class EmailMessageOperationsTest {
    private EmailMessageOperations operations;

    @Mock
    private EmailMessageRepo repo;

    @Before
    public void setup() {
        operations = new EmailMessageOperations(
                repo
        );
    }

    @Test
    public void getMessageByStatus() throws Exception {
        // given
        String status = "COMPLETED";
        Status requestStatus = Status.fromValue(status);
        EmailMessage emailMessage = new EmailMessage(Status.fromValue("COMPLETED"), "EMAIL MESSAGE RESPONSE");

        // when
        when(repo.findEmailMessageByStatus(requestStatus)).thenReturn(emailMessage);
        EmailMessageResponse response = operations.getMessageByStatus("COMPLETED");

        // assert
        verify(repo, times(1)).findEmailMessageByStatus(requestStatus);
        assertEquals(response.getData().getEmailMessage(), emailMessage.getEmailMessage());
        assertEquals(response.getData().getStatus(), emailMessage.getStatus());
    }

    @Test(expected = IdeaLabApiException.class)
    public void getMessageByStatus_StatusNotValid() {
        // given
        String status = "COMPLETED";
        Status requestStatus = Status.fromValue(status);
        EmailMessage emailMessage = new EmailMessage(Status.fromValue("COMPLETED"), "EMAIL MESSAGE RESPONSE");

        // when
        when(repo.findEmailMessageByStatus(requestStatus)).thenReturn(emailMessage);
        EmailMessageResponse response = operations.getMessageByStatus("NOT_VALID_STATUS");
    }

    @Test(expected = IdeaLabApiException.class)
    public void getMessageByStatus_MessageNotFound() {
        // given
        String status = "COMPLETED";
        Status requestStatus = Status.fromValue(status);
        EmailMessage emailMessage = null;

        // when
        when(repo.findEmailMessageByStatus(requestStatus)).thenReturn(emailMessage);
        EmailMessageResponse response = operations.getMessageByStatus("COMPLETED");
    }

    @Test
    public void updateMessage() throws Exception {
        // given
        EmailMessageUpdateRequest request = new EmailMessageUpdateRequest();
        request.setStatus("COMPLETED");
        request.setEmailMessage("EMAIL MESSAGE RESPONSE");

        Status requestStatus = Status.fromValue(request.getStatus());
        EmailMessage emailMessage = new EmailMessage(requestStatus, request.getEmailMessage());
        emailMessage.setId(2);

        // when
        when(repo.findEmailMessageByStatus(requestStatus)).thenReturn(emailMessage);
        when(repo.save(emailMessage)).thenReturn(emailMessage);

        EmailMessageResponse response = operations.updateMessage(request);

        // assert
        verify(repo, times(1)).findEmailMessageByStatus(requestStatus);
        verify(repo, times(1)).save(emailMessage);
        assertEquals(response.getData().getEmailMessage(), emailMessage.getEmailMessage());
        assertEquals(response.getData().getStatus(), emailMessage.getStatus());
        assertEquals(response.getData().getId(), emailMessage.getId());
    }

    @Test(expected = IdeaLabApiException.class)
    public void updateMessage_MessageNotFound() {
        // given
        EmailMessageUpdateRequest request = new EmailMessageUpdateRequest();
        request.setStatus("COMPLETED");
        request.setEmailMessage("EMAIL MESSAGE RESPONSE");

        Status requestStatus = Status.fromValue(request.getStatus());
        EmailMessage emailMessage = null;

        // when
        when(repo.findEmailMessageByStatus(requestStatus)).thenReturn(emailMessage);
        when(repo.save(emailMessage)).thenReturn(emailMessage);

        EmailMessageResponse response = operations.updateMessage(request);
    }

}
