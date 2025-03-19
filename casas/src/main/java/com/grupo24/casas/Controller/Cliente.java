package com.grupo24.casas.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class Cliente {

    @GetMapping("/check")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }

}
