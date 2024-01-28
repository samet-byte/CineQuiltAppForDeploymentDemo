package com.sametb.cinequiltapp.config;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Samet Bayat.
 * Date: 28.01.2024 8:49 PM
 * Project Name: CineQuiltApp
 * ©2024, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@RestController
@RequestMapping("api/v1")
public class WakeUpController {

    @GetMapping("/wake-up")
    public ResponseEntity<?> wakeUp() {
        return ResponseEntity.ok().build();
    }
}
