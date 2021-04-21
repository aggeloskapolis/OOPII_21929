import java.util.*;

public abstract class Traveller {


    public abstract double calculateSimilarity(City cityObject);

    private Vector<Integer> travellersTermsVector;
    private Vector<Double> travellersGeodesicVector;


    public void setTravellersTermsVector(Vector<Integer> travellersTermsVector) {
        this.travellersTermsVector = travellersTermsVector;
    }

    public void setTravellersGeodesicVector(Vector<Double> travellersGeodesicVector) {
        this.travellersGeodesicVector = travellersGeodesicVector;
    }

    public Vector<Double> getTravellersGeodesicVector() {
        return travellersGeodesicVector;
    }

    public Vector<Integer> getTravellersTermsVector() {
        return travellersTermsVector;
    }

    public Traveller(Vector<Integer> a, Vector<Double> b) {
        travellersTermsVector = a;
        travellersGeodesicVector = b;

    }


    //Method that is used to count the distance detween the two geodesic vectors
    public static double distance(Vector travellersGeodesic, Vector cityGeodesic) {
        if ((travellersGeodesic.get(0) == cityGeodesic.get(0)) && (travellersGeodesic.get(1) == cityGeodesic.get(1))) {
            return 0;
        } else {
            double theta =  (double)travellersGeodesic.get(1) - (double) cityGeodesic.get(1);
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
    public  City compareCities(ArrayList<City> cities){
        double max=-1.0;
        City maxSim = null;
        for (int i=0;i<cities.size();i++){
            double similarity=calculateSimilarity(cities.get(i));
            if (similarity>max){
            maxSim=cities.get(i);
            max=similarity;
            }
        }

        return maxSim;

    }

    //Method that compares and finds the 5 most suitables citys for the traveller
    public  City[] compareCities(ArrayList<City> cities,int n){
        double max=-1.0;
        City[] maxSim = new City[n];
        HashMap<Double, City> similarities = new HashMap<Double, City>();
        for (int i=0;i<cities.size();i++){
           similarities.put(calculateSimilarity(cities.get(i)),cities.get(i));

        }
        Map<Double, City> map = new TreeMap<Double, City>(similarities);
        int a=0;
        for (int i=(map.size()-1);(n+i)>=map.size();i--){

            maxSim[a]=map.get(i);
            a++;
        }


        return maxSim;

    }

    public static void main(String[] args) {
        Vector<Integer> v3 = new Vector();
        Vector <Double> v4 = new Vector();
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

        City city=new City() ;
        city.setTerms_vector(v3);
        city.setGeodesic_vector(v4);
        Vector<Integer> v1 = new Vector();
        Vector <Double> v2 = new Vector();
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

        ArrayList<Traveller> travellers = new ArrayList<Traveller>();
        travellers.add(new ElderTraveller(v1,v2));
        travellers.add(new YoungTraveller(v1,v2));
        travellers.add(new MiddleTraveller(v1,v2));
        int freeTicketWinnerIndex=0;
        double max=-0.0;
        double sim=0.0;
        for (int i=0;i<travellers.size();i++){
            sim=travellers.get(i).calculateSimilarity(city);

            if ((sim)>max){
                max=sim;
                freeTicketWinnerIndex=i;
            }


        }
        System.out.println(freeTicketWinnerIndex);


    }




}




    class YoungTraveller extends Traveller {

    double p=0.95;
    double sim=0.0;

    public double similarityTerms(Vector travellerTerms,Vector cityTerms){
        int a=0;

        for (int i=0;i<travellerTerms.size();i++){
            a+=Math.pow(((int)travellerTerms.get(i)-(int)cityTerms.get(i)),2);

        }
        return 1/(1+Math.sqrt(a));

    }



        public double calculateSimilarity(City cityObject) {

            sim=p*similarityTerms(getTravellersTermsVector(), cityObject.terms_vector)+(1-p)*geodesicSimilarity(getTravellersGeodesicVector(),cityObject.geodesic_vector);

            return sim;
        }

        public YoungTraveller(Vector<Integer> a, Vector<Double> b) {

            super(a, b);
        }

    }

    class MiddleTraveller extends Traveller {

        double p=0.50;
        double sim=0.0;

    public double similarityTerms(Vector travellerTerms,Vector cityTerms){
        double dotProduct=0.0;
        double normA=0.0;
        double normB=0.0;
        for (int i=0;i<travellerTerms.size();i++){
            dotProduct+=(int)travellerTerms.get(i)*(int)cityTerms.get(i);
            normA+=Math.pow((int)travellerTerms.get(i),2);
            normB+=Math.pow((int)cityTerms.get(i),2);

        }
        return dotProduct/(Math.sqrt(normA)*Math.sqrt(normB));
    }


        public MiddleTraveller(Vector<Integer> a, Vector<Double> b) {

            super(a, b);
        }

        public double calculateSimilarity(City cityObject) {

            sim=p*similarityTerms(getTravellersTermsVector(), cityObject.terms_vector)+(1-p)*geodesicSimilarity(getTravellersGeodesicVector(),cityObject.geodesic_vector);


            return sim;

        }
    }

    class ElderTraveller extends Traveller {

        double p=0.05;
        double sim=0.0;

    public static <T> double similarityTerms(Vector travellerTerms,Vector cityTerms ){

        Set<T> union=new HashSet<>(travellerTerms);
        union.addAll(cityTerms);

        Set<T> intersection=new HashSet<>(travellerTerms);
        intersection.addAll(cityTerms);

        return 1.0-(double) intersection.size()/union.size();

    }


        public ElderTraveller(Vector<Integer> a, Vector<Double> b) {

            super(a, b);
        }


        public double calculateSimilarity(City cityObject) {

            sim=p*similarityTerms(getTravellersTermsVector(), cityObject.terms_vector)+(1-p)*geodesicSimilarity(getTravellersGeodesicVector(),cityObject.geodesic_vector);


            return sim;
        }

    }

