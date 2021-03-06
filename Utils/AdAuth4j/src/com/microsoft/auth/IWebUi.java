package com.microsoft.auth;

import java.net.URI;
import java.util.concurrent.Future;

public interface IWebUi {
    Future<String> authenticateAsync(URI requestUri, URI redirectUri);
//    String authenticateAsync(URI requestUri, URI redirectUri) throws Exception;
}
