package hla13.dupa.Kolejka;


public class Kolejka {
    private int numberOfClietnsInQueueA;
    private int numberOfClietnsInQueueB;
    private int numberOfProductsForClientA;
    private int numberOfProductsForClientB;
    private int maxNumberOfClients;
    private int zabranoZA = 1;
    private int zabranoZB = 1;

    private static Kolejka instance = null;

    private Kolejka() {
        numberOfClietnsInQueueA=0;
        numberOfClietnsInQueueB=0;
        numberOfProductsForClientA=0;
        numberOfProductsForClientB=0;
        maxNumberOfClients=20;
    }
    static public Kolejka getInstance()
    {
        if(instance==null) instance = new Kolejka();
        return instance;
    }

    public int getNumberOfClietnsInQueueA() {
        return numberOfClietnsInQueueA;
    }

    public int getNumberOfClietnsInQueueB() {
        return numberOfClietnsInQueueB;
    }

    public int getNumberOfProductsForClientA() {
        return numberOfProductsForClientA;
    }

    public int getNumberOfProductsForClientB() {
        return numberOfProductsForClientB;
    }

    public int getMaxNumberOfClients() {
        return maxNumberOfClients;
    }

    public void setNumberOfClietnsInQueueA(int numberOfClietnsInQueueA) {
        this.numberOfClietnsInQueueA = numberOfClietnsInQueueA;
    }

    public void setNumberOfClietnsInQueueB(int numberOfClietnsInQueueB) {
        this.numberOfClietnsInQueueB = numberOfClietnsInQueueB;
    }

    public void setNumberOfProductsForClientA(int numberOfProductsForClientA) {
        this.numberOfProductsForClientA = numberOfProductsForClientA;
    }

    public void setNumberOfProductsForClientB(int numberOfProductsForClientB) {
        this.numberOfProductsForClientB = numberOfProductsForClientB;
    }

    public void setMaxNumberOfClients(int maxNumberOfClients) {
        this.maxNumberOfClients = maxNumberOfClients;
    }

    public boolean addTo(int count, int noOfProducts)
    {
        if(this.numberOfClietnsInQueueA+numberOfClietnsInQueueB+count<=this.maxNumberOfClients) {
            //if(this.numberOfClietnsInQueueA>=this.numberOfClietnsInQueueB){
            if(count >= 5){
                //this.numberOfClietnsInQueueB += count;
                this.numberOfClietnsInQueueB += 1;
                System.out.println("Kolejka: Dodaję klienta do kolejki B");
            }
            else{
                //this.numberOfClietnsInQueueA += count;
                this.numberOfClietnsInQueueA += 1;
                System.out.println("Kolejka: Dodaję klienta do kolejki A");
            }
            //System.out.println("Kolejka: Przybyło " + count + " klientów. Aktualna liczba klientów: ");
            System.out.println("Kolejka: Przybył klient. Aktualna liczba klientów: ");
            System.out.println("W kolejce A: " + this.numberOfClietnsInQueueA);
            System.out.println("W kolejce B: " + this.numberOfClietnsInQueueB);

            return true;
        }
        else
        {
            System.out.println("Kolejka: Nie ma miejsca w kolejce dla " + count + " klientów");
            return false;
        }
    }

    public boolean addProductsTo(int count)
    {
        if(numberOfProductsForClientA == 0 && zabranoZA == 1){
            numberOfProductsForClientA = count%5 + 1;
            zabranoZA = 0;
        }
        if(numberOfProductsForClientB == 0 && zabranoZB == 1){
            numberOfProductsForClientB = count%5 + 6;
            zabranoZB = 0;
        }

        return true;
    }

    public boolean getFrom(int count)
    {
        boolean isTrue = false;
        if(numberOfClietnsInQueueA-count>=0) {
            if(numberOfProductsForClientA == 0){
                this.numberOfClietnsInQueueA-=count;
                System.out.println("Kolejka: Właśnie obsłużono klienta w kolejce A. Aktualnie w kolejce A jest "+ this.numberOfClietnsInQueueA + " klientów");
                isTrue = true;
                zabranoZA = 1;
            }
            else if(numberOfProductsForClientA >0){
                numberOfProductsForClientA--;
                System.out.println("Kolejka: Właśnie skasowano produkt klienta w kolejce A. Aktualnie liczba produktów klienta w kolejce A wynosi: "+ this.numberOfProductsForClientA + " produktów");
                zabranoZA = 0;
            }

        }
        else if(numberOfClietnsInQueueA-count<0)
        {
            System.out.println("Kolejka: W kolejce A nie ma klientów do obsłużenia.");
            isTrue = false;
        }
        if(numberOfClietnsInQueueB-count>=0) {
            if(numberOfProductsForClientB == 0){
                this.numberOfClietnsInQueueB-=count;
                System.out.println("Kolejka: Właśnie obsłużono klienta w kolejce B. Aktualnie w kolejce B jest "+ this.numberOfClietnsInQueueB + " klientów");
                isTrue = true;
                zabranoZB = 1;
            }
            else if(numberOfProductsForClientB >0){
                numberOfProductsForClientB--;
                System.out.println("Kolejka: Właśnie skasowano produkt klienta w kolejce B. Aktualnie liczba produktów klienta w kolejce B wynosi: "+ this.numberOfProductsForClientB + " produktów");
                zabranoZB = 0;
            }
        }
        else if(numberOfClietnsInQueueB-count<0)
        {
            System.out.println("Kolejka: W kolejce B nie ma klientów do obsłużenia.");
            isTrue = false;
        }
        return isTrue;

    }
}
