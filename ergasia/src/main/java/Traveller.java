import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;


public abstract class Traveller {

    private String name;

    public abstract double calculateSimilarity(City cityObject);

    private Vector<Integer> travellersTermsVector;
    private Vector<Double> travellersGeodesicVector;
    private long timestamp;
    //Traveller's recommended city
    private String visit;


    public Vector<Double> getTravellersGeodesicVector() {
        return travellersGeodesicVector;
    }

    public Vector<Integer> getTravellersTermsVector() {
        return travellersTermsVector;
    }

    public void setTravellersTermsVector(Vector<Integer> travellersTermsVector) {
        this.travellersTermsVector = travellersTermsVector;
    }

    public void setTravellersGeodesicVector(Vector<Double> travellersGeodesicVector) {
        this.travellersGeodesicVector = travellersGeodesicVector;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getName() {
        return name;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public String getVisit() {
        return visit;
    }

    //Method that is used to count the distance detween the two geodesic vectors
    public static double distance(Vector travellersGeodesic, Vector cityGeodesic) {
        if ((travellersGeodesic.get(0) == cityGeodesic.get(0)) && (travellersGeodesic.get(1) == cityGeodesic.get(1))) {
            return 0;
        } else {
            double theta = (double) travellersGeodesic.get(1) - (double) cityGeodesic.get(1);
            double dist = Math.sin(Math.toRadians((double) travellersGeodesic.get(0))) * Math.sin(Math.toRadians((double) cityGeodesic.get(0))) + Math.cos(Math.toRadians((double) travellersGeodesic.get(0))) * Math.cos(Math.toRadians((double) cityGeodesic.get(0))) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            return (dist);
        }
    }

    public static double geodesicSimilarity(Vector travellersGeodesic, Vector cityGeodesic) {
        double n;
        n = Math.log(2 / (2 - (distance(travellersGeodesic, cityGeodesic) / 13016.410)));
        return n;
    }

    //Method that compares and finds the most suitable city for the traveller
    public String compareCities(HashMap<String, City> Cities) {
        double max = -1.0;
        City maxSim = null;
        String maxCity = null;
        System.out.println("cities size in compare cities" + Cities.size());
        Iterator it = Cities.entrySet().iterator();
        if (it != null) {
            while (it.hasNext()) {

                HashMap.Entry pair = (Map.Entry) it.next();
                City city = (City) pair.getValue();
                double similarity = calculateSimilarity(city);
                if (similarity > max) {
                    maxSim = city;
                    maxCity = (String) pair.getKey();
                    max = similarity;
                }

            }
        }
        return maxCity;
    }

    //Method that compares and finds the 5 most suitables citys for the traveller
    public City[] compareCities(ArrayList<City> cities, int n) {
        double max = -1.0;
        City[] maxSim = new City[n];
        HashMap<Double, City> similarities = new HashMap<Double, City>();
        for (int i = 0; i < cities.size(); i++) {
            similarities.put(calculateSimilarity(cities.get(i)), cities.get(i));

        }
        Map<Double, City> map = new TreeMap<Double, City>(similarities);
        int a = 0;
        for (int i = (map.size() - 1); (n + i) >= map.size(); i--) {

            maxSim[a] = map.get(i);
            a++;
        }
        return maxSim;
    }

    public static void writeJSON(ArrayList<Traveller> in_arraylist) throws JsonGenerationException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();
        AllTravellers data = new AllTravellers();
        data.setCollectionAllTravellers(in_arraylist);
        mapper.writeValue(new File("Traveller.json"), data);
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Traveller> readJSON() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();
        AllTravellers data = mapper.readValue(new File("Traveller.json"), AllTravellers.class);
        return data.getCollectionAllTravellers();
    }
    // Sort by name,timestamp
    class sortByTimestamb implements Comparator<Traveller> {
        public int compare(Traveller a, Traveller b) {

            String x1 = a.getName();
            String x2 = b.getName();
            if (x1 != null && x2 != null) {
                int comp = x1.trim().compareTo(x2.trim());
                if (comp != 0) {
                    return comp;
                }
            }
            return (int) (a.timestamp - b.timestamp);

        }

    }
    //Print travellers
    public void printTravellers(ArrayList<Traveller> travellers) {

        Collections.sort(travellers, new sortByTimestamb());

        String oldName = "";
        System.out.println("         Travellers      ");
        System.out.println("////////////////////////////");
        //No dublicate traveller names
        for (int i = 0; i < travellers.size(); i++) {
            if (travellers.get(i).getName() != null) {
                if (travellers.get(i).getName().trim().compareTo(oldName.trim()) != 0) {

                    System.out.println(travellers.get(i).getName() + " " + travellers.get(i).getTimestamp());

                    oldName = travellers.get(i).getName();
                }
            }
        }

    }


    public void mainprc() throws IOException, SQLException {

        City city = new City();
        ArrayList<Traveller> travellers = new ArrayList<Traveller>();
        AllCities allCities = new AllCities();

        //Read travellers from JSON file
        travellers = readJSON();
        //Initiate cities Database
        Database db = new Database();
        db.makeJDBCConnection();

        Vector<Integer> v3 = new Vector();
        Vector<Double> v4 = new Vector();
        v3.add(0);
        v3.add(9);
        v3.add(8);
        v3.add(7);
        v3.add(6);
        v3.add(5);
        v3.add(4);
        v3.add(3);
        v3.add(2);
        v3.add(1);

        v4.add(7.345);
        v4.add(13.888);

        City city1 = new City();
        city1.setTerms_vector(v3);
        city1.setGeodesic_vector(v4);
        Vector<Integer> v1 = new Vector();
        Vector<Double> v2 = new Vector();
        v1.add(1);
        v1.add(2);
        v1.add(3);
        v1.add(4);
        v1.add(5);
        v1.add(6);
        v1.add(7);
        v1.add(8);
        v1.add(9);
        v1.add(0);
        v2.add(13.888);
        v2.add(7.345);

        //First time travellers data
        //This code is used only for first running
        //After that we read data from JSON file
       /* ElderTraveller elder = new ElderTraveller();
        elder.setTravellersGeodesicVector(v2);
        elder.setTravellersTermsVector(v1);
        elder.setTimestamp(date.getTime());
        elder.setName("giorgos");
        //Set the name of the recommended city
        elder.setVisit(elder.compareCities(allCities.getCities()));
        ElderTraveller elder2 = new ElderTraveller();
        elder2.setTravellersGeodesicVector(v4);
        elder2.setTravellersTermsVector(v3);
        elder2.setTimestamp(date.getTime());
        elder2.setName("giorgos");
        elder2.setVisit(elder.compareCities(allCities.getCities()));
        MiddleTraveller mdlie = new MiddleTraveller();
        mdlie.setTravellersGeodesicVector(v4);
        mdlie.setTravellersTermsVector(v3);
        mdlie.setTimestamp(date.getTime());
        mdlie.setName("panos");
        mdlie.setVisit(elder.compareCities(allCities.getCities()));
        YoungTraveller young = new YoungTraveller();
        young.setTravellersGeodesicVector(v2);
        young.setTravellersTermsVector(v1);
        young.setTimestamp(date.getTime());
        ;
        young.setName("katos");
        young.setVisit(elder.compareCities(allCities.getCities()));
        //travellers.add(elder);
        //travellers.add(elder2);
        //travellers.add(young);
        //travellers.add(mdlie);*/
        //Find traveller who wins free ticket
        int freeTicketWinnerIndex = 0;
        double max = -0.0;
        double sim = 0.0;
        for (int i = 0; i < travellers.size(); i++) {
            sim = travellers.get(i).calculateSimilarity(city1);

            if ((sim) > max) {
                max = sim;
                freeTicketWinnerIndex = i;
            }


        }
        System.out.println("free ticket" + freeTicketWinnerIndex);
        //Check if city already exists
        boolean flag = false;
        String town = "athens";
        String country = "gr";

        for (int i = 0; i < allCities.getCities().size(); i++) {

            if (allCities.getCities().containsKey(town)) {
                flag = true;
            }
        }
        if (!flag) {
            //If not exists then city is added
            allCities.updateCities(town, city.retieveOpenData(town, country));
        }
        //Print travellers (χωρις διπλοτυπα)
        printTravellers(travellers);
        //Write cities to database
        db.writeCities(allCities);
        //Write travellers to JSON file
        writeJSON(travellers);

    }

}


class YoungTraveller extends Traveller {

