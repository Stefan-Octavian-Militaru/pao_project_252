package Utile;

import java.io.*;

public class CitesteFisier {

    public static String[] readDbInfoFromCsv(String fileName) throws IOException {
        InputStream is = CitesteFisier.class.getClassLoader().getResourceAsStream(fileName);
        if (is == null) {
            throw new FileNotFoundException("dbInfo.csv not found in resources");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String[] dbInfo = reader.readLine().split(",");

        return dbInfo;

    }


}
