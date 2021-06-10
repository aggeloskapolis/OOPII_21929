import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;


public class Database {
    static Connection db_con_obj = null; //A connection (session) with a specific database. SQL statements are executed and results are returned within the context
    //of a connection. A Connection object's database is able to provide information describing its tables, its supported SQL grammar, its stored procedures,
    //the capabilities of this connection, and so on. This information is obtained with the getMetaData method.
    static PreparedStatement db_prep_obj = null;//An object that represents a precompiled SQL statement.
    //A SQL statement is precompiled and stored in a PreparedStatement object. This object can then be used to efficiently execute this statement multiple times.

    public void makeJDBCConnection() {

        try {//We check that the DB Driver is available in our project.
            Class.forName("oracle.jdbc.driver.OracleDriver"); //This code line is to check that JDBC driver is available. Or else it will throw an exception. Check it with 2.
            System.out.println("Congrats - Seems your oracle JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
            e.printStackTrace();
            return;
        }

        try {
            // DriverManager: The basic service for managing a set of JDBC drivers.	 //We connect to a DBMS.
            db_con_obj = DriverManager.getConnection("jdbc:oracle:thin:@oracle12c.hua.gr:1521:orcl", "it21929", "IT21929");// Returns a connection to the URL.
            //Attempts to establish a connection to the given database URL. The DriverManager attempts to select an appropriate driver from the set of registered JDBC drivers.
            if (db_con_obj != null) {
                System.out.println("Connection Successful! Enjoy. Now it's time to CRUD data. ");

            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.out.println("Oracle Connection Failed!");
            e.printStackTrace();
            return;
        }

    }

    //Read city data from db
    public AllCities ReadData() throws SQLException {
        db_prep_obj = db_con_obj.prepareStatement("select * from cities");
        ResultSet rs = db_prep_obj.executeQuery();
        Vector<Integer> terms_city_vector = new Vector<>();
        Vector<Double> geodesic_city_vector = new Vector<>();


        AllCities allCities = new AllCities();
        Database db = new Database();
        int term_1 = 0;
        int term_2 = 0;
        int term_3 = 0;
        int term_4 = 0;
        int term_5 = 0;
        int term_6 = 0;
        int term_7 = 0;
        int term_8 = 0;
        int term_9 = 0;
        int term_10 = 0;

        double lat = 0;
        double lon = 0;

        String city_name = null;
        while (rs.next()) {
            City newCity = new City();

            city_name = rs.getString("CITY_NAME_COUNTRY_INITIALS");
            lat = rs.getDouble("LAT");
            lon = rs.getDouble("LON");
            term_1 = rs.getInt("TERM_1");
            term_2 = rs.getInt("TERM_2");
            term_3 = rs.getInt("TERM_3");
            term_4 = rs.getInt("TERM_4");
            term_5 = rs.getInt("TERM_5");
            term_6 = rs.getInt("TERM_6");
            term_7 = rs.getInt("TERM_7");
            term_8 = rs.getInt("TERM_8");
            term_9 = rs.getInt("TERM_9");
            term_10 = rs.getInt("TERM_10");

            terms_city_vector.add(term_1);
            terms_city_vector.add(term_2);
            terms_city_vector.add(term_3);
            terms_city_vector.add(term_4);
            terms_city_vector.add(term_5);
            terms_city_vector.add(term_6);
            terms_city_vector.add(term_7);
            terms_city_vector.add(term_8);
            terms_city_vector.add(term_9);
            terms_city_vector.add(term_10);

            geodesic_city_vector.add(lat);
            geodesic_city_vector.add(lon);
            newCity.setTerms_vector(terms_city_vector);
            newCity.setGeodesic_vector(geodesic_city_vector);

            allCities.updateCities(city_name, newCity);
        }

        return allCities;


    }

    //Write cities to database
    public void writeCities(AllCities cities) {

        HashMap<String, City> citiesToBeWritten = cities.getCities();

        Iterator it = citiesToBeWritten.entrySet().iterator();
        if (it != null) {
            while (it.hasNext()) {

                HashMap.Entry pair = (Map.Entry) it.next();
                City city = (City) pair.getValue();
                String nameOfCity = (String) pair.getKey();
                addDataToDB(nameOfCity,
                        city.getGeodesic_vector().get(0),
                        city.getGeodesic_vector().get(1),
                        city.getTerms_vector().get(0),
                        city.getTerms_vector().get(1),
                        city.getTerms_vector().get(2),
                        city.getTerms_vector().get(3),
                        city.getTerms_vector().get(4),
                        city.getTerms_vector().get(5),
                        city.getTerms_vector().get(6),
                        city.getTerms_vector().get(7),
                        city.getTerms_vector().get(8),
                        city.getTerms_vector().get(9));
            }
        }

    }

    //Add city data to db
    public void addDataToDB(String cityNameInitials, double lat, double lon, int term_1, int term_2, int term_3, int term_4, int term_5, int term_6,
                            int term_7, int term_8, int term_9, int term_10) {

        try {
            String insertQueryStatement = "INSERT  INTO  CITIES  VALUES  (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            db_prep_obj = db_con_obj.prepareStatement(insertQueryStatement);
            db_prep_obj.setString(1, cityNameInitials);//.setInt(1, newKey);//.setString
            db_prep_obj.setDouble(2, lat);//.setInt(2, year);
            db_prep_obj.setDouble(3, lon);
            db_prep_obj.setInt(4, term_1);
            db_prep_obj.setInt(5, term_2);
            db_prep_obj.setInt(6, term_3);
            db_prep_obj.setInt(7, term_4);
            db_prep_obj.setInt(8, term_5);
            db_prep_obj.setInt(9, term_6);
            db_prep_obj.setInt(10, term_7);
            db_prep_obj.setInt(11, term_8);
            db_prep_obj.setInt(12, term_9);
            db_prep_obj.setInt(13, term_10);
            // execute insert SQL statement Executes the SQL statement in this PreparedStatement object, which must be an SQL Data Manipulation Language (DML) statement
            int numRowChanged = db_prep_obj.executeUpdate(); //either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
            System.out.println("Rows " + numRowChanged + " changed.");

        } catch (

                SQLException e) {
            e.printStackTrace();
        }
    }
}