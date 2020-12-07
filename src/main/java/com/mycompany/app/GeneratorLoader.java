package com.mycompany.app;

import com.mycompany.sdmcsvgenerator.interfaces.IProperties.Apples;
import com.mycompany.sdmcsvgenerator.interfaces.IProperties.Cheeses;
import com.mycompany.sdmcsvgenerator.interfaces.IProperties.ProductTypes;
import com.mycompany.sdmcsvgenerator.interfaces.IProperties.Wines;
import com.mycompany.sdmcsvgenerator.logic.ProductGenerator;
import com.mycompany.sdmcsvgenerator.model.ProductList;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.mycompany.dto.ProductVars;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

@Named
@RequestScoped
public class GeneratorLoader implements Serializable {

// TODO: Inject
    private ProductGenerator productGenerator = new ProductGenerator();

    @Inject
    ProductVars productVars;

    public void generateProducts() {

        List<ProductList> list = new ArrayList<>();

        list.add(new ProductList(ProductTypes.KAESE, Cheeses.values(), productVars.getNumCheese()));
        list.add(new ProductList(ProductTypes.WEIN, Wines.values(), productVars.getNumWine()));
        list.add(new ProductList(ProductTypes.AEPFEL, Apples.values(), productVars.getNumApples()));

        if (productVars.getNumApples() > 0
                || productVars.getNumCheese() > 0
                || productVars.getNumWine() > 0) {
            try {
                productGenerator.generate(list, "/home/evgenij/products.csv");

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg", "Die CSV wurde erfolgreich generiert.");
                PrimeFaces.current().dialog().showMessageDynamic(message);

            } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException ex) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", "Beim Generieren der CSV ist ein Fehler aufgetreten.");
                PrimeFaces.current().dialog().showMessageDynamic(message);

                Logger.getLogger(GeneratorLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Leere CSV", "Eine leere CSV kann nicht verarbeitet werden.\nBitte wählen Sie mindestens 1 Produkt.");
            PrimeFaces.current().dialog().showMessageDynamic(message);
        }
    }
}
