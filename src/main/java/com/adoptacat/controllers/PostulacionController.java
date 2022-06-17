package com.adoptacat.controllers;

import com.adoptacat.constrains.ConstraintsSearch;
import com.adoptacat.model.DeliveryAppointment;
import com.adoptacat.model.Gato;
import com.adoptacat.model.Postulacion;
import com.adoptacat.model.User;
import com.adoptacat.service.EmailService;
import com.adoptacat.service.GatoService;
import com.adoptacat.service.PostulacionService;
import com.adoptacat.service.UserService;
import com.adoptacat.validator.ApplicationValidator;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@SessionAttributes("postulacion")
public class PostulacionController {

    @Autowired
    private GatoService gatoService;

    @Autowired
    private PostulacionService postulacionService;

    @Autowired
    private ApplicationValidator applicationValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;


    @RequestMapping("/showPostulaciones/{idCat}")
    public String showPostulaciones(@PathVariable(value = "idCat") long id, ModelMap modelMap) {

        Gato gato = gatoService.findOne(id);

        List<Postulacion> postulaciones = postulacionService.getPostulacionesByGato(gato);
        modelMap.addAttribute("postulaciones", postulaciones);


        List<String> comboValues = new ArrayList<>();
        comboValues.add("Todos");
        comboValues.add("Sí");
        comboValues.add("No");

        modelMap.addAttribute("comboValues", comboValues);


        ConstraintsSearch constraintsSearch = new ConstraintsSearch();
        constraintsSearch.setGatoId(id);
        modelMap.addAttribute("constraintsSearch", constraintsSearch);


        DeliveryAppointment appointment = new DeliveryAppointment();
        modelMap.addAttribute("deliveryAppointment", appointment);


        return "postulaciones";
    }

    @RequestMapping("/createApplication/{idCat}")
    public String createApplication(@PathVariable(value = "idCat") long id, Model model,
                                    Principal principal, RedirectAttributes flash) {

        Postulacion createPostulacion = new Postulacion();
        Gato gato = gatoService.findOne(id);

        if (gato.getOwnerName().equals(principal.getName())) {
            flash.addFlashAttribute("error", "Lo sentimos, no puede postularse a su propio anuncio");
            return "redirect:/index";
        }

        createPostulacion.setGato(gato);

        User user = userService.findByUsername(principal.getName());
        createPostulacion.setIdUsuario(user.getId());
        createPostulacion.setNombreUsuario(user.getName());
        createPostulacion.setUsuarioEmail(user.getUserName());


        model.addAttribute("postulacion", createPostulacion);
        model.addAttribute("titulo", "Postulación para: " + gato.getNombre());
        return "crear-postulacion";
    }

    @RequestMapping(value = "/createApplication", method = RequestMethod.POST)
    public String createApplication(@Valid Postulacion postulacion, BindingResult bindingResult,
                                    Model model, RedirectAttributes flash) {

        applicationValidator.validate(postulacion, bindingResult);

        if (bindingResult.hasErrors()) {
            return "crear-postulacion";
        }

        Gato gato = gatoService.fetchByIdWithPostulaciones(postulacion.getGato().getId());
        postulacion.setGato(gato);
        postulacion.setFechaPostulacion(new Date());
        postulacion.setEstatus(false);
        postulacionService.savePostulacion(postulacion);

        flash.addFlashAttribute("success", "Se ha registrado su postulación, le notificaremos muy pronto");

        return "redirect:/index";
    }

