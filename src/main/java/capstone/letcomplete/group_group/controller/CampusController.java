package capstone.letcomplete.group_group.controller;

import capstone.letcomplete.group_group.dto.output.SaveCampusOutput;
import capstone.letcomplete.group_group.service.CampusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/campus")
@RequiredArgsConstructor
public class CampusController {
    private final CampusService campusService;

    @PostMapping()
    public SaveCampusOutput save(@RequestParam(required = true) String campusName){
        return new SaveCampusOutput(campusService.save(campusName));
    }

}
