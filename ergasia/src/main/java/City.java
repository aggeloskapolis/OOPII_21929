import com.fasterxml.jackson.databind.ObjectMapper;
import weather.OpenWeatherMap;
import wikipedia.MediaWiki;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Vector;


public class City {

    Vector<Integer> terms_vector;
    Vector<Double> geodesic_vector;
    public MediaWikiThread mediaWikiThread;
    public OpenWeatherMapThread openWeatherMapThread;


    public Vector<Integer> getTerms_vector() {
        return terms_vector;
    }

    public void setTerms_vector(Vector<Integer> terms_vector) {
        this.terms_vector = terms_vector;
    }

    public Vector<Double> getGeodesic_vector() {
        return geodesic_vector;
    }

    public void setGeodesic_vector(Vector<Double> geodesic_vector) {
        this.geodesic_vector = geodesic_vector;
    }

   /* //Method that is used to take the Wikipedia article
    public static MediaWiki dataRetrieve(String name) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        MediaWiki
                mediaWiki_obj = mapper.readValue(new URL("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles=" + name + "&format=json&formatversion=2"), MediaWiki.class);

        return mediaWiki_obj;

    }
    //Method that is used to take data from OpenWeatherMap
    public static OpenWeatherMap dataRetrieve(String name, String country, String appid) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        OpenWeatherMap weather_obj = mapper.readValue(new URL("http://api.openweathermap.org/data/2.5/weather?q=" + name + "," + country + "&APPID=" + appid + ""), OpenWeatherMap.class);

        return weather_obj;

    }*/

    //Thread that is used to take the Wikipedia article
    class MediaWikiThread implements Runnable {

        private String name;
        private MediaWiki mediaWiki;
        ObjectMapper mapper = new ObjectMapper();

        @Override
        public void run() {
            while (true) {

                    System.out.println("run MediaWikiThread");
                    try {
                        mediaWiki = mapper.readValue(new URL("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles=" + name + "&format=json&formatversion=2"), MediaWiki.class);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

            }
        }

        public MediaWiki getValue() {

            return mediaWiki;
        }

        public void setValue(String name) {

            this.name = name;
        }
    }

    //Thread that is used to take data from OpenWeatherMap
    class OpenWeatherMapThread implements Runnable {

        private String name;
        private String country;
        private String appid;
        private OpenWeatherMap weather_obj;
        ObjectMapper mapper = new ObjectMapper();

        @Override
        public void run() {
            while (true) {
                if (name!=null) {

                    System.out.println("run OpenWeatherMapThread");
                    try {
                        weather_obj = mapper.readValue(new URL("http://api.openweathermap.org/data/2.5/weather?q=" + name + "," + country + "&APPID=" + appid + ""), OpenWeatherMap.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
        public OpenWeatherMap getValue() {
            name= null;
            return weather_obj;
        }
        public void setValues(String name, String country, String appid) {

            this.name = name;
            this.country = country;
            this.appid = appid;
        }
    }


    public void startThreads(){
        mediaWikiThread = new MediaWikiThread();
        new Thread(mediaWikiThread).start();
        openWeatherMapThread = new OpenWeatherMapThread();
        new Thread(openWeatherMapThread).start();
    }

    public City retieveOpenData(String town, String country) throws IOException {


        String appid = "aa18c171c10f233cc08a1e89121c0dad";
        String[] name = new String[]{"cafe", "museum", "history", "sea", "bike", "car", "house", "mountain", "bar", "lake"};

        Vector<Integer> terms = new Vector<>();
        Vector<Double> geodesic = new Vector<>();


        MediaWiki mediaWiki_obj;
        OpenWeatherMap weather_obj;

        openWeatherMapThread.setValues(town,country,appid);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        weather_obj = openWeatherMapThread.getValue();
        geodesic.add(weather_obj.getCoord().getLat());
        geodesic.add(weather_obj.getCoord().getLon());

        int count = 0;

        for (int i = 0; i < 10; i++) {
            mediaWikiThread.setValue(name[i]);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mediaWiki_obj = mediaWikiThread.getValue();

            count = CountWords.countCriterionfCity(mediaWiki_obj.getQuery().getPages().get(0).getExtract(), town);
            terms.add(count);

        }

        City city = new City();
        city.setTerms_vector(terms);
        city.setGeodesic_vector(geodesic);

        return city;
    }

}
//Class for getting City map collection
class AllCities {
    HashMap<String, City> Cities = new HashMap<String, City>();

    public HashMap<String, City> getCities() {
        return Cities;
    }

    public void updateCities(String key, City value) {
        Cities.put(key, value);
        System.out.println("update cities" + Cities.size());
    }
}
