import java.io.Serializable;

public class Manufacturer implements Serializable {
    private String name;
    //countProducts retine numarul de produse gestionate de acest producator
    private int countProducts = 1;

    public Manufacturer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCountProducts() {
        return countProducts;
    }

    public void setCountProducts(int countProducts) {
        this.countProducts = countProducts;
    }

    //Metoda equals o suprascrie pe cea din clasa Object, aceasta verificand daca doi producatori
    //sunt egali prin verificarea numelui
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        Manufacturer that = (Manufacturer) o;
        return name.equals(that.name);
    }
}
