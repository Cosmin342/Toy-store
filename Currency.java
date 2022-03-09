import java.io.Serializable;

public class Currency implements Serializable{
    private String name, symbol;
    //parityToEur reprezinta cati euro valoreaza o unitate din moneda respectiva
    private double parityToEur;

    public Currency() {
    }

    //Constructorul cu parametri initializeaza cele 3 campuri ale currency
    public Currency(String name, String symbol, double parityToEur) {
        this.name = name;
        this.symbol = symbol;
        this.parityToEur = parityToEur;
    }

    public double getParityToEur() {
        return parityToEur;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    //Metoda ce modifica paritatea la euro a monedei
    public void updateParity(double parityToEur){
        this.parityToEur = parityToEur;
    }

    @Override
    //Metoda equals verifica daca doua monede sunt egale pe baza numelui, simbolului si
    //paritatii la euro
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Double.compare(currency.parityToEur, parityToEur) == 0 &&
                name.equals(currency.name) && symbol.equals(currency.symbol);
    }
}
