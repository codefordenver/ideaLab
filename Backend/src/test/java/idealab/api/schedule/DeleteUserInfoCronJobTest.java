package idealab.api.schedule;

import idealab.api.model.CustomerInfo;
import idealab.api.model.PrintJob;
import idealab.api.repositories.CustomerInfoRepo;
import idealab.api.repositories.PrintJobRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class DeleteUserInfoCronJobTest {

    @Mock
    CustomerInfoRepo customerInfoRepo;

    @Mock
    PrintJobRepo printJobRepo;

    @InjectMocks
    DeleteUserInfoCronJob cronJob;

    @Before
    public void setup() {
        cronJob = new DeleteUserInfoCronJob(customerInfoRepo, printJobRepo);
    }

    @Test
    public void deleteUserInfo() {
        CustomerInfo ci = new CustomerInfo();
        ci.setCreatedAt(LocalDateTime.of(2019, Month.JANUARY, 1, 0, 0));
        
        List<CustomerInfo> customerInfoList = new ArrayList<>();
        customerInfoList.add(ci);

        PrintJob p = new PrintJob();
        p.setCreatedAt(LocalDateTime.of(2019, Month.JANUARY, 1, 0, 0));
        List<PrintJob> pJobs = new ArrayList<>();

        pJobs.add(p);

        when(customerInfoRepo.findAll()).thenReturn(customerInfoList);
        when(printJobRepo.findPrintJobsByCustomerIdNewestFirst(anyInt())).thenReturn(pJobs);
        doNothing().when(customerInfoRepo).deleteById(anyInt());
        doNothing().when(printJobRepo).deleteAll(any());

        cronJob.deleteUserInfo();
    }

    @Test
    public void deleteUserInfoNoOlderUsers() {
        List<CustomerInfo> customerInfoList = new ArrayList<>();
        CustomerInfo ci = new CustomerInfo();
        ci.setCreatedAt(LocalDateTime.now());
        customerInfoList.add(ci);

        when(customerInfoRepo.findAll()).thenReturn(customerInfoList);

        cronJob.deleteUserInfo();
    }

    @Test
    public void deleteUserInfoNoUsers() {
        when(customerInfoRepo.findAll()).thenReturn(null);
        doNothing().when(customerInfoRepo).deleteById(anyInt());
        doNothing().when(printJobRepo).deleteAll(any());

        cronJob.deleteUserInfo();
    }


}
