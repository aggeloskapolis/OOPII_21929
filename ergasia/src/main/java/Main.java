import java.io.IOException;
import java.sql.SQLException;


public class Main {

    public static void main(String[] args) throws IOException, SQLException {

        Traveller trv = new Traveller() {
            @Override
            public double calculateSimilarity(City cityObject) {
                return 0;
            }
        };
        //Main process
        trv.mainprc();
    }
}





