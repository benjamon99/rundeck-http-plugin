package main.java.com.exercise.rundeck.plugin.exercise.strategies.httprequest;

import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;

import main.java.com.exercise.rundeck.plugin.exercise.interfaces.HttpRequestStrategy;
/**
 * Strategy implementation for creating HTTP PUT requests.
 */
public class HttpPutStrategy implements HttpRequestStrategy {
  /**
     * Creates an {@link HttpPut} request with the given URL, body, and content type.
     * If the body and content type are provided, they are set on the request.
     *
     * @param url the URL to which the POST request is to be sent
     * @param body the body content to be included in the POST request, can be null
     * @param contentType the content type of the body, can be null
     * @return an {@link HttpPut} request configured with the specified parameters
     */
  public HttpRequestBase createRequest(String url, String body, String contentType) throws Exception {
    HttpPut put = new HttpPut(url);
    if (body != null && contentType != null) {
      put.setEntity(new StringEntity(body));
      put.setHeader("Content-type", contentType);
    }
    return put;
  }
}
