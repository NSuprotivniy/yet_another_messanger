package API;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import one.nio.http.HttpServerConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import server.HttpServerConfigFactory;
import server.Server;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UserTest {
    private final String API_EXAMPLES_PATH = "src/test/resources/api_examples/user/";
    private Server server;
    private URL url;

    @BeforeClass
    public void runServer() throws IOException {
        HttpServerConfig config;
        config = HttpServerConfigFactory.create(8080);
        server = new Server(config);
        server.start();
        url = new URL("http://localhost:8080/");
    }

    @Test
    public void create() throws IOException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(API_EXAMPLES_PATH + "create_request_correct.json"));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(reader.toString());
        out.close();

        int status = con.getResponseCode();
        System.out.println(status);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        System.out.println(inputLine);

    }

    @Test
    public void get() {

    }

    @Test
    public void update() {

    }

    @Test
    public void delete() {

    }

    @AfterClass
    public void closeServer() {
        server.stop();
    }
}
