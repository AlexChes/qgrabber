import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by vlad on 29/10/2016.
 */
public class Downloader {

    private String authKey;

    public Downloader() throws IOException {
        authKey = auth();
    }

    private static final String AUTH_URL = "http://85.142.162.119/os11/xmodules/qprint/openlogin.php";

    public String loadPage(String url) throws IOException {
        String preloadUrl = url.replace("index.php", "qsearch.php");
        getUrl(preloadUrl);

        return getUrl(url);
    }

    private String getUrl(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        URLConnection conn = url.openConnection();

        // Disable automatic redirects just for this connection
        HttpURLConnection httpConn = (HttpURLConnection) conn;
        httpConn.setInstanceFollowRedirects(false);

        // Send the request to the server
        conn.setRequestProperty("Cookie", "PHPSESSID=" + authKey);
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
        conn.connect();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "windows-1251"));

        StringBuilder result = new StringBuilder();

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            result.append(inputLine);
        in.close();

        return result.toString();
    }

    private String auth() throws IOException {
        URL url = new URL(AUTH_URL);
        URLConnection conn = url.openConnection();
        HttpURLConnection httpConn = (HttpURLConnection) conn;
        httpConn.setInstanceFollowRedirects(false);

        Map<String, List<String>> map = conn.getHeaderFields();

        String sessionId = map.get("Set-Cookie").get(0);
        sessionId = sessionId.substring(sessionId.indexOf("PHPSESSID=") + "PHPSESSID=".length());

        int endOfPass = sessionId.indexOf(";");
        if (endOfPass > 0) {
            sessionId = sessionId.substring(0, endOfPass);
        }

        httpConn.disconnect();

        return sessionId;
    }

}
