package main.java.com.exercise.rundeck.plugin.exercise;

import com.dtolabs.rundeck.core.plugins.Plugin;
import com.dtolabs.rundeck.plugins.descriptions.PluginProperty;
import com.dtolabs.rundeck.plugins.notification.NotificationPlugin;

import main.java.com.exercise.rundeck.plugin.exercise.interfaces.HttpRequestStrategy;
import main.java.com.exercise.rundeck.plugin.exercise.strategies.httprequest.HttpDeleteStrategy;
import main.java.com.exercise.rundeck.plugin.exercise.strategies.httprequest.HttpGetStrategy;
import main.java.com.exercise.rundeck.plugin.exercise.strategies.httprequest.HttpPostStrategy;
import main.java.com.exercise.rundeck.plugin.exercise.strategies.httprequest.HttpPutStrategy;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.Map;

@Plugin(name = "HttpNotificationPlugin", service = "Notification")
public class HttpNotificationPlugin implements NotificationPlugin {

    @PluginProperty(name = "example",title = "Example String",description = "Example description")
    private String example;

    private Map<String, HttpRequestStrategy> strategies = Map.of(
        "GET", new HttpGetStrategy(),
        "POST", new HttpPostStrategy(),
        "PUT", new HttpPutStrategy(),
        "DELETE", new HttpDeleteStrategy()
    );
    /**
     * Post a notification for the given trigger, dataset, and configuration
     * 
     * @param trigger event type causing notification
     * @param executionData execution data
     * @param config notification configuration
     * @return true if the notification was posted successfully, false otherwise.
     */
    @Override
    public boolean postNotification(String trigger, Map executionData, Map config) {
        String httpMethod = (String) config.get("method");
        String url = (String) config.get("url");
        String body = (String) config.get("body");
        String contentType = (String) config.get("contentType");

        HttpRequestStrategy strategy = strategies.getOrDefault(httpMethod.toUpperCase(), new HttpGetStrategy());

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpRequestBase request = strategy.createRequest(url, body, contentType);
            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            System.err.printf("Trigger %s fired for %s, configuration: %s\n",trigger,executionData,config);
            System.err.printf("Local field example is: %s\n",example);
            return statusCode >= 200 && statusCode < 300;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
