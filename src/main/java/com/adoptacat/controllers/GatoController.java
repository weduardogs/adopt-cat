package com.adoptacat.controllers;


import com.adoptacat.constrains.ConstraintsSearch;
import com.adoptacat.model.Gato;
import com.adoptacat.model.dao.DataCatReport;
import com.adoptacat.service.GatoService;
import com.adoptacat.service.UploadFileService;
import com.adoptacat.validator.CatValidator;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@SessionAttributes("gato")
public class GatoController {

    @Autowired
    private GatoService gatoService;

    @Autowired
    private CatValidator catValidator;

    @Autowired
    private UploadFileService uploadFileService;


    @RequestMapping("/applyGato")
    public String applyGato(@RequestParam("id") long id, ModelMap modelMap) {

        modelMap.addAttribute("id", id);

        return "showApplyForm";
    }

    @RequestMapping("/showGatos")
    public String showGatos(ModelMap modelMap) {
        List<Gato> gatos = gatoService.getAllGatos();
        modelMap.addAttribute("gatos", gatos);

        ConstraintsSearch constraintsSearch = new ConstraintsSearch();
        modelMap.addAttribute("constraintsSearch", constraintsSearch);

        return "my-ads";
    }

    @RequestMapping("/updatePost/{id}")
    public String showUpdate(@PathVariable(value = "id") long id, Model model, RedirectAttributes flash) {

        Gato gato = null;

        if (id > 0) {
            gato = gatoService.findOne(id);

            if (gato == null) {
                flash.addFlashAttribute("error", "No existe registro del gato");
                return "redirect:/showGatos";
            }
        } else {
            flash.addFlashAttribute("error", "No hay Id 0");
            return "redirect:/showGatos";
        }

        model.addAttribute("gato", gato);
        model.addAttribute("title", "Editar Anuncio");

        return "create-post";
    }

    @RequestMapping("/createPost")
    public String createPost(Model model) {
        model.addAttribute("gato", new Gato());
        model.addAttribute("title", "Crear Anuncio");
        return "create-post";
    }

    @RequestMapping(value = "/createPost", method = RequestMethod.POST)
    public String createGato(@Valid Gato gato, BindingResult bindingResult, Model model,
                             Principal principal,
                             @RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {

        catValidator.validate(gato, bindingResult);
        gato.setOwnerName(principal.getName());

        if (bindingResult.hasErrors()) {
            return "create-post";
        }

        if (!foto.isEmpty()) {

            if (gato.getId() > 0 && gato.getFoto() != null && gato.getFoto().length() > 0) {
                uploadFileService.delete(gato.getFoto());
            }

            String uniqueFilename = null;

            try {
                uniqueFilename = uploadFileService.copy(foto);
            } catch (IOException e) {
                e.printStackTrace();
            }

            flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");
            gato.setFoto(uniqueFilename);

        }

        gato.setEstatusVisible(true);

        gatoService.saveGato(gato);
        status.setComplete();

        flash.addFlashAttribute("success", "Anuncio guardado con éxito");
        return "redirect:/showGatos";
    }

    @RequestMapping("/deletePost/{id}")
    public String deleteGato(@PathVariable(value = "id") long id, RedirectAttributes flash) {

        if (id > 0) {
            Gato gato = gatoService.findOne(id);
            gatoService.deleteGato(gato);
            flash.addFlashAttribute("success", "Anuncio eliminado con éxito");
        }

        return "redirect:/showGatos";
    }

    @GetMapping(value = "/uploads/{filename:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

        Resource recurso = null;

        try {
            recurso = uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
                .body(recurso);
    }

    @RequestMapping("/showCatsByWeight")
    public String showPostulacionesByWeight(ConstraintsSearch constraintsSearch, ModelMap modelMap) {

        List<Gato> gatos;

        if (constraintsSearch.getPesoGato() > 0) {
            gatos = gatoService.findGatosByPeso(constraintsSearch.getPesoGato());
        } else {
            gatos = gatoService.getAllGatos();
        }

        modelMap.addAttribute("gatos", gatos);

        return "my-ads";
    }

    @GetMapping(value = "/catsReport", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> applicantsReport() throws JRException, IOException {

        List<Gato> gatos = gatoService.getAllGatos();
        List<DataCatReport> catData = new ArrayList<>();
        File imgLogo = ResourceUtils.getFile("classpath:static/images/logo.png");
		File foto = null;


        for (Gato gato : gatos) {
        	String uriFile = "uploads/" + gato.getFoto();
        	foto = ResourceUtils.getFile(uriFile);
        	catData.add(new DataCatReport(gato.getReportData(), new FileInputStream(foto)));
        }

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("imgLogo", new FileInputStream(imgLogo));
        parameters.put("gatos", new JRBeanCollectionDataSource((Collection<?>) catData));

        JasperReport compileReport = JasperCompileManager
                .compileReport(new FileInputStream("src/main/resources/gatos.jrxml"));


        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, parameters, new JREmptyDataSource());
        byte data[] = JasperExportManager.exportReportToPdf(jasperPrint);

        String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
        StringBuilder stringBuilder = new StringBuilder().append("Postulaciones:");
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(stringBuilder
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
