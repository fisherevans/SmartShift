package smartshift.common.jersey.providers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import org.apache.log4j.Logger;
import com.google.gson.Gson;
import smartshift.common.jersey.Hello;
import smartshift.common.util.json.APIResultFactory;
import smartshift.common.util.json.GsonFactory;

@Provider
public class JsonBodyReader<T> implements MessageBodyReader<T>, MessageBodyWriter<T> {

    private static final Logger logger = Logger.getLogger(JsonBodyReader.class);
    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        logger.debug("JsonBodyReader.isReadable()");
        return mediaType.equals(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    public T readFrom(Class<T> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        logger.debug("JsonBodyReader.readFrom()");
        try {
            Gson gson = GsonFactory.getGson();
            Reader reader = new InputStreamReader(entityStream, Charset.forName("UTF-8"));
            return gson.fromJson(reader, type);
        } catch(Exception e) {
            logger.warn("Failed to parse JSON object", e);
            throw new WebApplicationException(APIResultFactory.getResponse(Status.BAD_REQUEST, null, "Invalid JSON"));
        }
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return mediaType.equals(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    public long getSize(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        OutputStreamWriter writer = new OutputStreamWriter(entityStream, "UTF-8");
        try {
          Type jsonType;
          if (type.equals(genericType)) {
            jsonType = type;
          } else {
            jsonType = genericType;
          }
          GsonFactory.getGson().toJson(t, jsonType, writer);
        } finally {
          writer.close();
        }
    }
}

