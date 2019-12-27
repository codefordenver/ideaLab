package idealab.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import idealab.api.dto.response.AuditPrintJobColorCountResponse;
import idealab.api.dto.response.PrintJobAuditResponse;
import idealab.api.operations.AuditOperations;

@RestController
@RequestMapping("/api/audit")
public class AuditController {
    private final AuditOperations auditOperations;

    public AuditController(AuditOperations auditOperations) {
        this.auditOperations = auditOperations;
    }

    @GetMapping("/print-jobs")
    public ResponseEntity<?> getAllPrintJobsAuditTable() {
        PrintJobAuditResponse response = auditOperations.allPrintJobsAudit();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/print-jobs/{print-id}")
    public ResponseEntity<?> getPrintJobAuditTableGetById(@PathVariable("print-id") Integer printId) {
        PrintJobAuditResponse response = auditOperations.printJobAuditById(printId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/print-jobs/year-colors")
    public ResponseEntity<?> getPrintJobAuditGroupedByColorForPassedYear() {
        AuditPrintJobColorCountResponse response = auditOperations.printJobAuditColorsYearly();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}
