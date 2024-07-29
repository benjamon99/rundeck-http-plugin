package com.exercise.rundeck.plugin.exercise;

import com.dtolabs.rundeck.core.plugins.Plugin;
import com.dtolabs.rundeck.plugins.descriptions.PluginProperty;
import com.dtolabs.rundeck.plugins.notification.NotificationPlugin;

import com.exercise.rundeck.plugin.exercise.interfaces.HttpRequestStrategy;
import com.exercise.rundeck.plugin.exercise.interfaces.NotificationHandlerStrategy;
import com.exercise.rundeck.plugin.exercise.strategies.handlernotification.OnAvgDurationHandlerNotificationStrategy;
import com.exercise.rundeck.plugin.exercise.strategies.handlernotification.OnFailureHandlerNotificationStrategy;
import com.exercise.rundeck.plugin.exercise.strategies.handlernotification.OnRetryableFailureHandlerNotificationStrategy;
import com.exercise.rundeck.plugin.exercise.strategies.handlernotification.OnStartHandlerNotificationStrategy;
import com.exercise.rundeck.plugin.exercise.strategies.handlernotification.OnSuccessHandlerNotificationStrategy;
import com.exercise.rundeck.plugin.exercise.strategies.httprequest.HttpDeleteStrategy;
import com.exercise.rundeck.plugin.exercise.strategies.httprequest.HttpGetStrategy;
import com.exercise.rundeck.plugin.exercise.strategies.httprequest.HttpPostStrategy;
import com.exercise.rundeck.plugin.exercise.strategies.httprequest.HttpPutStrategy;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.Map;

@Plugin(name = "HttpNotificationPlugin", service = "Notification")
public class HttpNotificationPlugin implements NotificationPlugin {

    @PluginProperty(name = "url", title = "URL (Mandatory)", description = "The URL to send the request to (e.g. http://my-url/api)")
    private String url;

    @PluginProperty(name = "method", title = "HTTP Method (GET by default)", description = "The HTTP method to use (GET, POST, PUT, DELETE)")
    private String method;

    @PluginProperty(name = "body", title = "Request Body (Optional)", description = "The body of the request (for POST and PUT)")
    private String body;

    @PluginProperty(name = "contentType", title = "Content Type (Optional)", description = "The content type of the request (e.g. application/json)")
    private String contentType;

    private Map<String, HttpRequestStrategy> strategies = Map.of(
        "GET", new HttpGetStrategy(),
        "POST", new HttpPostStrategy(),
        "PUT", new HttpPutStrategy(),
        "DELETE", new HttpDeleteStrategy()
    );

    private Map<String, NotificationHandlerStrategy> handlers = Map.of(
        "start", new OnStartHandlerNotificationStrategy(),
        "success", new OnSuccessHandlerNotificationStrategy(),
        "failure", new OnFailureHandlerNotificationStrategy(),
        "avgduration", new OnAvgDurationHandlerNotificationStrategy(),
        "retryablefailure", new OnRetryableFailureHandlerNotificationStrategy()
    );

    @Override
    public boolean postNotification(String trigger, Map executionData, Map config) {
        System.setErr(System.out);
        String httpMethod = method != null ? method : (String) config.get("method");
        String requestUrl = url != null ? url : (String) config.get("url");
        String requestBody = body != null ? body : (String) config.get("body");
        String requestContentType = contentType != null ? contentType : (String) config.get("contentType");

        if(httpMethod == null) {
            httpMethod = "get";
        }

        HttpRequestStrategy strategy = strategies.getOrDefault(httpMethod.toUpperCase(), new HttpGetStrategy());

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpRequestBase request = strategy.createRequest(requestUrl, requestBody, requestContentType);
            System.err.printf("Sending %s request to %s with body: %s and content type: %s\n", httpMethod, requestUrl, requestBody, requestContentType);
            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            System.err.printf("Received response with status code: %d\n", statusCode);
            String responseBody = EntityUtils.toString(response.getEntity());
            System.err.printf("Response body: %s\n", responseBody);

            NotificationHandlerStrategy handler = handlers.get(trigger.toLowerCase());
            if (handler != null) {
                handler.handle(executionData, config);
            }
            return statusCode >= 200 && statusCode < 300;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
