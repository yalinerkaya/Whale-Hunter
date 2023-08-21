package com.example.display.api;

import com.example.display.application.DisplayService;
import com.example.display.dto.DisplayDataResponse;
import com.example.global.util.DisplayConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class DisplayApi {

    private final DisplayService displayService;

    @GetMapping("/display")
    public ModelAndView display(ModelMap modelMap) {
        modelMap.addAttribute(DisplayConstants.DATA_ATTRIBUTE_NAME, DisplayConstants.DATA_API_URL);
        return new ModelAndView(DisplayConstants.VIEW_DISPLAY);
    }

    @GetMapping("/data")
    @ResponseBody
    public ResponseEntity<DisplayDataResponse> data() {
        Optional<DisplayDataResponse> dataOptional = displayService.buildDataset();
        return dataOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }
}
