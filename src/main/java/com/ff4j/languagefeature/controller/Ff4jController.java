package com.ff4j.languagefeature.controller;


import com.ff4j.languagefeature.configuration.Ff4jConfig;
import lombok.extern.slf4j.Slf4j;
import org.ff4j.FF4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class Ff4jController {

    @Autowired
    private FF4j ff4j;

    @GetMapping("/web-console")
    public String home(Model model) {
        model.addAttribute("featureEnabled", ff4j.check(Ff4jConfig.FRENCH_LANG));
        return "index";
    }

    @PostMapping("/toggle")
    public String toggleFeature(@RequestParam(name = "toggle", required = false) Boolean toggle) {

        if(ff4j.check(Ff4jConfig.FRENCH_LANG)) {
            ff4j.disable(Ff4jConfig.FRENCH_LANG);
        }else {
            ff4j.enable(Ff4jConfig.FRENCH_LANG);
        }
        log.info(Ff4jConfig.FRENCH_LANG);

        return "redirect:/";
    }
}
