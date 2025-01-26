package com.retail.experience.controller;

import com.retail.experience.exception.ApplicationProcessException;
import com.retail.experience.factory.ComputerComponentFactory;
import com.retail.experience.io.CsvLoader;
import com.retail.experience.io.Parser;
import com.retail.experience.model.Component;
import com.retail.experience.service.controller.LoaderControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LoaderController {

    private final LoaderControllerService loaderService;

    @Autowired
    public LoaderController(LoaderControllerService loaderService) {
        this.loaderService = loaderService;
    }

    @ResponseBody
    @GetMapping("/load/resources")
    public boolean loadCsvResources(@RequestParam String filepath) throws ApplicationProcessException {
        CsvLoader loader = new CsvLoader(filepath);
        Parser parser = new Parser(loader, new ComputerComponentFactory());
        List<Component> components = parser.parseComponents();
        if (components.isEmpty()) {
            return false;
        }
        return loaderService.loadComponents(components);
    }
}
