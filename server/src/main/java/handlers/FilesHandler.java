package handlers;

import com.google.gson.Gson;
import dao.database.CassandraFile;
import models.File;
import one.nio.http.Request;
import one.nio.http.Response;
import session.LogonException;
import session.SessionStorage;
import wrappers.file.FileErrorResponse;
import wrappers.file.FileGetAllResponseSuccess;

import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;

public class FilesHandler extends RESTHandler {
    private final CassandraFile cassandraFile = CassandraFile.getInstance();
    private final SessionStorage sessionStorage = SessionStorage.getInstance();

    @Override
    protected Response get(Request request) throws LogonException {
        Gson gson = new Gson();
        String userUUID = sessionStorage.get(request.getHeader("token: ")).getUuid();
        List<File> file = cassandraFile.getAll(userUUID, asList("uuid", "name"));
        if (file == null || file.size() == 0) {
            return new Response(Response.NOT_FOUND, gson.toJson(FileErrorResponse.notFound("file")).getBytes());
        }
        String[] uuids = file.stream().map(File::getUuid).map(UUID::toString).toArray(String[]::new);
        String[] names = file.stream().map(File::getName).toArray(String[]::new);
        FileGetAllResponseSuccess responseSuccess = new FileGetAllResponseSuccess(uuids, names);
        return Response.ok(gson.toJson(responseSuccess));
    }

    @Override
    protected Response create(Request request) throws LogonException {
        return null;
    }

    @Override
    protected Response edit(Request request) throws LogonException {
        return null;
    }

    @Override
    protected Response delete(Request request) throws LogonException {
        return null;
    }
}