    double p = 0.95;
    double sim = 0.0;

    public double similarityTerms(Vector travellerTerms, Vector cityTerms) {
        int a = 0;

        for (int i = 0; i < travellerTerms.size(); i++) {
            a += Math.pow(((int) travellerTerms.get(i) - (int) cityTerms.get(i)), 2);

        }
        return 1 / (1 + Math.sqrt(a));

    }


    @Override
    public double calculateSimilarity(City cityObject) {

        sim = p * similarityTerms(getTravellersTermsVector(), cityObject.terms_vector) + (1 - p) * geodesicSimilarity(getTravellersGeodesicVector(), cityObject.geodesic_vector);

        return sim;
    }

    @Override
    public void setTravellersGeodesicVector(Vector<Double> travellersGeodesicVector) {
        super.setTravellersGeodesicVector(travellersGeodesicVector);
    }

    @Override
    public void setTravellersTermsVector(Vector<Integer> travellersTermsVector) {
        super.setTravellersTermsVector(travellersTermsVector);
    }


}

class MiddleTraveller extends Traveller {

    double p = 0.50;
    double sim = 0.0;

    public double similarityTerms(Vector travellerTerms, Vector cityTerms) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < travellerTerms.size(); i++) {
            dotProduct += (int) travellerTerms.get(i) * (int) cityTerms.get(i);
            normA += Math.pow((int) travellerTerms.get(i), 2);
            normB += Math.pow((int) cityTerms.get(i), 2);

        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    @Override
    public void setTravellersTermsVector(Vector<Integer> travellersTermsVector) {
        super.setTravellersTermsVector(travellersTermsVector);
    }

    @Override
    public void setTravellersGeodesicVector(Vector<Double> travellersGeodesicVector) {
        super.setTravellersGeodesicVector(travellersGeodesicVector);
    }


    @Override
    public double calculateSimilarity(City cityObject) {

        sim = p * similarityTerms(getTravellersTermsVector(), cityObject.terms_vector) + (1 - p) * geodesicSimilarity(getTravellersGeodesicVector(), cityObject.geodesic_vector);


        return sim;

    }
}

class ElderTraveller extends Traveller {

