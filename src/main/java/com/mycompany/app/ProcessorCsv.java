package com.mycompany.app;

import com.mycompany.sdm.Processor;
import com.mycompany.sdm.Reader;
import com.mycompany.sdm.dto.Product;
import com.mycompany.sdm.dto.Properties;
import com.mycompany.sdm.interfaces.IProperties;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

@Named
@SessionScoped
public class ProcessorCsv implements Serializable, IProperties {

    private UploadedFile file;
    private InputStreamReader isr = null;
    private boolean disabled = true;
    private int days;
    private final static String ALLOWEDTYPES = "/(csv)$/";

    private final Map<ProductTypes, Properties> qualitiesMap = qualities;

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
        setDisabled(false);
    }

    /**
     * Liest die Produkte aus der CSV ein.
     */
    public void process() {
        Reader reader = new Reader();

        products = reader.read(isr);
        LOG.log(Level.INFO, products.toString());
        read(products);
        // Setzt den Prozess-Button auf disabled
        setDisabled(true);
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

    /**
     * Berechnet x Tage in die Zukunft. Nötig, um das MHZ als Datum anzeigen zu
     * können.
     *
     * @param days Anzahl der gewünschten Tage in der Zukunft
     * @return Datum: Heute + Anzahl der Tage
     */
    public Date addDays(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
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

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
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
