package idealab.api.operations;

import idealab.api.dto.request.UserCsvUploadRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.model.Employee;
import idealab.api.model.Status;
import idealab.api.repositories.EmployeeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UploadOperations {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadOperations.class);

    private final EmployeeRepo employeeRepo;

    public UploadOperations(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public GenericResponse processUserCsvUpload(MultipartFile file) {
        GenericResponse response = new GenericResponse();

        if(file.isEmpty()) {
            response.setSuccess(false);
            response.setMessage("File was empty");
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            return response;
        }

        List<UserCsvUploadRequest> requests = new ArrayList<>();
        try {
            byte[] bytes = file.getBytes();
            ByteArrayInputStream inputFilestream = new ByteArrayInputStream(bytes);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFilestream));
            String line = "";
            List<String> items;
            while ((line = br.readLine()) != null) {
                items = Arrays.asList(line.split("\\s*,\\s*"));
                UserCsvUploadRequest request = new UserCsvUploadRequest();
                request.setFirstName(items.get(0));
                request.setLastName(items.get(1));
                request.setEmail(items.get(2));
                request.setColor(items.get(3));
                request.setEmployeeId(Integer.valueOf(items.get(4)));
                request.setStatus(items.get(5));

                try {
                    Employee e = employeeRepo.findEmployeeById(request.getEmployeeId());
                    if(e == null) {
                        LOGGER.error("CSV line not processed: Employee not valid\n" + line);
                        continue;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("CSV line not processed: Error getting Employee\n" + line);
                    continue;
                }

                Status status = Status.valueOf(request.getStatus());
                if(status == null) {
                    LOGGER.error("CSV line not processed: Status not valid\n" + line);
                    continue;
                }

                requests.add(request);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setMessage("Upload Failed");
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            return response;
        }
        response.setSuccess(true);
        response.setMessage("Upload Successful");
        response.setHttpStatus(HttpStatus.ACCEPTED);
        return response;
    }

}
