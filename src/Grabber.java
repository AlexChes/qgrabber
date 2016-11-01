import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by alex on 29/10/2016.
 */
public class Grabber {

    private String url;

    private Downloader downloader;
    private int countOfPages;

    public Grabber(String url) throws IOException {
        this.url = url;

        System.out.print("Auth... ");
        downloader = new Downloader();
        System.out.println("done");

        System.out.print("Loading first page... ");
        String page = downloader.loadPage(url);
        System.out.println("done");

        Parser parser = new Parser(page);
        countOfPages = parser.getCountOfLinkedPages();
        System.out.println("Count of linked pages: " + countOfPages);
    }

    public Question[] grabQuestions() throws IOException {
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < countOfPages; i++) {
            System.out.println("Page " + (i + 1));
            Question[] q = questionsFromPage(url.replace("&groupno", "&oldgrp") + "&groupno=" + i);
            questions.addAll(Arrays.asList(q));
        }

        return questions.toArray(new Question[]{});
    }

    private  Question[] questionsFromPage(String url) throws IOException {
        String page = downloader.loadPage(url);

        Parser parser = new Parser(page);
        return parser.getQuestions();
    }
}
