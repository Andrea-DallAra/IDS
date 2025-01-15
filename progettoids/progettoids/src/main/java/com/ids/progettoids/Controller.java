package com.ids.progettoids;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
public class Controller 
{
    @RequestMapping("/Ciao")    
    public String Ciao()
    {
        return "ciao";
    }
}
