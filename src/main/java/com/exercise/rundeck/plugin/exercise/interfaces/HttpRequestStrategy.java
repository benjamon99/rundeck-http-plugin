package com.exercise.rundeck.plugin.exercise.interfaces;

import org.apache.http.client.methods.HttpRequestBase;
/**
 * Strategy interface for creating HTTP requests, implemented for the Strategy Classes.
 */
public interface HttpRequestStrategy {
   /**
     * Creates an HTTP request based on the specified parameters using the Apache HTTP library.
     *
     * @param url         The URL to which the request will be made (GET, POST, PUT or DELETE).
     * @param body        The body content for the request (if applicable).
     * @param contentType The content type of the body (if applicable).
     * @return An instance of HttpRequestBase representing the HTTP request.
     * @throws Exception if there is an error creating the request.
     */
  HttpRequestBase createRequest(String url, String body, String contentType) throws Exception;
}