    @RequestMapping("/showPostulacionesByPreviousCats")
    public String showApplicantsByPreviousCats(ConstraintsSearch constraintsSearch, ModelMap modelMap) {

        Gato gato = gatoService.findOne(constraintsSearch.getGatoId());

        List<Postulacion> postulaciones;

        if (constraintsSearch.getSelectedValue().equals("*")) {
            postulaciones = postulacionService.getPostulacionesByGato(gato);
        } else {
            postulaciones = postulacionService.findPostulacionesByGatoAndTuvoGato(gato, constraintsSearch.getSelectedValue());
        }

        modelMap.addAttribute("postulaciones", postulaciones);

        List<String> comboValues = new ArrayList<>();
        comboValues.add("Todos");
        comboValues.add("Sí");
        comboValues.add("No");
        modelMap.addAttribute("comboValues", comboValues);

        DeliveryAppointment appointment = new DeliveryAppointment();
        modelMap.addAttribute("deliveryAppointment", appointment);

        modelMap.addAttribute("idCat", constraintsSearch.getGatoId());

        return "postulaciones";
    }


    @RequestMapping("/showPostulacionesBySpace")
    public String showApplicantsBySpace(ConstraintsSearch constraintsSearch, ModelMap modelMap) {

        Gato gato = gatoService.findOne(constraintsSearch.getGatoId());

        List<Postulacion> postulaciones;
        if (constraintsSearch.getSelectedValue().equals("*")) {
            postulaciones = postulacionService.getPostulacionesByGato(gato);
        } else {
            postulaciones = postulacionService.findPostulacionesByGatoAndTieneEspacio(gato, constraintsSearch.getSelectedValue());
        }

        modelMap.addAttribute("postulaciones", postulaciones);

        List<String> comboValues = new ArrayList<>();
        comboValues.add("Todos");
        comboValues.add("Sí");
        comboValues.add("No");

        modelMap.addAttribute("comboValues", comboValues);

        DeliveryAppointment appointment = new DeliveryAppointment();
        modelMap.addAttribute("deliveryAppointment", appointment);

        modelMap.addAttribute("idCat", constraintsSearch.getGatoId());

        return "postulaciones";
    }


    @RequestMapping(value = "/acceptAdoption", method = RequestMethod.POST)
    public String acceptAdoption(@Valid DeliveryAppointment deliveryAppointment, BindingResult bindingResult, RedirectAttributes flash) {

        if (bindingResult.hasErrors()) {
            return "redirect:/showPostulaciones/" + deliveryAppointment.getCatId();
        }

        Postulacion currentPostulacion = postulacionService.findOne(deliveryAppointment.getApplicationId());
        currentPostulacion.setEstatus(true);
        postulacionService.savePostulacion(currentPostulacion);

        Gato currentGato = gatoService.findOne(deliveryAppointment.getCatId());
        currentGato.setEstatusVisible(false);

        gatoService.saveGato(currentGato);



        emailService.sendSimpleEmail(deliveryAppointment.getEmail(),
                "Has sido seleccionado para la adopción", deliveryAppointment.getAppointmentAddress());

        flash.addFlashAttribute("success", "Correo enviado satisfactoriamente a: " + deliveryAppointment.getEmail());
        return "redirect:/index";
    }


    @GetMapping(value = "/applicantsReport/{idCat}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> applicantsReport(@PathVariable(value = "idCat") long id) throws JRException, IOException {

        Gato gato = gatoService.findOne(id);

        File imgLogo = ResourceUtils.getFile("classpath:static/images/logo.png");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("nombreGato", gato.getNombre());
        parameters.put("imgLogo", new FileInputStream(imgLogo));
        parameters.put("idGato", gato.getId());
        parameters.put("postulaciones", new JRBeanCollectionDataSource((Collection<?>) postulacionService.getPostulacionesByGato(gato)));

        JasperReport compileReport = JasperCompileManager
                .compileReport(new FileInputStream("src/main/resources/postulaciones.jrxml"));


        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, parameters, new JREmptyDataSource());
        byte data[] = JasperExportManager.exportReportToPdf(jasperPrint);

        String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
        StringBuilder stringBuilder = new StringBuilder().append("Postulaciones:");
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(stringBuilder
                        .append(gato.getNombre())
                        .append(gato.getId())
                        .append(sdf)
                        .append(".pdf")
                        .toString())
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(contentDisposition);

        return ResponseEntity.ok().contentLength((long) data.length)
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers).body(new ByteArrayResource(data));
    }


}
