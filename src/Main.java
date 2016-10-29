import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad on 27/10/2016.
 */
public class Main {

    public static void main(String... args) throws IOException {
        String urlStr = "http://85.142.162.119/os11/xmodules/qprint/index.php?theme_guid=d06ff6d27541e311b6f4001fc68344c9&proj_guid=068A227D253BA6C04D0C832387FD0D89";

        Grabber grabber = new Grabber(urlStr);
        Question[] questions = grabber.grabQuestions();

        Gson gson = new Gson();
        String json =  gson.toJson(questions);

        System.out.println(json);
    }


    private static String questionsFromPageAndLinkedPagesToJson(String url) throws IOException {
        System.out.println("Auth");
        Downloader downloader = new Downloader();
        String page = downloader.loadPage(url);

        Parser parser = new Parser(page);
        int countOfLinkedPages = parser.getCountOfLinkedPages();

        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < countOfLinkedPages; i++) {


            questionsFromPageToJson(url.replace("&groupno", "&oldgrp") + "&groupno=" + i);

            try {
                Thread.sleep(1000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println(countOfLinkedPages);

        Gson gson = new Gson();
        return gson.toJson(questions);
    }

    private static String questionsFromPageToJson(String url) throws IOException {
        Downloader downloader = new Downloader();
        String page = downloader.loadPage(url);

        Parser parser = new Parser(page);
        Question[] questions = parser.getQuestions();
        int countOfLinkedPages = parser.getCountOfLinkedPages();

        System.out.println(countOfLinkedPages);

        Gson gson = new Gson();
        return gson.toJson(questions);
    }

}
