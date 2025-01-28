package com.ids.progettoids;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class DefaultController 
{
    @RequestMapping("/Ciao")    
    public String Ciao()
    {
        return "ciao";
    }
}
