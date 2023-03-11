package lk.epic.controller;

import lk.epic.dto.CustomerDTO;
import lk.epic.service.CustomerService;
import lk.epic.util.ResponseUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("customer")
@CrossOrigin
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping
    public ResponseUtil getAllCustomers() {
        return new ResponseUtil(200, "success", customerService.getAll());
    }

    @GetMapping(params = {"format"})
    public void generateReport(@RequestParam String format, HttpServletResponse response) {
        try {
            List<CustomerDTO> customerDTOS = new ArrayList<>();/*customerService.getAll()*/;

            /*file loading*/
            File file = ResourceUtils.getFile("classpath:employee.jrxml");

            /*compilation te file*/
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(customerDTOS);

            Map<String, Object> map = new HashMap<>();
            map.put("created by", "Wishvajith");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, dataSource);

            if (format.equalsIgnoreCase("docx")) {
                JRDocxExporter exporter = new JRDocxExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
                response.setHeader(
                        HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=employees.docx");
                response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                exporter.exportReport();

            }else if (format.equalsIgnoreCase("pdf")){
                JRPdfExporter exporter = new JRPdfExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
                response.setContentType("application/pdf");
                response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,HttpHeaders.CONTENT_DISPOSITION);
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=employees.pdf");
                exporter.exportReport();

            }else if (format.equalsIgnoreCase("excel")){
                JRXlsxExporter exporter = new JRXlsxExporter();
                SimpleXlsxReportConfiguration reportConfigXLS = new SimpleXlsxReportConfiguration();
                reportConfigXLS.setSheetNames(new String[] { "Sheet 1" });
                reportConfigXLS.setDetectCellType(true);
                reportConfigXLS.setOnePagePerSheet(false);
                reportConfigXLS.setRemoveEmptySpaceBetweenRows(true);
                reportConfigXLS.setRemoveEmptySpaceBetweenColumns(true);
                reportConfigXLS.setWhitePageBackground(false);
                reportConfigXLS.setCollapseRowSpan(true);

                exporter.setConfiguration(reportConfigXLS);
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
                response.setHeader("Content-Disposition", "attachment;filename=employees.xlsx");
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                exporter.exportReport();

            }else if (format.equalsIgnoreCase("csv")){
                JRCsvExporter exporter = new JRCsvExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleWriterExporterOutput(response.getOutputStream()));
                response.setHeader("Content-Disposition", "attachment;filename=employees.csv");
                response.setContentType("text/csv");
                exporter.exportReport();
            }else {

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JRException | IOException e) {
            throw new RuntimeException(e);
        }



    }

    @PostMapping
    public ResponseUtil saveCustomer(@RequestBody CustomerDTO customerDTO) {
        if (customerService.saveCustomer(customerDTO)) {
            return new ResponseUtil(200, "Successfully Added", null);
        } else {
            return new ResponseUtil(500, "Can't save the customer", null);
        }
    }

    @PutMapping
    public ResponseUtil updateCustomer(@RequestBody CustomerDTO customerDTO) {
        if (customerService.UpdateCustomer(customerDTO)) {
            return new ResponseUtil(200, "Successfully Updated", null);
        } else {
            return new ResponseUtil(500, "Can't update the customer", null);
        }
    }

    @DeleteMapping(params = {"userId"})
    public ResponseUtil deleteCustomer(@RequestParam String userId ) {
        if (customerService.deleteCustomer(userId)) {
            return new ResponseUtil(200, "Successfully deleted", null);
        } else {
            return new ResponseUtil(500, "Fail to delete the customer!", null);
        }
    }
}
