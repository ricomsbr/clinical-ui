package br.com.ackta.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.Resource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import net.sf.jasperreports.view.JasperViewer;

public class ReportUtil {

    private static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	private static final String XLSX = ".xlsx";
	private static final String PDF = ".pdf";

	public static void createAndSaveSheet(
			String filePath, 
			Resource layout, 
			Collection<?> beanCollection) {
		Map<String, Object> parameters = new HashMap<String, Object>();
    	parameters.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
    	parameters.put(JRParameter.REPORT_LOCALE, LocaleContextHolder.getLocale());
		createAndSaveSheet(filePath, parameters, layout, beanCollection);
	}
	
	public static void createAndSaveSheet(
			String filePath, 
			Map<String, Object> parameters, 
			Resource layout, 
			Collection<?> beanCollection) {
		String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern(YYYYMMDDHHMMSS));
		createAndSaveSheet(filePath, fileName, parameters, layout, beanCollection);
	}		
	public static void createAndSaveSheet(
			String filePath, 
			String fileName,
			Map<String, Object> parametros, 
			Resource layout, 
			Collection<?> beanCollection) {
        try {
            JasperPrint print = prepareReport(parametros, layout, beanCollection);

            // exibe o resultado
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(print));

            File dir = new File(filePath);
            dir.mkdir();
            exporter.setExporterOutput(
                    new SimpleOutputStreamExporterOutput(filePath + fileName + XLSX));
            SimpleXlsxReportConfiguration xlsReportConfiguration = new SimpleXlsxReportConfiguration();
            xlsReportConfiguration.setOnePagePerSheet(false);
            xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
            xlsReportConfiguration.setDetectCellType(false);
            xlsReportConfiguration.setWhitePageBackground(false);
            xlsReportConfiguration.setCollapseRowSpan(true);

            exporter.setConfiguration(xlsReportConfiguration);
            exporter.exportReport();
        } catch (JRException e) {
            throw new RuntimeException(e);
        }

    }
	
	public static void createAndSavePdf(
			String filePath, 
			Resource layout, 
			Collection<?> beanCollection) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(JRParameter.REPORT_LOCALE, LocaleContextHolder.getLocale());
		createAndSavePdf(filePath, parameters, layout, beanCollection);
	}
	
	public static void createAndSavePdf(
			String filePath, 
			Map<String, Object> parameters, 
			Resource layout, 
			Collection<?> beanCollection) {
		String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern(YYYYMMDDHHMMSS));
		createAndSavePdf(filePath, fileName, parameters, layout, beanCollection);
	}
	
	public static void createAndSavePdf(
			String filePath, 
			String fileName,
			Map<String, Object> parameters, 
			Resource layout, 
			Collection<?> beanCollection) {
        try {
        	File dir = new File(filePath);
            dir.mkdir();
            
            JasperPrint print = prepareReport(parameters, layout, beanCollection);
                 
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(print));
            
            exporter.setExporterOutput(
                    new SimpleOutputStreamExporterOutput(filePath + fileName + PDF));
            SimplePdfReportConfiguration pdfReportConfiguration = new SimplePdfReportConfiguration();

            exporter.setConfiguration(pdfReportConfiguration);
            exporter.exportReport();
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

	public static byte[] createPdf(
			Resource layout, 
			Collection<?> beanCollection) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(JRParameter.REPORT_LOCALE, LocaleContextHolder.getLocale());
		return createPdf(parameters, layout, beanCollection);
	}

	public static byte[] createPdf(
			Map<String, Object> parameters, 
			Resource layout, 
			Collection<?> beanCollection) {
		byte[] bytesData = new byte[]{};
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            
            JasperPrint print = prepareReport(parameters, layout, beanCollection);
            JasperExportManager.exportReportToPdfStream(print, bos);
            bytesData = bos.toByteArray();
        } catch (JRException | IOException e) {
            throw new RuntimeException(e);
        }
		return bytesData;
    }
	
	public static void createAndViewReport(
			Map<String, Object> parameters, 
			Resource layout, 
			Collection<?> beanCollection) {
		JasperPrint print = prepareReport(parameters, layout, beanCollection);
		JasperViewer.viewReport(print, true );
	}
	
	/**
	 * @param parameters
	 * @param layout
	 * @param beanCollection
	 * @return
	 * @throws IOException
	 * @throws JRException
	 */
	private static JasperPrint prepareReport(Map<String, Object> parameters, Resource layout,
			Collection<?> beanCollection) {
		try {
			// gerando o jasper design
			InputStream inputStream = layout.getInputStream();
			JasperDesign design = JRXmlLoader.load(inputStream);
	
			// compila o relatório
			JasperReport report = JasperCompileManager.compileReport(design);
	
			//
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(beanCollection);
	
			// executa o relatório
			JasperPrint print = JasperFillManager.fillReport(report, parameters, beanCollectionDataSource);
			return print;
        } catch (JRException | IOException e) {
            throw new RuntimeException(e);
        }
	}
}
