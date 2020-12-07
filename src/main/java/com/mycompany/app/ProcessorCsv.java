package com.mycompany.app;

import com.mycompany.sdm.Processor;
import com.mycompany.sdm.Reader;
import com.mycompany.sdm.dto.Product;
import com.mycompany.sdm.dto.Properties;
import com.mycompany.sdm.interfaces.IProperties;
import java.io.FileNotFoundException;
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
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

@Named
@SessionScoped
public class ProcessorCsv implements Serializable, IProperties {

    private UploadedFile file;
    private InputStreamReader isr = null;
    private boolean disabled = true;
    private int days;

    private final Map<ProductTypes, Properties> qualitiesMap = qualities;

    private List<Product> products;
    private static final Logger LOG = Logger.getLogger(ProcessorCsv.class.getName());

    public ProcessorCsv() {
    }

    public String getAllowedTypes() {
        return "/(csv)$/";
    }

    public void upload(FileUploadEvent event) {
        file = event.getFile();

        try {
            isr = new InputStreamReader(file.getInputStream());
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }

        setDisabled(false);
    }

    public void process() {
        Reader reader = new Reader();

        try {
            products = reader.read(isr);
            LOG.log(Level.INFO, products.toString());
            read(products);
            setDisabled(true);
        } catch (FileNotFoundException ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "CSV nicht gefunden", "Die CSV wurde nicht gefunden: " + ex);
            PrimeFaces.current().dialog().showMessageDynamic(message);
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    private void read(List<Product> products) {
        Processor processor = new Processor();
        processor.process(products, getDays());
    }

    /**
     * Berechnet x Tage in die Zukunft.
     *
     * @param days
     * @return
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
}
