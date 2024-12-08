package com.gcu.allcontrollers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcu.business.HourSheetBusinessService;
import com.gcu.model.HourSheet;

@RestController
@RequestMapping("/api/hoursheets")
public class HourSheetAPIController 
{
    @Autowired
    private HourSheetBusinessService hourSheetService;

    // API Methods -------------------------------
    // get all hour sheets
    @GetMapping
    public List<HourSheet> getAllHourSheetsAPI()
    {
        return hourSheetService.getAllHourSheetsAPI();
    }

    // get hour sheet by id
    @GetMapping("/{id}")
    public ResponseEntity<HourSheet> getHourSheetByIdAPI(@PathVariable int id)
    {
        
        Optional<HourSheet> hourSheet = hourSheetService.getHourSheetByIdAPI(id);
        return hourSheet.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // -------------------------------------------
}
