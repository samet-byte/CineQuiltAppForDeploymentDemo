package com.sametb.cinequiltapp.config;

import com.sametb.cinequiltapp._custom.SamTextFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.sametb.cinequiltapp._custom.BusinessHelper.getServerIp;

/**
 * @author Samet Bayat.
 * Date: 3.12.2023 4:13 AM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@RestController
@RequestMapping("${base}")
@CrossOrigin(origins = {"https://cinequilt.netlify.app", "http://localhost:8888",  "https://sametb.com"})
public class ApiController {
    @GetMapping("${endpoint.api.ip}")
    public String serverIp() {
        return getServerIp();
    }
}
