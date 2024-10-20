package com.gcu.allcontrollers;

import com.gcu.business.HourSheetBusinessInterface;
import com.gcu.model.HourSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HourSheetController 
{

    @Autowired
    private HourSheetBusinessInterface hourSheetService;

    // Display the hour sheets
    @GetMapping("/hoursheets")
    public String displayHourSheets(Model model) {
        List<HourSheet> hourSheets = hourSheetService.getAllHourSheets();
        model.addAttribute("hourSheets", hourSheets);
        return "hoursheet"; // Ensure this matches the template name exactly
    }

    // Handle form submission to add a new hour sheet
    @PostMapping("/addHoursheet")
    public String addHourSheet(@RequestParam String employeeName, @RequestParam int hoursWorked) {
        HourSheet newSheet = new HourSheet(employeeName, hoursWorked);
        hourSheetService.addHourSheet(newSheet); // Add the new hour sheet
        return "redirect:/hoursheets"; // Redirect to display the updated list
    }
}
