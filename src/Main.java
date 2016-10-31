import com.google.gson.Gson;

import java.io.IOException;

/**
 * Created by vlad on 27/10/2016.
 */
public class Main {

    public static void main(String... args) throws IOException {
//        String urlStr = "http://85.142.162.119/os11/xmodules/qprint/index.php?theme_guid=d06ff6d27541e311b6f4001fc68344c9&proj_guid=068A227D253BA6C04D0C832387FD0D89";

        if (args.length < 1) {
            System.out.println("No url.");
            return;
        }

        Grabber grabber = new Grabber(args[0]);
        Question[] questions = grabber.grabQuestions();

        Gson gson = new Gson();
        String json =  gson.toJson(questions);

        System.out.println(json);
    }

}
