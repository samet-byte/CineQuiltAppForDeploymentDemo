package com.sametb.cinequiltapp.config;

import com.sametb.cinequiltapp._custom.SamTextFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Samet Bayat.
 * Date: 3.12.2023 4:13 AM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:3000")
public class ApiController {
    @GetMapping("/ip")
    public String getServerIp() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            SamTextFormat.Companion.errorMessage("Error getting IP address: " + e.getMessage());
            return "Unable to retrieve IP address";
        }
    }
}
