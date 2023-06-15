package hla13.dupa.Klienci;

import java.util.Random;

import hla13.dupa.sim.random.SimGenerator;


public class Klienci {
    int timeToNext;
    private Random random;
    private SimGenerator generator;

    public Klienci() {
        random = new Random();
        timeToNext = generateTimeToNext();
        generator = new SimGenerator();
    }

    public int produce()
    {
        timeToNext=generateTimeToNext();
        // liczba produktów
        int count = (int) generator.poisson(3.5);
        //int count = random.nextInt(10)+1;
        //System.out.println("Do kas przybyło " + count + " klientów. Kolejni klienci przybędą za: " + timeToNext);
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
