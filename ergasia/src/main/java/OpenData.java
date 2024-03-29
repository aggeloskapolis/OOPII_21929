import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import weather.OpenWeatherMap;
import wikipedia.MediaWiki;


import java.io.IOException;
import java.net.URL;

/**
 * City description and weather information using OpenData with Jackson JSON processor.
 *
 * @author John Violos
 * @version 1.0
 * @since 29-2-2020
 */
public class OpenData {

    /**
     * Retrieves weather information, geotag (lan, lon) and a Wikipedia article for a given city.
     *
     * @param city    The Wikipedia article and OpenWeatherMap city.
     * @param country The country initials (i.e. gr, it, de).
     * @param appid   Your API key of the OpenWeatherMap.
     */
    public static void RetrieveData(String city, String country, String appid) throws IOException, JsonMappingException {
        ObjectMapper mapper = new ObjectMapper();
        OpenWeatherMap weather_obj = mapper.readValue(new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&APPID=" + appid + ""), OpenWeatherMap.class);
        //System.out.println(city + " temperature: " + (weather_obj.getMain()).getTemp());
        //System.out.println(city + " lat: " + weather_obj.getCoord().getLat() + " lon: " + weather_obj.getCoord().getLon());
        MediaWiki mediaWiki_obj = mapper.readValue(new URL("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles=" + city + "&format=json&formatversion=2"), MediaWiki.class);
        System.out.println(city + " Wikipedia article: " + mediaWiki_obj.getQuery().getPages().get(0).getExtract());
    }



    public static void main(String[] args) throws IOException, JsonMappingException {
        String appid = "aa18c171c10f233cc08a1e89121c0dad";
        RetrieveData("Rome", "it", appid);
        RetrieveData("Athens", "gr", appid);
        RetrieveData("Corfu", "gr", appid);
        RetrieveData("Berlin", "de", appid);

    }

}