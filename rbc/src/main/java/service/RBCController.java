package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RBCController {
    @Autowired
    private RBCService rbcService;

    @GetMapping("/rbc")
    public String weather() {
        return "Maximum Dollar of last 30 days " + rbcService.GetMaxRate();
    }
}