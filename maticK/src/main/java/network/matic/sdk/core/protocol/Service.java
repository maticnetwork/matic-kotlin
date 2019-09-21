package network.matic.sdk.core.protocol;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Flowable;
import java8.util.concurrent.CompletableFuture;
import network.matic.sdk.core.protocol.core.Request;
import network.matic.sdk.core.protocol.core.Response;
import network.matic.sdk.core.protocol.websocket.events.Notification;
import network.matic.sdk.core.utils.Async;

/**
 * Base service implementation.
 */
public abstract class Service implements Web3jService {

    protected final ObjectMapper objectMapper;

    public Service(boolean includeRawResponses) {
        objectMapper = ObjectMapperFactory.getObjectMapper(includeRawResponses);
    }

    protected abstract InputStream performIO(String payload) throws IOException;

    @Override
    public <T extends Response> T send(
            Request request, Class<T> responseType) throws IOException {
        String payload = objectMapper.writeValueAsString(request);

        final InputStream is = performIO(payload);
        try {
            if (is != null) {
                return objectMapper.readValue(is, responseType);
            } else {
                return null;
            }
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    @Override
    public <T extends Response> CompletableFuture<T> sendAsync(
            Request jsonRpc20Request, Class<T> responseType) {
        return Async.run(() -> send(jsonRpc20Request, responseType));
    }

    @Override
    public <T extends Notification<?>> Flowable<T> subscribe(
            Request request,
            String unsubscribeMethod,
            Class<T> responseType) {
        throw new UnsupportedOperationException(
                String.format(
                        "Service %s does not support subscriptions",
                        this.getClass().getSimpleName()));
    }
}
