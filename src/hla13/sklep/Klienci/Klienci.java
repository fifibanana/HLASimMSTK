package hla13.sklep.Klienci;

import java.util.Random;

import hla13.sklep.sim.random.SimGenerator;


public class Klienci {
    int timeToNext;
    private Random random;
    private SimGenerator generator;


    // Poisson distribution parameter to regulate percent of client buying under 5 products
    // at a = 3.5 about 60% clients purchase under 5 products
    private double n = 3.5;


    public Klienci() {
        random = new Random();
        timeToNext = generateTimeToNext();
        generator = new SimGenerator();
    }

    public int produce()
    {
        timeToNext=generateTimeToNext();
        // liczba produktów
        int count = (int) generator.poisson(n);
        System.out.println("Do kasy przybył klient! Kolejny klient przybędzie za: " + timeToNext);
        return count;
    }

    public int getTimeToNext() {
        return timeToNext;
    }

    private int generateTimeToNext()
    {
        return random.nextInt(8)+3;
    }
}
