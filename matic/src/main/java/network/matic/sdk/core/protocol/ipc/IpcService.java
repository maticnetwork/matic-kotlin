package network.matic.sdk.core.protocol.ipc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import network.matic.sdk.core.protocol.Service;

/**
 * Ipc service implementation.
 */
public class IpcService extends Service {

    private static final Logger log = LoggerFactory.getLogger(IpcService.class);

    public IpcService(boolean includeRawResponses) {
        super(includeRawResponses);
    }

    public IpcService() {
        this(false);
    }

    protected IOFacade getIO() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    protected InputStream performIO(String payload) throws IOException {
        IOFacade io = getIO();
        io.write(payload);
        log.debug(">> " + payload);

        String result = io.read();
        log.debug("<< " + result);
        io.close();

        // It's not ideal converting back into an inputStream, but we want
        // to be consistent with the HTTPService API.
        // UTF-8 (the default encoding for JSON) is explicitly used here.
        return new ByteArrayInputStream(result.getBytes("UTF-8"));
    }

    @Override
    public void close() throws IOException {
    }
}
