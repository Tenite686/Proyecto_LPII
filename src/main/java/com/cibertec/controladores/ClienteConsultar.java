package com.cibertec.controladores;

import com.cibertec.modelos.Cliente;
import com.cibertec.repositorio.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.apachecommons.CommonsLog;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.ui.Model;
@Controller
@CommonsLog
public class ClienteConsultar {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/consultarCliente")
    public String buscarClientesPorParametros(
            @RequestParam(name = "nombre", required = false, defaultValue = "") String nombre,
            @RequestParam(name = "direccion", required = false, defaultValue = "") String direccion,
            @RequestParam(name = "num_ruc", required = false, defaultValue = "") String num_ruc,
            @RequestParam(name = "telefono", required = false, defaultValue = "") String telefono,
            Model model) {
        log.info(">>> Parámetros recibidos - Nombre: " + nombre + ", Dirección: " + direccion + ", Num RUC: " + num_ruc + ", Teléfono: " + telefono);

        List<Cliente> lstSalida = clienteRepository.listaCompleja(
        		 nombre,
                 direccion,
                 num_ruc,
                 telefono);


        model.addAttribute("clientes", lstSalida);
        model.addAttribute("nombre", nombre);
        model.addAttribute("direccion", direccion);
        model.addAttribute("num_ruc", num_ruc);
        model.addAttribute("telefono", telefono);
        return "vistas/cliente/consultar";
    }

    @GetMapping("/reporteClientePdf")
    public void reportePDF(HttpServletRequest request,
                           HttpServletResponse response,
                           @RequestParam(name = "nombre", required = false, defaultValue = "") String nombre,
                           @RequestParam(name = "direccion", required = false, defaultValue = "") String direccion,
                           @RequestParam(name = "num_ruc", required = false, defaultValue = "") String num_ruc,
                           @RequestParam(name = "telefono", required = false, defaultValue = "") String telefono) {

        log.info(">>> Parámetros recibidos - Nombre: " + nombre + ", Dirección: " + direccion + ", Num RUC: " + num_ruc + ", Teléfono: " + telefono);

        try {
            // Obtener la lista de clientes según los parámetros
            List<Cliente> lstSalida = clienteRepository.listaCompleja(nombre, direccion, num_ruc, telefono);

            // Depuración: imprimir la lista de salida
            lstSalida.forEach(cliente -> log.info(cliente.toString()));

            // Configuración del response para descargar el PDF
            response.setContentType("application/x-pdf");
            response.addHeader("Content-disposition", "attachment; filename=ReporteCliente.pdf");

            // Obtener el archivo que contiene el diseño del reporte
            String fileDirectory = request.getServletContext().getRealPath("reporte/Cliente.jasper");
            log.info(">>> File Reporte >> " + fileDirectory);
            FileInputStream stream = new FileInputStream(new File(fileDirectory));

            // Parámetros adicionales, como la ruta del logo
            String fileLogo = request.getServletContext().getRealPath("img/cibertec.jpg");
            log.info(">>> File Logo >> " + fileLogo);
            HashMap<String, Object> params = new HashMap<>();
            params.put("RUTA_LOGO", fileLogo);

            // Creación del data source para JasperReports
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lstSalida);

            // Llenar el reporte con los datos y parámetros
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(stream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

            // Exportar el reporte como PDF y enviar al OutputStream del response
            OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de errores aquí, por ejemplo, redirigir o mostrar un mensaje de error
        }
    }

    
}
