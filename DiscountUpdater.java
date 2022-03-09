import java.util.List;

public class DiscountUpdater {
    //Metoda care adauga un discount in lista de discount-uri a unui magazin
    public static void addDiscount(String command, Store store) {
        List<String> parameters = CommandProcessor.allParametersForDiscount(command);
        int i = 0;
        String type = null, name = null;
        double value = 0;
        //Se parcurge lista parametrilor si se extrage tipul discount-ului, valoarea acestuia
        //si numele in aceasta ordine
        for (String parameter:parameters){
            if (i == 0){
                type = parameter;
            }
            if (i == 1){
                value = Double.parseDouble(parameter);
            }
            if (i == 2){
                name = parameter;
            }
            i++;
        }
        Discount discount;
        //Daca discount-ul e de tip procentual, se creeaza un nou discount cu tipul PERCENTAGE_DISCOUNT
        if (type.equals("PERCENTAGE")){
            discount = new Discount(name, DiscountType.PERCENTAGE_DISCOUNT, value);
        }
        //Altfel, unul cu tipul FIXED_DISCOUNT
        else {
            discount = new Discount(name, DiscountType.FIXED_DISCOUNT, value);
        }
        //Se adauga discount-ul creat in store
        store.addDiscount(discount);
    }

    //Metoda ce aplica un discount tuturor produselor din magazin
    public static void applyDiscount(String command, Store store) {
        List<String> parameters = CommandProcessor.allParameters(command);
        String type = null;
        double value = 0;
        int i = 0;
        //Se extrag, pe rand, tipul si valoarea discount-ului cautat
        for (String parameter:parameters){
            if (i == 0){
                type = parameter;
            }
            if (i == 1){
                value = Double.parseDouble(parameter);
            }
            i++;
        }
        //Se cauta discount-ul si se aplica tuturor produselor din magazin
        store.findAndApply(type, value);
    }
}
