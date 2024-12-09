package com.gcu.allcontrollers;

import com.gcu.business.HourSheetBusinessService;
import com.gcu.model.HourSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hoursheets")
public class HourSheetAPIController 
{

    private final HourSheetBusinessService hourSheetService;

    @Autowired
    public HourSheetAPIController(HourSheetBusinessService hourSheetService) 
    {
        this.hourSheetService = hourSheetService;
    }

    // Get all hour sheets
    @GetMapping
    public ResponseEntity<List<HourSheet>> getAllHourSheetsAPI() 
    {
        return ResponseEntity.ok(hourSheetService.getAllHourSheetsAPI());
    }

    // Get hour sheet by ID
    @GetMapping("/{id}")
    public ResponseEntity<HourSheet> getHourSheetByIdAPI(@PathVariable int id) 
    {
        Optional<HourSheet> hourSheet = hourSheetService.getHourSheetByIdAPI(id);
        return hourSheet.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    // Add a new hour sheet
    @PostMapping
    public ResponseEntity<HourSheet> addHourSheet(@RequestBody HourSheet hourSheet) 
    {
        hourSheetService.addHourSheet(hourSheet);
        return ResponseEntity.ok(hourSheet);
    }

    // Edit hours worked for a specific hour sheet
    @PutMapping("/{id}")
    public ResponseEntity<?> editHourSheet(@PathVariable int id, @RequestParam int newHours) 
    {
        hourSheetService.editTimeSheet(id, newHours);
        return ResponseEntity.ok().build();
    }

    // Delete hour sheet by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHourSheet(@PathVariable int id) 
    {
        hourSheetService.removeHourSheet(id);
        return ResponseEntity.noContent().build();
    }
}
