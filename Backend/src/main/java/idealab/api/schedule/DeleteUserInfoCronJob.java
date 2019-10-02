package idealab.api.schedule;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import idealab.api.model.CustomerInfo;
import idealab.api.repositories.CustomerInfoRepo;

@Component
public class DeleteUserInfoCronJob {

    private CustomerInfoRepo customerInfoRepo;

    private static final Integer NUM_DAYS_RETENTION = 30;

    public DeleteUserInfoCronJob(CustomerInfoRepo customerInfoRepo) {
        this.customerInfoRepo = customerInfoRepo;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteUserInfoCronJob.class);

    //Run everyday at 6am - Delete users older than 30 days
    @Scheduled(cron = "0 0 6 * * *")
    public void deleteUserInfo() {
        LOGGER.info("Running cron job to delete users");
        Iterable<CustomerInfo> customerInfoList = customerInfoRepo.findAll();
        
        if (customerInfoList != null) {
            for (CustomerInfo c : customerInfoList) {
                LocalDateTime d = c.getCreatedAt();
                
                if (d.isBefore(LocalDateTime.now().minusDays(NUM_DAYS_RETENTION))) {
                    customerInfoRepo.delete(c);
                }
            }
        } else {
            LOGGER.info("No users to delete");
        }
    }

}
