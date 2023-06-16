package hla13.sklep.Produkty;

import hla13.sklep.sim.random.SimGenerator;

import java.util.Random;


public class Produkty {
    int timeToNext;
    private Random random;
    private SimGenerator generator;

    // Poisson distribution parameter to regulate percent of client buying under 5 products
    // at a = 3.5 about 60% clients purchase under 5 products
    private double n = 3.5;


    public Produkty() {
        random = new Random();
        timeToNext = generateTimeToNext();
        generator = new SimGenerator();
    }

    public int produce()
    {
        timeToNext=generateTimeToNext();
        int count = (int) generator.poisson(n);
        System.out.println("Wylosowana liczba produktów dla klienta pierwszego w kolejce wynosi: " + count + " produktów. Kolejna liczba produktów zostanie wylosowana za: " + timeToNext);
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
