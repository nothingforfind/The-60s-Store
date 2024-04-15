package org.example.the60sstore.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.the60sstore.Service.CustomerService;
import org.example.the60sstore.Service.EmailSenderService;
import org.example.the60sstore.Service.LanguageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {

    private final CustomerService customerService;
    private final LanguageService languageService;
    private final EmailSenderService emailSenderService;

    public ContactController(CustomerService customerService, LanguageService languageService, EmailSenderService emailSenderService) {
        this.customerService = customerService;
        this.languageService = languageService;
        this.emailSenderService = emailSenderService;
    }

    @GetMapping({"/contact"})
    public String toContact(HttpSession session, HttpServletRequest request, Model model) {
        customerService.addLogged(session, model);
        languageService.addLanguagle(request, model);
        return "store-contact";
    }


    @PostMapping({"/sent-message"})
    public String sentMessage(@RequestParam String name, @RequestParam String email,
                              @RequestParam String subject, @RequestParam String message,
                              HttpSession session, Model model) {

        emailSenderService.sendEmail("longlhfx02906@funix.edu.vn", "CUSTOMER MAIL: " + email,
                "SUBJECT: " + subject + "\nCUSTOMER NAME: " + name +
                        "\nCONTENT: " + message);
        customerService.addLogged(session, model);
        model.addAttribute("sent", "success");

        return "store-home";
    }
}