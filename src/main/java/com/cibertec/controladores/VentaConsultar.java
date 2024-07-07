package com.cibertec.controladores;

import com.cibertec.modelos.Cliente;
import com.cibertec.modelos.Producto;
import com.cibertec.modelos.Venta;
import com.cibertec.repositorio.ClienteRepository;
import com.cibertec.repositorio.ProductoRepository;
import com.cibertec.repositorio.VentaRepository;
import lombok.extern.apachecommons.CommonsLog;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@CommonsLog
public class VentaConsultar {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/consultarVenta")
    public String consultarVenta(
            @RequestParam(name = "id_cliente", required = false, defaultValue = "-1") Integer id_cliente,
            @RequestParam(name = "id_producto", required = false, defaultValue = "-1") Integer id_producto,
            @RequestParam(name = "fechaDesde", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
            @RequestParam(name = "fechaHasta", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta,
            Model model) {

        List<Venta> lstVentas = ventaRepository.listaCompleja(id_cliente, id_producto, fechaDesde, fechaHasta);
        List<Cliente> listaclientes = clienteRepository.findAll();
        List<Producto> listaproductos = productoRepository.findAll();

        model.addAttribute("ventas", lstVentas);
        model.addAttribute("clientes", listaclientes);
        model.addAttribute("productos", listaproductos);
        model.addAttribute("fechaDesde", fechaDesde);
        model.addAttribute("fechaHasta", fechaHasta);
        model.addAttribute("id_cliente", id_cliente);
        model.addAttribute("id_producto", id_producto);

        return "vistas/Venta/consultar";
    }

    @GetMapping("/reporteVentaPdf")
    public void reportePDF(HttpServletRequest request,
                           HttpServletResponse response,
                           @RequestParam(name = "id_cliente", required = false) Integer id_cliente,
                           @RequestParam(name = "id_producto", required = false) Integer id_producto,
                           @RequestParam(name = "fechaDesde", required = false) String fechaDesde,
                           @RequestParam(name = "fechaHasta", required = false) String fechaHasta) {

        log.info(">>> Parámetros recibidos - ID Cliente: " + id_cliente + ", ID Producto: " + id_producto + ", Fecha Desde: " + fechaDesde + ", Fecha Hasta: " + fechaHasta);

        try {
            List<Venta> lstVentas = ventaRepository.listaCompleja(id_cliente, id_producto, parseFecha(fechaDesde), parseFecha(fechaHasta));

            response.setContentType("application/x-pdf");
            response.addHeader("Content-disposition", "attachment; filename=ReporteVentas.pdf");

            String fileDirectory = request.getServletContext().getRealPath("reporte/Venta.jasper");
            FileInputStream stream = new FileInputStream(new File(fileDirectory));

            String fileLogo = request.getServletContext().getRealPath("img/cibertec.jpg");
            HashMap<String, Object> params = new HashMap<>();
            params.put("RUTA_LOGO", fileLogo);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lstVentas);

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(stream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

            OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

        } catch (IOException | JRException e) {
            e.printStackTrace();
        }
    }

    private Date parseFecha(String fechaString) {
        if (fechaString != null && !fechaString.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.parse(fechaString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
