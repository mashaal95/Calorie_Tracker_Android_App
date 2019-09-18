package com.example.calorie_tracker_final_v2;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class UrlHttp {
    private static final String BASE_URL =
            "http://10.1.1.83:8080/Calorie_Tracker_1/webresources/restful_web_serv.";
    static final String BASE_URL_for_Maps = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
    static List<JSONObject> fitnessAreas = new ArrayList<>();


    public static boolean getUserName(String username, String password) {

        String methodPath = "credential/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        Boolean checker = false;
        String passwordToHash = "";
        String hashtext="";

//Making HTTP request

        try {
            url = new URL(BASE_URL + methodPath + "findByUsername/" +username);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
//Read the response
            int i = conn.getResponseCode();

            Scanner inStream;
            inStream = new Scanner(conn.getInputStream());
//read the input stream and store it as string
            while(inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }

            JSONArray jsonArray = new JSONArray(textResult);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            textResult = ((JSONObject) jsonObject).getString("passwordHash");
            if(password.equals(textResult)) {
                checker = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return checker;
    }

    public static String getUserFirstName(String username) {

        String methodPath = "credential/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        String second="";
//Making HTTP request

        try {
            url = new URL(BASE_URL + methodPath + "findByUserDetail/" +username);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
//Read the response
            int i = conn.getResponseCode();
            Scanner inStream;
            if (i==200 || i==204)
                inStream = new Scanner(conn.getInputStream());
            else
                inStream = new Scanner(conn.getErrorStream());
//read the input stream and store it as string
            while(inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }

            JSONArray jsonArray = new JSONArray(textResult);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            second = ((JSONObject) jsonObject).getString("firstName");

            System.out.print(textResult);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return second;
    }


    public static String enterUserInfo(JSONObject userInfo) {
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "userdetail";
        try {
            Gson gson = new Gson();
            String stringUserJson = userInfo.toString();
            url = new URL(BASE_URL + methodPath);
            //open the connect)ion
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            // conn.setFixedLengthStreamingMode(stringUserJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Send the POST out
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(stringUserJson);
            out.flush();
            out.close();
            int a = conn.getResponseCode();

            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return "";
    }

    public static int getUserId() {

        String methodPath = "userdetail";
        int count=0;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
//Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
//read the input stream and store it as string
            while (inStream.hasNextLine())
                textResult += inStream.nextLine();
            JSONArray jsonArray = new JSONArray(textResult);
            JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length()-1);
            //count = ((JSONObject) jsonObject).getInt("userId");
            // count = (Integer) ((JSONObject) new JSONArray(textResult).getJSONObject(new JSONArray(textResult).length() - 1)).get("userId") +1;
            count= jsonArray.length()+1;


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return count;
    }

    public static String enterUserCredentials(JSONObject userInfo) {
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "credential";
        try {
            Gson gson = new Gson();
            String stringUserJson = userInfo.toString();
            url = new URL(BASE_URL + methodPath);
            //open the connect)ion
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            // conn.setFixedLengthStreamingMode(stringUserJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Send the POST out
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(stringUserJson);
            out.flush();
            out.close();
            int a = conn.getResponseCode();

            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return "";

    }

    public static List<JSONObject> addUser(String latitude,String longitude) {
        String methodPath = latitude+","+longitude+"&radius=5000&type=park&keyword=park&key=AIzaSyA2CyTXsyrnLUi0XOzb4v9vTFZ0rEjYfSo";
        URL url = null;
        HttpURLConnection conn = null;
        int result = 0;
        String txtResult = "";
        try {
            url = new URL(BASE_URL_for_Maps +methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            result = conn.getResponseCode();
            Scanner sc = new Scanner(conn.getInputStream());
            while(sc.hasNextLine())
                txtResult += sc.nextLine();
            JSONObject jsonObject = new JSONObject(txtResult);
            JSONArray arr = jsonObject.getJSONArray("results");
            for(int i = 0; i < arr.length(); i++)
            {

                JSONObject jsonObject1 = arr.getJSONObject(i);
                JSONObject point = new JSONObject();
                point.put("latitude",jsonObject1.getJSONObject("geometry").getJSONObject("location").getDouble("lat"));
                point.put("longitude",jsonObject1.getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
                point.put("name",jsonObject1.getString("name"));
                fitnessAreas.add(point);
            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            conn.disconnect();
        }
        return fitnessAreas;

    }

    public static String getUserAddress(String firstName) {

        String methodPath = "userdetail/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        String address="";
        int postCode = 0;
//Making HTTP request

        try {
            url = new URL(BASE_URL + methodPath + "findByFirstName/" +firstName);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
//Read the response
            int i = conn.getResponseCode();
            Scanner inStream;
            if (i==200 || i==204)
                inStream = new Scanner(conn.getInputStream());
            else
                inStream = new Scanner(conn.getErrorStream());
//read the input stream and store it as string
            while(inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }

            JSONArray jsonArray = new JSONArray(textResult);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            address = ((JSONObject) jsonObject).getString("address");
            postCode = ((JSONObject) jsonObject).getInt("postCode");


            System.out.print(textResult);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return address+" "+postCode;
    }

    public static JSONObject getUserDetail(String firstName) {

        String methodPath = "userdetail/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        String address="";
        int postCode = 0;
        JSONObject jsonObject = new JSONObject();
//Making HTTP request

        try {
            url = new URL(BASE_URL + methodPath + "findByFirstName/" +firstName);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
//Read the response
            int i = conn.getResponseCode();
            Scanner inStream;
            if (i==200 || i==204)
                inStream = new Scanner(conn.getInputStream());
            else
                inStream = new Scanner(conn.getErrorStream());
//read the input stream and store it as string
            while(inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }

            JSONArray jsonArray = new JSONArray(textResult);
            jsonObject = jsonArray.getJSONObject(0);




            System.out.print(textResult);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return jsonObject;
    }

    public static ArrayList<String> getFoodNames(String categories) {

        String methodPath = "food/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        String address="";
        int postCode = 0;
        ArrayList<String> food = new ArrayList<>();
//Making HTTP request

        try {
            url = new URL(BASE_URL + methodPath + "findByCategory/" +categories);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
//Read the response
            int i = conn.getResponseCode();
            Scanner inStream;
            if (i==200 || i==204)
                inStream = new Scanner(conn.getInputStream());
            else
                inStream = new Scanner(conn.getErrorStream());
//read the input stream and store it as string
            while(inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }

            JSONArray jsonArray = new JSONArray(textResult);
            for(int n = 0; n < jsonArray.length(); n++)
            {
                JSONObject object = jsonArray.getJSONObject(n);
                String foods = ((JSONObject) object).getString("foodName");
                food.add(foods);
            }



            System.out.print(textResult);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return food;
    }

    public static String searchGoogle(String keyword, String[] params, String[] values)
    {
        final String API_KEY = "";
        final String SEARCH_ID_cx = "";
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter="";
        if (params!=null && values!=null){
            for (int i =0; i < params.length; i ++){
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "=";
                query_parameter += values[i];
            }
        }
        try {
            url = new URL("https://www.googleapis.com/customsearch/v1?key="+
                    API_KEY+ "&cx="+ SEARCH_ID_cx + "&q="+ keyword + query_parameter);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            int i = connection.getResponseCode();
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return textResult;

    }

    public static String searchGoogleImage(String keyword, String[] params, String[] values)
    {
        final String API_KEY = "";
        final String SEARCH_ID_cx = "";
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter="";
        if (params!=null && values!=null){
            for (int i =0; i < params.length; i ++){
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "=";
                query_parameter += values[i];
            }
        }
        try {
            url = new URL("https://www.googleapis.com/customsearch/v1?key="+
                    API_KEY+ "&cx="+ SEARCH_ID_cx + "&q="+ keyword + query_parameter+"&searchType=image");
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            int i = connection.getResponseCode();
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return textResult;

    }

    public static String getSnippet(String result) {
        String snippet = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if (jsonArray != null && jsonArray.length() > 0) {
                snippet = jsonArray.getJSONObject(0).getString("snippet");
            }
        } catch (Exception e) {
            e.printStackTrace();
            snippet = "NO INFO FOUND";
        }
        return snippet;
    }

    public static String getLink(String result) {
        String tlink = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            JSONObject index = jsonArray.getJSONObject(0);
            JSONObject image = index.getJSONObject("image");
            tlink = ((JSONObject) image).getString("thumbnailLink");

        } catch (Exception e) {
            e.printStackTrace();
            tlink = "NO INFO FOUND";
        }
        return tlink;
    }


    public static Integer retrieveFoods(String foodName) {
        String method = "https://api.nal.usda.gov/ndb/search/?format=json&q="+foodName+"&sort=r&max=5&offset=0&api_key=";
        int id = 0;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        List<String> foodData = new ArrayList<>();
        String textResult = "";  //Making HTTP request
        try {
            url = new URL(method);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            // conn.setRequestProperty("Accept", "application/json");
            //Read the response
            int connresp = conn.getResponseCode();
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine())
                textResult += inStream.nextLine();

            JSONObject obj = new JSONObject(textResult);
            JSONObject data = obj.getJSONObject("list");
            JSONArray arr = data.getJSONArray("item");
            for (int i = 0; i < arr.length() - 1; i++) {
                JSONObject o = arr.getJSONObject(i);
                String s = ((JSONObject) o).getString("name");
                id =  ((JSONObject) o).getInt("ndbno");
                foodData.add(s+" foodId: "+id);
                int x = 0;
            }
            id = ((JSONObject) arr.getJSONObject(0)).getInt("ndbno");


        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            conn.disconnect();
        }


        //return array[0];
        //return arr.get(0).toString();
        return id;
    }

    public static String retrieveFInfo(int foodId) {
        String method = "https://api.nal.usda.gov/ndb/V2/reports?ndbno="+foodId+"&type=f&format=json&api_key=";
        int id = 0;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        List<String> foodData = new ArrayList<>();
        String textResult = "";  //Making HTTP request
        JSONObject foodObj = new JSONObject();
        try {
            url = new URL(method);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            // conn.setRequestProperty("Accept", "application/json");
            //Read the response
            int connresp = conn.getResponseCode();
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine())
                textResult += inStream.nextLine();

            JSONObject obj = new JSONObject(textResult);
            JSONArray array = obj.getJSONArray("foods");
            JSONObject o = array.getJSONObject(0);
            JSONObject food = o.getJSONObject("food");
            JSONObject foodDesc = food.getJSONObject("desc");
            JSONArray nutrients = food.getJSONArray("nutrients");
            JSONObject fat = nutrients.getJSONObject(2);
            JSONObject energy = nutrients.getJSONObject(0);

            JSONArray foodDescription = fat.getJSONArray("measures");
            JSONObject servingData = foodDescription.getJSONObject(0);

            double servingAmount = ((JSONObject)servingData).getInt("qty");
            int fatValue = ((JSONObject) fat).getInt("value") ;
            int calories = ((JSONObject) energy).getInt("value");
            String servingUnit = ((JSONObject)servingData).getString("label");
            String foodName = ((JSONObject)foodDesc).getString("name").substring(0,49);
            String foodCategory;
            if(foodDesc.isNull("fg"))
                foodCategory = "Other";
            else
                foodCategory = ((JSONObject)foodDesc).getString("fg");


            foodObj.put("calAmount", calories);
            foodObj.put("category", foodCategory);
            foodObj.put("fatG", fatValue);
            foodObj.put("foodId", getFoodId());
            foodObj.put("foodName", foodName);
            foodObj.put("serveAmount", servingAmount);
            foodObj.put("serveUnit", servingUnit);


        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            conn.disconnect();
        }


        return foodObj.toString();
    }

    public static int getFoodId() {
        String method = "food";
        int length = 0;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";  //Making HTTP request
        try {
            url = new URL(BASE_URL + method);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();

                JSONArray arr = new JSONArray(textResult);
                length = arr.length()+1;


            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            conn.disconnect();
        }


        //return array[0];
        //return arr.get(0).toString();
        return length;
    }

    public static JSONObject retrieveFood(String fName) {
        String methodPath = "food/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        JSONObject foodObject = new JSONObject();
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath + "findByFoodName/" + fName);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
//Read the response
            int i = conn.getResponseCode();
            Scanner inStream;
            if (i == 200 || i == 204)
                inStream = new Scanner(conn.getInputStream());
            else
                inStream = new Scanner(conn.getErrorStream());
//read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }

            JSONArray jsonArray = new JSONArray(textResult);
            foodObject = jsonArray.getJSONObject(0);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return foodObject;
    }


    public static Integer increaseFoods(String data) {
        String methodPath = "food";
        URL url = null;
        HttpURLConnection conn = null;
        int result = 0;
        try {
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(data);
            writer.flush();
            writer.close();
            result = conn.getResponseCode();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            conn.disconnect();
        }
        return result;
    }

    public static int getConsumptionId() {

        String methodPath = "consumption";
        int count=0;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
//Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
//read the input stream and store it as string
            while (inStream.hasNextLine())
                textResult += inStream.nextLine();

            JSONArray jsonArray = new JSONArray(textResult);
            JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length()-1);
            count = jsonArray.length()+1;


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return count;
    }

    public static String enterUserConsumption(JSONObject userInfo) {
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "consumption";
        try {
            Gson gson = new Gson();
            String stringUserJson = userInfo.toString();
            url = new URL(BASE_URL + methodPath);
            //open the connect)ion
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            // conn.setFixedLengthStreamingMode(stringUserJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Send the POST out
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(stringUserJson);
            out.flush();
            out.close();
            int a = conn.getResponseCode();

            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return "";
    }

    public static double getUserCalBurnedPerStep(int userid)
    {
        String methodPath = "userdetail/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        String address="";
        int postCode = 0;
        double calBurned = 0.0;
//Making HTTP request

        try {
            url = new URL(BASE_URL + methodPath + "findByUserIdCaloriesBurned/" +userid);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
//Read the response
            int i = conn.getResponseCode();
            Scanner inStream;
            if (i==200 || i==204)
                inStream = new Scanner(conn.getInputStream());
            else
                inStream = new Scanner(conn.getErrorStream());
//read the input stream and store it as string
            while(inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }

            JSONArray jsonArray = new JSONArray(textResult);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            calBurned = ((JSONObject) jsonObject).getDouble("UserCalBurnedPerStep");



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return calBurned;

    }

    public static double getUserCalBurnedAtRest(int userid)
    {
        String methodPath = "userdetail/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        String address="";
        int postCode = 0;
        double calBurned = 0.0;
//Making HTTP request

        try {
            url = new URL(BASE_URL + methodPath + "findByUserIdCalBurnedAtRest/" +userid);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Accept", "text/plain");
            conn.connect();
//Read the response
            int i = conn.getResponseCode();
            Scanner inStream;
            if (i==200 || i==204)
                inStream = new Scanner(conn.getInputStream());
            else
                inStream = new Scanner(conn.getErrorStream());
//read the input stream and store it as string
            while(inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }

            String convert = textResult.toString();
            calBurned = Double.parseDouble(convert);


            System.out.print(textResult);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return calBurned;

    }

    public static int getUserTotalCaloriesConsumed(int userid,String date) {

        String methodPath = "consumption/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        int second=0;
//Making HTTP request

        try {
            url = new URL(BASE_URL + methodPath + "findByUserTotalCalConsumed/" +userid + "/"+date);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
//Read the response
            int i = conn.getResponseCode();
            Scanner inStream;
            if (i==200 || i==204)
                inStream = new Scanner(conn.getInputStream());
            else
                inStream = new Scanner(conn.getErrorStream());
//read the input stream and store it as string
            while(inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }

            JSONArray jsonArray = new JSONArray(textResult);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            second = ((JSONObject) jsonObject).getInt("UserTotalCalConsumed");

            System.out.print(textResult);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return second;
    }


    public static int getUserID(String firstName) {

        String methodPath = "userdetail/";
        int count=0;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath + "findByFirstName/" +firstName);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
//Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
//read the input stream and store it as string
            while (inStream.hasNextLine())
                textResult += inStream.nextLine();
            JSONArray jsonArray = new JSONArray(textResult);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            count = ((JSONObject) jsonObject).getInt("userId");



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return count;
    }

    public static HashMap getUserCaloriesConsumedANDBurnedANDRemainingCalorie(int userid, String date) {

        String methodPath = "report/";
        int count=0;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        HashMap<String,Integer> connector = new HashMap<>();
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath + "userCalConsumedBurnedANDRemCal/" + userid+"/"+date);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
//Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
//read the input stream and store it as string
            while (inStream.hasNextLine())
                textResult += inStream.nextLine();
            JSONArray jsonArray = new JSONArray(textResult);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            connector.put("Total Cal Consumed",((JSONObject)jsonObject).getInt("UserCalConsumed"));
            connector.put("Total Cal Burned",((JSONObject)jsonObject).getInt("UserCalBurned"));
            connector.put("Remaining Calorie",((JSONObject)jsonObject).getInt("Remaining Calorie"));


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return connector;
    }

    public static JSONObject getUserCalories(String fromDate, String toDate, int userId)
    {

        URL url = null;
        String method = "report/userCalBurnedConsumedStepsTaken/";
        HttpURLConnection conn = null;
        String textResult = "";
        JSONObject data = new JSONObject();
        try {
            url = new URL(BASE_URL + method + userId + "/" + fromDate + "/" + toDate);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
//            conn.connect();
            int code = conn.getResponseCode();
            String x = "";
            Scanner inStream;
            if(code == 200 || code == 204)
                inStream = new Scanner(conn.getInputStream());
            else
                inStream = new Scanner(conn.getErrorStream());
            inStream = new Scanner(conn.getInputStream());

            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }

            JSONArray arr = new JSONArray(textResult);
            List<Integer> caloriesConsumed = new ArrayList<>();
            List<String> reportDates = new ArrayList<>();
            List<Integer>  caloriesBurned = new ArrayList<>();
            for(int i = 0 ; i < arr.length(); i++)
            {
                int Consumed =  ((JSONObject) arr.get(i)).getInt("UserCalConsumed");
                int Burned =  ((JSONObject) arr.get(i)).getInt("UserCalBurned");
                String date = ((JSONObject) arr.get(i)).getString("Date");
                caloriesConsumed.add(Consumed);
                caloriesBurned.add(Burned);
                reportDates.add(date);
                //foods.add(data);
            }
            data.put("UserCalConsumed",caloriesConsumed);
            data.put("UserCalBurned",caloriesBurned);
            data.put("Date",reportDates);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            conn.disconnect();
        }
        return data;
    }

    public static JSONObject getExistingFoodName(String foodname) {

        String methodPath = "food/";
        int count=0;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        JSONObject jsonObject = new JSONObject();

//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath + "findByFoodName/" +foodname);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
//Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
//read the input stream and store it as string
            while (inStream.hasNextLine())
                textResult += inStream.nextLine();

            JSONArray jsonArray = new JSONArray(textResult);
            jsonObject = jsonArray.getJSONObject(0);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return jsonObject;
    }


    public static int getReportID() {

        String methodPath = "report";
        int count=0;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
//Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
//read the input stream and store it as string
            while (inStream.hasNextLine())
                textResult += inStream.nextLine();
            JSONArray jsonArray = new JSONArray(textResult);
            JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length()-1);
            count= jsonArray.length()+1;


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return count;
    }


    public static String enterUserReport(JSONObject userInfo) {
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "report";
        try {
            Gson gson = new Gson();
            String stringUserJson = userInfo.toString();
            url = new URL(BASE_URL + methodPath);
            //open the connect)ion
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            // conn.setFixedLengthStreamingMode(stringUserJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Send the POST out
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(stringUserJson);
            out.flush();
            out.close();
            int a = conn.getResponseCode();

            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return "";
    }




}
