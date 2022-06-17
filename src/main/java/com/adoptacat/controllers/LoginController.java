package com.adoptacat.controllers;

import com.adoptacat.model.Gato;
import com.adoptacat.service.GatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
import java.util.List;


@Controller
@SessionAttributes("user")
public class LoginController {

    @Autowired
    private GatoService gatoService;

    @GetMapping(value = {"/login", "/", ""})
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model, Principal principal, RedirectAttributes flash) {

        if (principal != null) {
            flash.addFlashAttribute("info", "Ya has iniciado sesión");
            return "redirect:/index";
        }

        if (error != null) {
            model.addAttribute("error", "La combinación de usuario y contraseña no es correcta");
        }

        if (logout != null) {
            model.addAttribute("success", "Has cerrado sesión correctamente");
        }

        return "login";
    }

    @GetMapping("/index")
    public String welcomePage(ModelMap modelMap) {

        List<Gato> gatos = gatoService.findGatoByEstatusVisible(true);

        modelMap.addAttribute("gatos", gatos);

        return "index";
    }

}
