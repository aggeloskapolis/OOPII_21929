import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import weather.OpenWeatherMap;
import wikipedia.MediaWiki;

import java.io.IOException;
import java.net.URL;
import java.util.Vector;



public class City {

    Vector<Integer> terms_vector ;
    Vector<Double> geodesic_vector ;

    public Vector<Integer> getTerms_vector() { return terms_vector; }

    public void setTerms_vector(Vector<Integer> terms_vector) {
        this.terms_vector = terms_vector;
    }

    public Vector<Double> getGeodesic_vector() {
        return geodesic_vector;
    }

    public void setGeodesic_vector(Vector<Double> geodesic_vector) {
        this.geodesic_vector = geodesic_vector;
    }

    //Method that is used to take the Wikipedia article
    public static MediaWiki dataRetrieve(String name) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        MediaWiki mediaWiki_obj = mapper.readValue(new URL("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles=" + name + "&format=json&formatversion=2"), MediaWiki.class);

        return mediaWiki_obj;

    }

    //Method that is used to take data from OpenWeatherMap
    public static OpenWeatherMap dataRetrieve(String name,String country,String appid) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        OpenWeatherMap weather_obj = mapper.readValue(new URL("http://api.openweathermap.org/data/2.5/weather?q=" + name + "," + country + "&APPID=" + appid + ""), OpenWeatherMap.class);

        return weather_obj;

    }

    public void main(String[] args) throws IOException, JsonMappingException {
        String appid = "aa18c171c10f233cc08a1e89121c0dad";

        String[] name= new String[]{"cafe", "museum", "history","sea","bike","car","house","mountain","bar","lake"};


        Vector<Integer> terms=new Vector<>();
        Vector<Double> geodesic=new Vector<>();


        MediaWiki mediaWiki_obj;
        OpenWeatherMap weather_obj;
        String town="rome";
        String country="it";
        int count=0;

        for (int i=0;i<10;i++) {
            mediaWiki_obj = dataRetrieve(name[i]);
            count=CountWords.countCriterionfCity(mediaWiki_obj.getQuery().getPages().get(0).getExtract(), town);
            terms.add(count);

        }

        weather_obj=dataRetrieve(town,country,appid);
        geodesic.add(weather_obj.getCoord().getLat());
        geodesic.add(weather_obj.getCoord().getLon());

        City city=new City();
        city.setTerms_vector(terms);
        city.setGeodesic_vector(geodesic);








    }






}
