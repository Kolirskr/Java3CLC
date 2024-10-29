package com.gcu.allcontrollers;

import com.gcu.business.HourSheetBusinessService;
import com.gcu.model.HourSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HourSheetController 
{

    @Autowired
    private HourSheetBusinessService hourSheetService;

    @GetMapping("/hoursheet")
    public String getHourSheetPage(Model model) 
    {
        // Retrieve all hour sheets from the service
        List<HourSheet> hourSheets = hourSheetService.getAllHourSheets();

        // Add the list to the model
        model.addAttribute("hourSheets", hourSheets);

        // Return the name of the HTML template (without extension)
        return "hoursheet";
    }

    @PostMapping("/addHoursheet")
    public String addHourSheet(@RequestParam String employeeName, @RequestParam int hoursWorked) 
    {
        // Create a new HourSheet object
        HourSheet newHourSheet = new HourSheet(employeeName, hoursWorked);

        // Add the hour sheet using the service
        hourSheetService.addHourSheet(newHourSheet);

        // Redirect back to the hoursheet page to display updated data
        return "redirect:/hoursheet";
    }
}
