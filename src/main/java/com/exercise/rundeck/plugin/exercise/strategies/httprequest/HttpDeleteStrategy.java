package com.exercise.rundeck.plugin.exercise.strategies.httprequest;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpRequestBase;

import com.exercise.rundeck.plugin.exercise.interfaces.HttpRequestStrategy;
/**
 * Strategy implementation for creating HTTP DELETE requests.
 */
public class HttpDeleteStrategy implements HttpRequestStrategy {
  /**
     * Creates an {@link HttpDelete} request with the given URL, body, and content type.
     * If the body and content type are provided, they are set on the request.
     *
     * @param url the URL to which the DELTE request is to be sent
     * @param body the body content to be included in the POST request. In this case null
     * @param contentType the content type of the body. In this case null
     * @return an {@link HttpDelete} request configured with the specified parameters
     */
  public HttpRequestBase createRequest(String url, String body, String contentType) throws Exception {
    return new HttpDelete(url);
  }
}
