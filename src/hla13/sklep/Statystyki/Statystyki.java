package hla13.sklep.Statystyki;

import hla13.sklep.sim.monitors.Diagram;
import hla13.sklep.sim.monitors.MonitoredVar;

import java.awt.*;
import java.util.Random;

public class Statystyki {


    private final double federationTimeLimit = 300;
    int timeToNext;
    private Random random;
    double sredniaDlugoscKolejkiDlaKasSzybkich = 0.0;
    double sredniaDlugoscKolejkiDlaKasWolnych = 0.0;

    int sumOfClientsInFastQ = 0;
    int sumOfClientsInSlowQ = 0;

    int fastQLengthChanges = 0;

    int slowQLengthChanges = 0;

    int ostatniaOdnotowanaLiczbaKlienctowWKolejceFast = 0;
    int ostatniaOdnotowanaLiczbaKlienctowWKolejceSlow = 0;

    int liczbaObsluzonychWkolejceFast = 0;
    int liczbaObsluzonychWkolejceSlow = 0;

    int lacznyCzasOczekiwaniaWkolejceFast = 0;
    int lacznyCzasOczekiwaniaWkolejceSlow = 0;

    int timeAddedFastQ = 0;
    int timeAddedSlowQ = 0;

    double sredniCzasOczekiwaniaWKolejceFast = 0.0;
    double sredniCzasOczekiwaniaWKolejceSlow = 0.0;

    int licznikWykonan = 0;
    double licznikWykonanDouble = 0.0;
    MonitoredVar dlugoscKolejkiFast = new MonitoredVar();
    MonitoredVar czasOczekiwaniaWKolejceFast = new MonitoredVar();
    MonitoredVar dlugoscKolejkiSlow = new MonitoredVar();
    MonitoredVar czasOczekiwaniaWKolejceSlow = new MonitoredVar();

    Diagram diagramDlugosciKolejek = new Diagram(Diagram.DiagramType.TIME_FUNCTION,"Zmiana długości kolejek");
    Diagram diagramCzasuOczekiwan = new Diagram(Diagram.DiagramType.TIME_FUNCTION,"Zmiana czasu oczekiwania w kolejce");
    public Statystyki() {
        random = new Random();
        timeToNext = generateTimeToNext();
    }


    public void wypiszStatystyki(int numberOfClientsInFast,int numberOfClientsInSlow, int numberOfProductsInSlow, int numberOfProductsInFast){
        licznikWykonanDouble += 1.0;
        dlugoscKolejkiFast.setValue(numberOfClientsInFast,licznikWykonanDouble);
        dlugoscKolejkiSlow.setValue(numberOfClientsInSlow,licznikWykonanDouble);

        if(numberOfClientsInFast != ostatniaOdnotowanaLiczbaKlienctowWKolejceFast){
            if(numberOfClientsInFast< ostatniaOdnotowanaLiczbaKlienctowWKolejceFast){
                liczbaObsluzonychWkolejceFast++;
            }
            fastQLengthChanges++;

            sumOfClientsInFastQ += numberOfClientsInFast;
            ostatniaOdnotowanaLiczbaKlienctowWKolejceFast = numberOfClientsInFast;
        }
        if(numberOfClientsInSlow != ostatniaOdnotowanaLiczbaKlienctowWKolejceSlow){
            if(numberOfClientsInSlow< ostatniaOdnotowanaLiczbaKlienctowWKolejceSlow){
                liczbaObsluzonychWkolejceSlow++;
            }
            slowQLengthChanges++;

            sumOfClientsInSlowQ += numberOfClientsInSlow;
            ostatniaOdnotowanaLiczbaKlienctowWKolejceSlow = numberOfClientsInSlow;
        }
        System.out.println("Liczba klientów w kolejce Fast: "+ numberOfClientsInFast);
        System.out.println("Liczba klientów w kolejce Slow: "+ numberOfClientsInSlow);
        if(fastQLengthChanges !=0){
            sredniaDlugoscKolejkiDlaKasSzybkich = sumOfClientsInFastQ *1.0/ fastQLengthChanges;
        }
        if(slowQLengthChanges !=0){
            sredniaDlugoscKolejkiDlaKasWolnych = sumOfClientsInSlowQ *1.0/ slowQLengthChanges;
        }
        System.out.println("Srednia liczba klientów w kolejce kasy Fast: "+ sredniaDlugoscKolejkiDlaKasSzybkich);
        System.out.println("Srednia liczba klientów w kolejce kasy Slow: "+ sredniaDlugoscKolejkiDlaKasWolnych);



        if(numberOfProductsInSlow>0&& timeAddedFastQ ==0){
            lacznyCzasOczekiwaniaWkolejceFast +=numberOfProductsInSlow;
            czasOczekiwaniaWKolejceFast.setValue(numberOfProductsInSlow,licznikWykonanDouble);
            timeAddedFastQ = 1;
        }
        else if(numberOfProductsInSlow == 0){
            timeAddedFastQ = 0;
        }
        if(numberOfProductsInFast>0&& timeAddedSlowQ ==0){
            lacznyCzasOczekiwaniaWkolejceSlow +=numberOfProductsInFast;
            czasOczekiwaniaWKolejceSlow.setValue(numberOfProductsInFast,licznikWykonanDouble);
            timeAddedSlowQ = 1;
        }
        else if(numberOfProductsInFast == 0){
            timeAddedSlowQ = 0;
        }
        if(liczbaObsluzonychWkolejceFast !=0)
            sredniCzasOczekiwaniaWKolejceFast = lacznyCzasOczekiwaniaWkolejceFast * 1.0 / liczbaObsluzonychWkolejceFast;
        if(liczbaObsluzonychWkolejceSlow !=0)
            sredniCzasOczekiwaniaWKolejceSlow = lacznyCzasOczekiwaniaWkolejceSlow * 1.0 / liczbaObsluzonychWkolejceSlow;

        System.out.println("Sredni czas oczekiwania w kolejce fast wynosi: "+ sredniCzasOczekiwaniaWKolejceFast);
        System.out.println("Sredni czas oczekiwania w kolejce slow wynosi: "+ sredniCzasOczekiwaniaWKolejceSlow);
        licznikWykonan++;

        //DIAGRAMY

        if(licznikWykonan == federationTimeLimit){

            diagramDlugosciKolejek.add(dlugoscKolejkiFast, Color.RED,"Kolejka Fast");
            diagramDlugosciKolejek.add(dlugoscKolejkiSlow, Color.GREEN,"Kolejka Slow");
            diagramDlugosciKolejek.show();
            diagramCzasuOczekiwan.add(czasOczekiwaniaWKolejceFast,Color.RED,"Kolejka Fast");
            diagramCzasuOczekiwan.add(czasOczekiwaniaWKolejceSlow,Color.GREEN,"Kolejka Slow");
            diagramCzasuOczekiwan.show();

        }


    }

    public int getTimeToNext() {
            return timeToNext;
        }
    private int generateTimeToNext()
        {
            return (random.nextInt(2)+1);
        }
}//34687469236894323486
