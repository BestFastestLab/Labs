import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;

public class JsonFile {
    FileInputStream fileInputStream = new FileInputStream("D:\\test");
    BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, 200);
    byte[] text = bufferedInputStream.readAllBytes();
    String TEXT = new String(text);
    private JSONParser jsonParser = new JSONParser();

    public JsonFile() throws IOException {
    }

    public Integer getJsonCollectionSize() {
        int counter = 0;
        try {
            System.out.println(TEXT);
            JSONArray jsonArray = (JSONArray) jsonParser.parse(TEXT);
            for (Object obj : jsonArray) {
                counter++;
            }
        } catch (ParseException ex) {
            System.out.println(ex);;
        }
        return counter;
    }

    public String getName(int index) {
        String tempName = null;
        try {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(TEXT);
            JSONObject jsonObject = (JSONObject) jsonArray.get(index);
            tempName = (String) jsonObject.get("name");
        } catch (ParseException pe) {
            System.err.println(pe);
        }
        return tempName;
    }

    public Integer getId(int index) {
        int tempId = 0;
        try {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(TEXT);
            JSONObject jsonObject = (JSONObject) jsonArray.get(index);
            tempId = BigDecimal.valueOf((Long) jsonObject.get("id")).intValue();
        } catch (ParseException pe) {
            System.err.println(pe);
        }
        return tempId;

    }

    public Coordinates getCoordinates(int index) {
        Coordinates coordinates = new Coordinates();
        Double tempX = 0.0;
        Long tempY = 0L;
        try {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(TEXT);
            JSONObject jsonObject = (JSONObject) jsonArray.get(index);
            tempX = (Double) jsonObject.get("coordinate_x");
            tempY = BigDecimal.valueOf((Long) jsonObject.get("coordinate_y")).longValue();
        } catch (ParseException pe) {
            System.err.println(pe);
        }
        coordinates.setX(tempX);
        coordinates.setY(tempY);
        return coordinates;
    }

    public MusicGenre getMusicGenre(int index) {
        MusicGenre tempGenre = null;
        String comparison;
        try {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(TEXT);
            JSONObject jsonObject = (JSONObject) jsonArray.get(index);
            comparison = (String) jsonObject.get("genre");
            switch (comparison) {
                case "RAP":
                    tempGenre = MusicGenre.RAP;
                    break;
                case "POST_ROCK":
                    tempGenre = MusicGenre.POST_ROCK;
                    break;
                case "BRIT_POP":
                    tempGenre = MusicGenre.BRIT_POP;
                    break;
            }
        } catch (ParseException pe) {
            System.err.println(pe);
        }
        return tempGenre;
    }

    public Album getbestAlbum(int index) {
        Album album = new Album();
        String tempName = null;
        int tempLength = 0;
        try {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(TEXT);
            JSONObject jsonObject = (JSONObject) jsonArray.get(index);
            tempName = ((String) jsonObject.get("bestAlbum name"));
            tempLength = BigDecimal.valueOf((Long) jsonObject.get("bestAlbum length")).intValue();;
        } catch (ParseException pe) {
            System.err.println(pe);
        }
        album.setName(tempName);
        album.setLength(tempLength);
        return album;
    }

    public Long getNumberOfParticipants(int index) {
        long tempNumber = 0;
        try {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(TEXT);
            JSONObject jsonObject = (JSONObject) jsonArray.get(index);
            tempNumber = ((Long) jsonObject.get("numberOfParticipants"));
        } catch (ParseException pe) {
            System.err.println(pe);
        }
        return tempNumber;
    }

    public Long getSinglesCount(int index) {
        long tempCount = 0;
        try {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(TEXT);
            JSONObject jsonObject = (JSONObject) jsonArray.get(index);
            tempCount = ((Long) jsonObject.get("singlesCount"));
        } catch (ParseException pe) {
            System.err.println(pe);
        }
        return tempCount;
    }
    public Long getAlbumCount ( int index){
        long tempCount = 0;
        try {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(TEXT);
            JSONObject jsonObject = (JSONObject) jsonArray.get(index);
            tempCount = ((Long) jsonObject.get("albumCount"));
        } catch (ParseException pe) {
            System.err.println(pe);
        }
        return tempCount;
    }
}