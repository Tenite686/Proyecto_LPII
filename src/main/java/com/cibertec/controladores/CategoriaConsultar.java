package com.cibertec.controladores;

import com.cibertec.modelos.Categoria;
import com.cibertec.repositorio.CategoriaRepository;
import lombok.extern.apachecommons.CommonsLog;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

@Controller
@CommonsLog
public class CategoriaConsultar {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/consultarCategoria")
    public String buscarCategoriasPorParametros(
            @RequestParam(name = "nombre", required = false, defaultValue = "") String nombre,
            Model model) {
        log.info(">>> Parámetros recibidos - Nombre: " + nombre);

        List<Categoria> lstSalida = categoriaRepository.listaCompleja(nombre);

        model.addAttribute("categorias", lstSalida);
        model.addAttribute("nombre", nombre);

        return "vistas/Categoria/consultar";
    }

    @GetMapping("/reporteCategoriaPdf")
    public void reportePDF(HttpServletRequest request,
                           HttpServletResponse response,
                           @RequestParam(name = "nombre", required = false, defaultValue = "") String nombre,
                           Model model) {

        log.info(">>> Parámetros recibidos - Nombre: " + nombre);

        try {
            List<Categoria> lstSalida = categoriaRepository.listaCompleja(nombre);

            response.setContentType("application/x-pdf");
            response.addHeader("Content-disposition", "attachment; filename=ReporteCategoria.pdf");

            String fileDirectory = request.getServletContext().getRealPath("reporte/Categoria.jasper");
            FileInputStream stream = new FileInputStream(new File(fileDirectory));

            String fileLogo = request.getServletContext().getRealPath("img/cibertec.jpg");
            HashMap<String, Object> params = new HashMap<>();
            params.put("RUTA_LOGO", fileLogo);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lstSalida);

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(stream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

            OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

        } catch (IOException | JRException e) {
            e.printStackTrace();
        }
    }
}
