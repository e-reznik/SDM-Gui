package com.mycompany.app;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.event.TabChangeEvent;

@Named
@SessionScoped
public class Helper implements Serializable {

    private boolean disabled = true;
    private int activeIndex;

    public Helper() {

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

    public void onTabChanged(TabChangeEvent event) {
        String id = event.getTab().getId();
        if (id.equals("tabcsv")) {
            activeIndex = 0;
            setDisabled(true);
            System.out.println("ist csv");
        } else if (id.equals("tabdb")) {
            activeIndex = 1;
            setDisabled(false);
            System.out.println("ist db");
        }
        System.out.println("id: " + id);
    }

    /* g& s */
    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

}
