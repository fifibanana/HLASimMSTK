README:
Michal Dudka
Filip Banaszek


Topic:
Aplikacja symuluje sklep z dwoma rodzajami kas: kasami zwykłymi oraz kasami „uprzywilejowanymi” (do 5 produktów)
w których obsługa jest znacząco szybsza. 
Klienci w losowym czasie przychodzą do sklepu, dokonują zakupów (n% klientów kupuje do 5 produktów)
kierują się do odpowiedniej kolejki, płacą gotówką i wychodzą. 
Zaproponować taką liczbę kas, żeby kolejka nie przekroczyła zadanej długości (L). 

1. W modelu do naszego zadania zrobilismy 4 federatów:
    - Kasy
    - Kolejki
    - Klienci
    - Produkty
2. Na początku klas federatów istnieje możliwość regulacji parametrów symulacji:
    - private final double federationTimeLimit = 300; regulacja długości trwania symulacji
    - W klasie kolejka:
         - private int maxQLength = 20 - regulacja maksymalnej długości kolejki L
    - W klasie klienci:
      - // Poisson distribution parameter to regulate percent of client buying under 5 products
      // at a = 3.5 about 60% clients purchase under 5 products
      private double n = 3.5;
      // czas przybycia klienta z zakresu 2-10
      private int timeToNextClient = 8 ;
    - W klasie produkty:
      - // Poisson distribution parameter to regulate percent of client buying under 5 products
      // at a = 3.5 about 60% clients purchase under 5 products
      private double n = 3.5;
    
