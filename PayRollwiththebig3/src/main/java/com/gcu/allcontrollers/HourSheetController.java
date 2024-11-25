package com.gcu.allcontrollers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcu.business.HourSheetBusinessService;
import com.gcu.model.HourSheet;

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
    public String addHourSheet(
    @RequestParam int userId,
    @RequestParam int hoursWorked,
    @RequestParam boolean isApproved) 
    {

        // Create a new HourSheet object with current date
        HourSheet newHourSheet = new HourSheet(userId, hoursWorked, new Date(), isApproved);

        // Add the hour sheet using the service
        hourSheetService.addHourSheet(newHourSheet);

        // Redirect back to the hoursheet page to display updated data
        return "redirect:/hoursheet";
    }

    // Add method to delete a timesheet by userId
    @PostMapping("/deleteHoursheet")
    public String deleteHourSheet(@RequestParam int timeSheetId) {
        // Call the delete method from the business service
        hourSheetService.removeHourSheet(timeSheetId);

        // Redirect back to the hoursheet page to display updated data
        return "redirect:/hoursheet";
    }

    // Add method to edit hours worked for a specific timesheet
    @PostMapping("/editHoursheet")
    public String editHourSheet(
        @RequestParam int timeSheetId,
        @RequestParam int newHours
    ) {
        // Call the edit method from the business service
        hourSheetService.editTimeSheet(timeSheetId, newHours);

        // Redirect back to the hoursheet page to display updated data
        return "redirect:/hoursheet";
    }
}
