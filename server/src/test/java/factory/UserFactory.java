package factory;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UserFactory {
    private final static String API_EXAMPLES_PATH = "src/test/resources/api_examples/user/";

    public static String createRequestCorrect(String name, String email, String password) throws IOException {
        String requestJson = new String(Files.readAllBytes(Paths.get(API_EXAMPLES_PATH + "create_request_correct.json")));
        return requestJson
                .replace("${name}", name)
                .replace("${email}", email)
                .replace("${password}", password);
    }

    public static String createRequestCorrect() throws IOException {
        String name = RandomStringUtils.randomAlphanumeric(10);
        String email = name + "@example.com";
        String password = RandomStringUtils.randomAlphanumeric(10);
        return createRequestCorrect(name, email, password);
    }

    public static String createResponseCorrect(String uuid) throws IOException {
        String requestJson = new String(Files.readAllBytes(Paths.get(API_EXAMPLES_PATH + "create_response_success.json")));
        return requestJson.replace("${uuid}", uuid);
    }

    public static String createResponseCorrect() throws IOException {
        String uuid = Uuids.timeBased().toString();
        return createResponseCorrect(uuid);
    }

    public static String updateRequestCorrect(String uuid, String name, String email, String password) throws IOException {
        String requestJson = new String(Files.readAllBytes(Paths.get(API_EXAMPLES_PATH + "update_request_correct.json")));
        return requestJson
                .replace("${uuid}", uuid)
                .replace("${name}", name)
                .replace("${email}", email)
                .replace("${password}", password);
    }

    public static String updateRequestCorrect() throws IOException {
        String uuid = Uuids.timeBased().toString();
        String name = RandomStringUtils.randomAlphanumeric(10);
        String email = name + "@example.com";
        String password = RandomStringUtils.randomAlphanumeric(10);
        return updateRequestCorrect(uuid, name, email, password);
    }

    public static String updateResponseCorrect(String uuid) throws IOException {
        String requestJson = new String(Files.readAllBytes(Paths.get(API_EXAMPLES_PATH + "update_response_success.json")));
        return requestJson.replace("${uuid}", uuid);
    }

    public static String updateResponseCorrect() throws IOException {
        String uuid = Uuids.timeBased().toString();
        return updateResponseCorrect(uuid);
    }

    public static String deleteRequestCorrect(String uuid) throws IOException {
        String requestJson = new String(Files.readAllBytes(Paths.get(API_EXAMPLES_PATH + "delete_request_correct.json")));
        return requestJson.replace("${uuid}", uuid);
    }

    public static String deletRequestCorrect() throws IOException {
        String uuid = Uuids.timeBased().toString();
        return deleteRequestCorrect(uuid);
    }

    public static String deleteResponseCorrect(String uuid) throws IOException {
        String requestJson = new String(Files.readAllBytes(Paths.get(API_EXAMPLES_PATH + "delete_response_success.json")));
        return requestJson.replace("${uuid}", uuid);
    }

    public static String deleteResponseCorrect() throws IOException {
        String uuid = Uuids.timeBased().toString();
        return deleteResponseCorrect(uuid);
    }

    public static String getRequestCorrect(String uuid) throws IOException {
        String requestJson = new String(Files.readAllBytes(Paths.get(API_EXAMPLES_PATH + "get_request_correct.json")));
        return requestJson.replace("${uuid}", uuid);
    }

    public static String getRequestCorrect() throws IOException {
        String uuid = Uuids.timeBased().toString();
        return getRequestCorrect(uuid);
    }

    public static String getResponseCorrect(String name, String email) throws IOException {
        String requestJson = new String(Files.readAllBytes(Paths.get(API_EXAMPLES_PATH + "get_response_success.json")));
        return requestJson
                .replace("${name}", name)
                .replace("${email}", email);
    }

    public static String getResponseCorrect() throws IOException {
        String name = RandomStringUtils.randomAlphanumeric(10);
        String email = name + "@example.com";
        return getResponseCorrect(name, email);
    }

}
