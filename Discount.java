import java.io.Serializable;
import java.time.*;

public class Discount implements Serializable {
    private String name;
    //discountType contine una din cele doua constante din enum-ul DiscountType
    private DiscountType discountType;
    private double value;
    //lastDateApplied reprezinta data la care se aplica discount-ul
    private LocalDateTime lastDateApplied = null;

    //Constructorul cu paramteri initializeaza campurile name, discountType si value
    public Discount(String name, DiscountType discountType, double value) {
        this.name = name;
        this.discountType = discountType;
        this.value = value;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public double getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getLastDateApplied() {
        return lastDateApplied;
    }

    //Metoda ce preia data si ora curenta pentru a seta lastDateApplied
    public void setAsAppliedNow(){
        lastDateApplied = LocalDateTime.now();
    }

    @Override
    //Metoda ce verifica daca doua discount-uri sunt egale pe baza tipului, a numelui
    //si a valorii
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return Double.compare(discount.value, value) == 0 &&
                name.equals(discount.name) && discountType == discount.discountType;
    }
}
