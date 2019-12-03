package idealab.api.schedule;

import idealab.api.model.CustomerInfo;
import idealab.api.model.PrintJob;
import idealab.api.repositories.CustomerInfoRepo;
import idealab.api.repositories.PrintJobRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class DeleteUserInfoCronJob {

    private CustomerInfoRepo customerInfoRepo;
    private PrintJobRepo printJobRepo;

    private static final Integer NUM_DAYS_RETENTION = 30;

    public DeleteUserInfoCronJob(CustomerInfoRepo customerInfoRepo, PrintJobRepo printJobRepo) {
        this.customerInfoRepo = customerInfoRepo;
        this.printJobRepo = printJobRepo;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteUserInfoCronJob.class);

    //For each customer, check the created date
    // if older than 30 days, check to see if most recent printjob is also over 30 days
    // if yes, delete the user and associated print jobs

    //Run everyday at 6am - Delete users older than 30 days
    @Scheduled(cron = "0 0 6 * * *")
    public void deleteUserInfo() {
        LOGGER.info("Running cron job to delete users");
        Iterable<CustomerInfo> customerInfoList = customerInfoRepo.findAll();
        List<CustomerInfoCronJobInfo> customersToDelete = new ArrayList<>();

        if (customerInfoList == null) {
            LOGGER.info("No Customers to Delete");
            return;
        }

        customerInfoList.forEach(c -> {
            customersToDelete.add(new CustomerInfoCronJobInfo(c));
        });

        LocalDateTime now = LocalDateTime.now();
        for (CustomerInfoCronJobInfo c : customersToDelete) {
            if (c.getCreatedAt().plus(NUM_DAYS_RETENTION, ChronoUnit.DAYS).isBefore(now)) {
                List<PrintJob> pJobs = printJobRepo.findPrintJobsByCustomerIdNewestFirst(c.getId());
                if (pJobs != null && pJobs.size() >= 1) {
                    PrintJob p = pJobs.get(0);
                    if (p != null) {
                        if (p.getCreatedAt().plus(NUM_DAYS_RETENTION, ChronoUnit.DAYS).isBefore(now)) {
                            printJobRepo.deleteAll(pJobs);
                            customerInfoRepo.deleteById(c.getId());
                        }
                    }
                }
            }
        }
    }

}
