package com.cibertec.controladores;

import com.cibertec.modelos.Categoria;
import com.cibertec.modelos.Producto;
import com.cibertec.repositorio.CategoriaRepository;
import com.cibertec.repositorio.ProductoRepository;
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
public class ProductoConsultar {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/consultarProducto")
    public String buscarProductosPorParametros(
            @RequestParam(name = "descripcion", required = false, defaultValue = "") String descripcion,
            @RequestParam(name = "codigo", required = false, defaultValue = "") String codigo,
            @RequestParam(name = "id_categoria", required = false, defaultValue = "-1") int id_categoria,
            @RequestParam(name = "precio", required = false, defaultValue = "") Double precio,
            @RequestParam(name = "stock", required = false, defaultValue = "-1") int stock,

            Model model) {
    	log.info(">>> Parámetros recibidos - Descripción: " + descripcion + ", Código: " + codigo + ", ID Categoría: " + id_categoria + ", Precio: " + precio + ", Stock: " + stock);

        List<Producto> lstSalida = productoRepository.listaCompleja(descripcion, codigo, id_categoria,precio,stock);
        List<Categoria> listaCategorias = categoriaRepository.findAll();

        model.addAttribute("productos", lstSalida);
        model.addAttribute("categorias", listaCategorias);
        model.addAttribute("descripcion", descripcion);
        model.addAttribute("codigo", codigo);
        model.addAttribute("id_categoria", id_categoria);
        model.addAttribute("precio", precio);
        if (stock != -1) {
            model.addAttribute("stock", stock);
        }


        return "vistas/Producto/consultar";
    }

    @GetMapping("/reporteProductoPdf")
    public void reportePDF(HttpServletRequest request,
                           HttpServletResponse response,
                           @RequestParam(name = "descripcion", required = false, defaultValue = "") String descripcion,
                           @RequestParam(name = "codigo", required = false, defaultValue = "") String codigo,
                           @RequestParam(name = "id_categoria", required = false, defaultValue = "-1") int id_categoria,
                           @RequestParam(name = "precio", required = false, defaultValue = "") Double precio,
                           @RequestParam(name = "stock", required = false, defaultValue = "-1") int stock,
                           Model model) {

    	log.info(">>> Parámetros recibidos - Descripción: " + descripcion + ", Código: " + codigo + ", ID Categoría: " + id_categoria + ", Precio: " + precio + ", Stock: " + stock);

        try {
            List<Producto> lstSalida = productoRepository.listaCompleja(descripcion, codigo, id_categoria,precio,stock);

            response.setContentType("application/x-pdf");
            response.addHeader("Content-disposition", "attachment; filename=ReporteProducto.pdf");

            String fileDirectory = request.getServletContext().getRealPath("reporte/Producto.jasper");
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
