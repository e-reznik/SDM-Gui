package com.mycompany.app;

import com.mycompany.sdm.Processor;
import com.mycompany.sdm.Reader;
import com.mycompany.sdm.dto.Product;
import com.mycompany.sdm.dto.ProductProperties;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import com.mycompany.sdm.interfaces.Properties;

@Named
@SessionScoped
public class ProcessorCsv implements Serializable, Properties {

    @Inject
    private Helper helper;

    private UploadedFile file;
    private InputStreamReader isr = null;
    private int days;
    private final static String ALLOWEDTYPES = "/(csv)$/";

    private final Map<ProductTypes, ProductProperties> qualitiesMap = qualities;

    private List<Product> products;
    private static final Logger LOG = Logger.getLogger(ProcessorCsv.class.getName());

    public ProcessorCsv() {
    }

    /**
     * Zuständig für den Upload der CSV.
     *
     * @param event
     */
    public void upload(FileUploadEvent event) {
        file = event.getFile();

        try {
            isr = new InputStreamReader(file.getInputStream());
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }

        // Setzt den Prozess-Button auf enabled
        helper.setDisabled(false);
    }

    public void process() throws IOException {
        int activeIndex = helper.getActiveIndex();
        if (activeIndex == 0) {
            processCsv();
        } else if (activeIndex == 1) {
            processDb();
        }
    }

    /**
     * Liest die Produkte aus der CSV ein.
     */
    public void processCsv() {
        Reader reader = new Reader();

        products = reader.read(isr);
        LOG.log(Level.INFO, products.toString());
        read(products);
        // Setzt den Prozess-Button auf disabled
        helper.setDisabled(true);
    }

    /**
     * Liest die Produkte aus der DB ein.
     */
    public void processDb() {
        Reader reader = new Reader();

        products = reader.read();
        LOG.log(Level.INFO, products.toString());
        read(products);
        // Setzt den Prozess-Button auf disabled
        helper.setDisabled(true);
    }

    /**
     * Führt die Process-Methode mit den zuvor eingelesenen Produkten und der
     * Anzahl der Tagen (Iterationen) aus.
     *
     * @param products Liste mit den zu verarbeitenden Produkten
     */
    private void read(List<Product> products) {
        Processor processor = new Processor();
        processor.process(products, getDays());
    }

    /* g & s */
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public InputStreamReader getIsr() {
        return isr;
    }

    public void setIsr(InputStreamReader isr) {
        this.isr = isr;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getALLOWEDTYPES() {
        return ALLOWEDTYPES;
    }
}
