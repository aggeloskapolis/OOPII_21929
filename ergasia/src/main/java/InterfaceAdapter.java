import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class InterfaceAdapter {

    public static void main(String[] args) {

        JSONParser parser = new JSONParser();
        ObjectMapper objectMapper=new ObjectMapper();

        try {
            Object obj = parser.parse(new FileReader("Traveller.json"));

            //JSONArray array = (JSONArray) obj;
            JSONArray array = (JSONArray) obj;
            //JSONObject jsonObject = (JSONObject) array.get(0);

            /*String name = (String) jsonObject.get("name");
            System.out.println(name);

            String city = (String) jsonObject.get("city");
            System.out.println(city);

            String job = (String) jsonObject.get("job");
            System.out.println(job);*/

            // loop array
            //JSONArray cars = (JSONArray) jsonObject.get("cars");
            Iterator iterator = array.iterator();
            /*while (iterator.hasNext()) {
                //System.out.println(iterator.next().toString());
                //System.out.println(array.get(1));
            }*/
            String str=obj.toString();
            //System.out.println(str);

            ObjectMapper mapper = new ObjectMapper();
            mapper.enableDefaultTyping();
            String jsonDataString = mapper.writeValueAsString(array);
            Traveller data = mapper.readValue(jsonDataString,Traveller.class);



            //Traveller trv=objectMapper.readValue((DataInput) array,Traveller.class);

            ArrayList<Object> list = new ArrayList<Object>();
            ArrayList<Traveller> list2 = new ArrayList<Traveller>();
            //list.add(data);
            System.out.println(list);

                /*for (int i=0, l=array.size(); i<l; i++){
                    list.add(array.get(i));
                }*/

           /* for (int i=0, l=array.size(); i<l; i++){
                //System.out.println(list.get(i));
                // list2.add((Traveller) list.get(i));
                //list.add(array.get(i));
            }*/






        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}