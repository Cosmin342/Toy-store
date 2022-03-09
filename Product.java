import java.io.Serializable;

public class Product implements Serializable {
    private String uniqueId, name;
    //manufacturer retine producatorul produsului
    private Manufacturer manufacturer;
    private double price;
    //quantity reprezinta nr de produse disponibile
    private int quantity;
    private Discount discount;

    public Product() {
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    @Override
    //Metoda equals compara doua produse pe baza variabilelor membru
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 && quantity == product.quantity &&
                uniqueId.equals(product.uniqueId) && name.equals(product.name) &&
                manufacturer.equals(product.manufacturer);
    }

    //Metoda returneaza un sir de caractere reprezentand informtiile despre produs, pentru
    //afisarea pretului utilizandu-se sirul symbol
    public String information(String symbol) {
        double price = this.price;
        if (this.discount != null){
            if (this.discount.getDiscountType() == DiscountType.FIXED_DISCOUNT){
                price -= this.discount.getValue();
            }
            else {
                price -= (this.discount.getValue() / 100) * price;
            }
        }
        return this.uniqueId + "," + this.name + "," + this.manufacturer.getName() + "," +
                symbol + String.format("%.2f", price) + "," + this.quantity;
    }
}
