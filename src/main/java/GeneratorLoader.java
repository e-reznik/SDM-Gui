
import com.mycompany.sdm.interfaces.IProperties;
import com.mycompany.sdm.logic.ProductGenerator;
import com.mycompany.sdm.model.ProductList;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import dto.ProductVars;
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
public class GeneratorLoader implements Serializable, IProperties {

// TODO: Inject
    private ProductGenerator productGenerator = new ProductGenerator();

    @Inject
    ProductVars productVars;

    public void generateProducts() {

        List<ProductList> list = new ArrayList<>();

        list.add(new ProductList(ProductTypes.KAESE, Cheeses.values(), productVars.getNumCheese()));
        list.add(new ProductList(ProductTypes.WEIN, Wines.values(), productVars.getNumWine()));
        list.add(new ProductList(ProductTypes.AEPFEL, Apples.values(), productVars.getNumApples()));

        try {
            productGenerator.generate(list, "/home/evgenij/products.csv");

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg", "Die CSV wurde erfolgreich generiert.");
            PrimeFaces.current().dialog().showMessageDynamic(message);

        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", "Beim Generieren der CSV ist ein Fehler aufgetreten.");
            PrimeFaces.current().dialog().showMessageDynamic(message);

            Logger.getLogger(GeneratorLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
