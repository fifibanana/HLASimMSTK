package hla13.sklep.Produkty;

import hla13.sklep.sim.random.SimGenerator;

import java.util.Random;


public class Produkty {
    int timeToNext;
    private Random random;
    private SimGenerator generator;

    public Produkty() {
        random = new Random();
        timeToNext = generateTimeToNext();
        generator = new SimGenerator();
    }

    public int produce()
    {
        timeToNext=generateTimeToNext();
        int count = (int) generator.poisson(3.5);
       // int count2 = random.nextInt(2)+1;
       // List listOfCount = new ArrayList<>();
       // listOfCount.add(count);
       // listOfCount.add(count2);
        System.out.println("Wylosowana liczba produktów dla klienta pierwszego w kolejce wynosi: " + count + " produktów. Kolejna liczba produktów zostanie wylosowana za: " + timeToNext);
        //System.out.println("Wylosowana liczba produktów dla klienta pierwszego w kolejce B wynosi: " + count + " produktów. Kolejna liczba produktów zostanie wylosowana za: " + timeToNext);
        return count;
    }

    public int getTimeToNext() {
        return timeToNext;
    }

    private int generateTimeToNext()
    {
        return random.nextInt(3)+1;
    }
}
