import java.util.*;

public class CommandProcessor {
    //Metoda ce intoarce un numar utilizat pentru a decide ce comanda sa se execute
    public static int commandNumber(String command){
        StringBuilder builder = new StringBuilder(command);
        if (builder.indexOf("loadcsv") != -1){
            return 1;
        }
        if (builder.indexOf("savecsv") != -1){
            return 2;
        }
        if (builder.indexOf("exit") != -1){
            return 3;
        }
        if (builder.indexOf("quit") != -1){
            return 4;
        }
        if (builder.indexOf("listmanufacturers") != -1){
            return 5;
        }
        if (builder.indexOf("listproductsbymanufacturer") != -1){
            return 6;
        }
        if (builder.indexOf("listproducts") != -1){
            return 7;
        }
        if (builder.indexOf("showproduct") != -1){
            return 8;
        }
        if (builder.indexOf("listcurrencies") != -1){
            return 9;
        }
        if (builder.indexOf("getstorecurrency") != -1){
            return 10;
        }
        if (builder.indexOf("addcurrency") != -1){
            return 11;
        }
        if (builder.indexOf("updateparity") != -1){
            return 12;
        }
        if (builder.indexOf("setstorecurrency") != -1){
            return 13;
        }
        if (builder.indexOf("listdiscounts") != -1){
            return 14;
        }
        if (builder.indexOf("addiscount") != -1){
            return 15;
        }
        if (builder.indexOf("applydiscount") != -1){
            return 16;
        }
        if (builder.indexOf("calculatetotal") != -1){
            return 17;
        }
        if (builder.indexOf("savestore") != -1){
            return 18;
        }
        if (builder.indexOf("loadstore") != -1){
            return 19;
        }
        return 0;
    }

    //Metoda ce extrage primul parametru al unei comenzi
    public static String firstParameter(String command){
        StringTokenizer tokens = new StringTokenizer(command);
        //Primul token reprezinta numele comenzii, el fiind ignorat
        tokens.nextToken();
        return tokens.nextToken();
    }

    //Metoda ce extrage toti parametrii unei comenzi
    public static List<String> allParameters(String command){
        List<String> parameters = new ArrayList<String>();
        StringTokenizer tokens = new StringTokenizer(command);
        tokens.nextToken();
        //Cat timp exista parametri, acestia sunt pusi in lista
        while (tokens.hasMoreTokens()){
            parameters.add(tokens.nextToken());
        }
        return parameters;
    }

    //Metoda care extrage toti parametrii pentru functia addiscount
    public static List<String> allParametersForDiscount(String command){
        List<String> parameters = new ArrayList<String>();
        StringTokenizer tokens = new StringTokenizer(command);
        tokens.nextToken();
        //Se adauga primii doi parametri in lista
        parameters.add(tokens.nextToken());
        parameters.add(tokens.nextToken());
        //Cum numele discount-ului este intre ghilimele, se va utiliza un StringTokenizer
        //cu delimitatorul "
        tokens = new StringTokenizer(command, "\"");
        tokens.nextToken();
        parameters.add(tokens.nextToken());
        return parameters;
    }

    //Metoda ce extrage denumirea unui producator pentru functia listproductsbymanufacturer
    public static String manufacturer(String command) {
        String manufacturer = "";
        StringTokenizer tokens = new StringTokenizer(command);
        tokens.nextToken();
        //Se concateneaza la sirul manufacturer toti tokeni obtinuti de dupa numele comenzii
        while (tokens.hasMoreTokens()){
            manufacturer += tokens.nextToken() + " ";
        }
        //La final, se elimina spatiul suplimentar de la finalul sirului
        manufacturer = manufacturer.substring(0, manufacturer.length() - 1);
        return manufacturer;
    }
}
