package com.exercise.rundeck.plugin.exercise.strategies.httprequest;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;

import com.exercise.rundeck.plugin.exercise.interfaces.HttpRequestStrategy;
/**
 * Strategy implementation for creating HTTP POST requests.
 */
public class HttpPostStrategy implements HttpRequestStrategy {
  /**
     * Creates an {@link HttpPost} request with the given URL, body, and content type.
     * If the body and content type are provided, they are set on the request.
     *
     * @param url the URL to which the POST request is to be sent
     * @param body the body content to be included in the POST request, can be null
     * @param contentType the content type of the body, can be null
     * @return an {@link HttpPost} request configured with the specified parameters
     */
  public HttpRequestBase createRequest(String url, String body, String contentType) throws Exception {
    HttpPost post = new HttpPost(url);
    if (body != null && contentType != null) {
      post.setEntity(new StringEntity(body));
      post.setHeader("Content-type", contentType);
    }
    return post;
  }
}