    double p = 0.05;
    double sim = 0.0;

    public <T> double similarityTerms(Vector travellerTerms, Vector cityTerms) {

        Set<T> union = new HashSet<>(travellerTerms);
        union.addAll(cityTerms);

        Set<T> intersection = new HashSet<>(travellerTerms);
        intersection.addAll(cityTerms);

        return 1.0 - (double) intersection.size() / union.size();

    }


    @Override
    public void setTravellersGeodesicVector(Vector<Double> travellersGeodesicVector) {
        super.setTravellersGeodesicVector(travellersGeodesicVector);
    }

    @Override
    public void setTravellersTermsVector(Vector<Integer> travellersTermsVector) {
        super.setTravellersTermsVector(travellersTermsVector);
    }


    @Override
    public double calculateSimilarity(City cityObject) {

        sim = p * similarityTerms(getTravellersTermsVector(), cityObject.terms_vector) + (1 - p) * geodesicSimilarity(getTravellersGeodesicVector(), cityObject.geodesic_vector);


        return sim;
    }

}

//Class for getting Arraylist Travellers
class AllTravellers {
    private ArrayList<Traveller> collectionAllTravellers;

    public ArrayList<Traveller> getCollectionAllTravellers() {
        return collectionAllTravellers;
    }

    public void setCollectionAllTravellers(ArrayList<Traveller> collectionAllTravellers) {
        this.collectionAllTravellers = collectionAllTravellers;
    }
}


