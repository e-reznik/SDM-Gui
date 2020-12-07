
import com.mycompany.sdm.Processor;
import com.mycompany.sdm.Reader;
import com.mycompany.sdm.dto.Product;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

@Named
@RequestScoped
public class ProcessorLoader implements Serializable {

    private static final Logger LOG = Logger.getLogger(ProcessorLoader.class.getName());

 

    public ProcessorLoader() {
    }


}