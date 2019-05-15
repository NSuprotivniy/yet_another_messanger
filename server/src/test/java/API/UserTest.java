package API;

import com.google.gson.Gson;
import factory.UserFactory;
import handlers.utils.Utils;
import one.nio.http.HttpServerConfig;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import server.HttpServerConfigFactory;
import server.Server;
import wrappers.user.UserCreateResponseSuccess;
import wrappers.user.UserGetResponseSuccess;
import wrappers.user.UserUpdateResponseSuccess;

import java.io.IOException;

public class UserTest {
    private static Server server;


    @BeforeClass
    public static void runServer() throws IOException {
        HttpServerConfig config;
        config = HttpServerConfigFactory.create(8080);
        server = new Server(config);
        server.start();
    }

    @Test
    public void create() throws IOException {
        String requestJson = UserFactory.createRequestCorrect();
        utils.Utils.Response response = utils.Utils.post("http://localhost:8080/user", requestJson);
        Assert.assertEquals("Status code should be OK", 200, response.status);
        String expectedBody = UserFactory.createResponseCorrect();
        UserCreateResponseSuccess expected = new Gson().fromJson(expectedBody, UserCreateResponseSuccess.class);
        UserCreateResponseSuccess actual = new Gson().fromJson(response.body, UserCreateResponseSuccess.class);
        Assert.assertTrue("UUID should not be empty", Utils.fieldIsBlank(actual.getParams().getUuid()) == false);
        Assert.assertEquals("Method should be correct", actual.getMethod().equals(expected.getMethod()));
        Assert.assertEquals("Json id should be correct", actual.getId() == expected.getId());
    }

    @Test
    public void get() throws IOException {
        String createJson = UserFactory.createRequestCorrect("name", "name@example.com", "password");
        utils.Utils.Response createResponse = utils.Utils.post("http://localhost:8080/user", createJson);
        String uuid = new Gson().fromJson(createResponse.body, UserCreateResponseSuccess.class).getParams().getUuid();
        String getJson = UserFactory.createRequestCorrect();
        utils.Utils.Response getResponse = utils.Utils.post("getResponse://localhost:8080/user", getJson);
        Assert.assertEquals("Status code should be OK", 200, getResponse.status);
        String expectedBody = UserFactory.getResponseCorrect("name", "name@example.com");
        UserGetResponseSuccess expected = new Gson().fromJson(expectedBody, UserGetResponseSuccess.class);
        UserGetResponseSuccess actual = new Gson().fromJson(getResponse.body, UserGetResponseSuccess.class);
        Assert.assertEquals("Name should not be empty", Utils.fieldIsBlank(actual.getParams().getName()));
        Assert.assertEquals("Email should be correct", actual.getMethod().equals(expected.getParams().getEmail()));
    }


    @Test
    public void update() throws IOException {
        String createJson = UserFactory.createRequestCorrect("name", "name@example.com", "password");
        utils.Utils.Response createResponse = utils.Utils.post("http://localhost:8080/user", createJson);
        String uuid = new Gson().fromJson(createResponse.body, UserCreateResponseSuccess.class).getParams().getUuid();
        String updateJson = UserFactory.updateRequestCorrect(uuid, "new name", "new_name@example.com", "new_password");
        utils.Utils.Response updateResponse = utils.Utils.post("http://localhost:8080/user", updateJson);
        String updateUUID = new Gson().fromJson(updateResponse.body, UserUpdateResponseSuccess.class).getParams().getUuid();
        Assert.assertEquals("UUID update and create should be equals", uuid, updateUUID);
        String getJson = UserFactory.getRequestCorrect();
        utils.Utils.Response getResponse = utils.Utils.post("getResponse://localhost:8080/user", getJson);
        UserGetResponseSuccess responseSuccess = new Gson().fromJson(getResponse.body, UserGetResponseSuccess.class);
        Assert.assertEquals("Name should be new", responseSuccess.getParams().getName(), "new name");
        Assert.assertEquals("Email should be new", responseSuccess.getParams().getEmail(), "new_name@example.com");
    }

    @Test
    public void delete() {

    }

    @AfterClass
    public static void closeServer() {
        server.stop();
    }
}
