public class Helper {
    //Metoda ce converteste un string la double
    public static double convertPrice(String price){
        return Double.parseDouble(price);
    }

    //Metoda ce prelucreaza preturile citite din fisierul csv
    public static double processPrice(String price){
        StringBuilder build = new StringBuilder(price);
        build.deleteCharAt(0);
        //Daca apare - intr-un pret, trebuie pastrat doar primul pret (cel din-naintea -)
        if (build.indexOf(" -") != -1){
            int start = build.indexOf(" -");
            build.delete(start, build.toString().length() - 1);
        }
        //Dacaa apare , , aceasta este eliminata din pret
        if (build.indexOf(",") != -1){
            int start = build.indexOf(",");
            build.delete(start, start + 1);
        }
        //Se intoarce pretul convertit la double
        return Double.parseDouble(build.toString());
    }

    //Metoda ce elimina caracterul &nbsp din fisierul de intrare si intoarce un int reprezentand
    //nr de produse disponibile
    public static int processNumberAvailableInStock(String numberAvailableInStock){
        int availableInStock = 0;
        StringBuilder build = new StringBuilder(numberAvailableInStock);
        if (build.indexOf("\u00a0") != -1){
            build.delete(build.indexOf("\u00a0"), build.toString().length());
            availableInStock = Integer.parseInt(build.toString());
        }
        return availableInStock;
    }
}
