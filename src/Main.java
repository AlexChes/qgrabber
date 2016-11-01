import com.google.gson.Gson;

import java.io.IOException;

/**
 * Created by alex on 27/10/2016.
 */

public class Main {

    public static void main(String... args) throws IOException {
        if (args.length < 1) {
            System.out.println("No url.");
            return;
        }

        // Скачиваем информацию с сайта
        Grabber grabber = new Grabber(args[0]);
        Question[] questions = grabber.grabQuestions();

        // Преобразуем результат в формат JSON
        Gson gson = new Gson();
        String json =  gson.toJson(questions);

        // Выводим результат
        System.out.println(json);
    }

}
