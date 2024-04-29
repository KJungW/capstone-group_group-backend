package capstone.letcomplete.group_group.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
@Tag(name="HealthChecking", description = "HealthChecking 관련 API")
public class HealthCheckingController {
    @GetMapping
    @Operation(summary = "Check Health", description = "health checking 수행")
    public String healthCheck() {
        return "ok";
    }
}
