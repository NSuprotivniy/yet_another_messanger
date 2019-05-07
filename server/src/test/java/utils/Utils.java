package utils;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

public class Utils {
    public static Response post(String url, String body) throws IOException {
        HttpResponse response = new NetHttpTransport().createRequestFactory()
                .buildPostRequest(new GenericUrl(url), ByteArrayContent.fromString("application/json", body))
                .execute();
        return new Response(response.getStatusCode(), IOUtils.toString(response.getContent()));
    }

    public static Response get(String url, String body) throws IOException {
        HttpResponse response = new NetHttpTransport().createRequestFactory()
                .buildGetRequest(new GenericUrl(url))
                .setContent(ByteArrayContent.fromString("application/json", body))
                .execute();
        return new Response(response.getStatusCode(), IOUtils.toString(response.getContent()));
    }

    public static Response update(String url, String body) throws IOException {
        HttpResponse response = new NetHttpTransport().createRequestFactory()
                .buildGetRequest(new GenericUrl(url))
                .setContent(ByteArrayContent.fromString("application/json", body))
                .execute();
        return new Response(response.getStatusCode(), IOUtils.toString(response.getContent()));
    }

    public static Response delete(String url, String body) throws IOException {
        HttpResponse response = new NetHttpTransport().createRequestFactory()
                .buildDeleteRequest(new GenericUrl(url))
                .setContent(ByteArrayContent.fromString("application/json", body))
                .execute();
        return new Response(response.getStatusCode(), IOUtils.toString(response.getContent()));
    }

    public static class Response {
        public int status;
        public String body;

        public Response(int status, String body) {
            this.status = status;
            this.body = body;
        }
    }
}
