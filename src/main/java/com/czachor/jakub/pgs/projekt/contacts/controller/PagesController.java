package com.czachor.jakub.pgs.projekt.contacts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Jakub Czachor
 * PagesController is used to return filepath to *.html files.
 */

@Controller
public class PagesController {
    @RequestMapping(value = "/")
    public String getIndexPage(){
        return "index";
    }

    @RequestMapping(value = "/allcontacts")
    public String getAllContactsPage(){
        return "contacts/allcontacts";
    }
}
