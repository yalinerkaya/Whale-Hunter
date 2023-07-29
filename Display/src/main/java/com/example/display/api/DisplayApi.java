package com.example.display.api;

import com.example.display.application.DisplayService;
import com.example.display.dto.DisplayDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class DisplayApi {

    private static final String DATA_API_URL = "/data";
    private static final String VIEW_DISPLAY = "display";

    private final DisplayService displayService;

    @Autowired
    public DisplayApi(DisplayService displayService) {
        this.displayService = displayService;
    }

    @GetMapping("/display")
    public ModelAndView display(ModelMap modelMap) {
        modelMap.addAttribute("data_api_url", DATA_API_URL);
        return new ModelAndView(VIEW_DISPLAY);
    }

    @GetMapping("/data")
    @ResponseBody
    public ResponseEntity<DisplayDataResponse> data() {
        Optional<DisplayDataResponse> dataOptional = displayService.buildDataset();
        return dataOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }
}
