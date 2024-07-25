package main.java.com.exercise.rundeck.plugin.exercise;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.HttpVersion;
import org.junit.jupiter.api.Test;

import main.java.com.exercise.rundeck.plugin.exercise.interfaces.HttpRequestStrategy;
import main.java.com.exercise.rundeck.plugin.exercise.strategies.httprequest.HttpDeleteStrategy;
import main.java.com.exercise.rundeck.plugin.exercise.strategies.httprequest.HttpGetStrategy;
import main.java.com.exercise.rundeck.plugin.exercise.strategies.httprequest.HttpPostStrategy;
import main.java.com.exercise.rundeck.plugin.exercise.strategies.httprequest.HttpPutStrategy;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import static org.mockito.Mockito.*;

class ExcerciseApplicationTests {


// Tests for validate creation of requests
  @Test
  void testCreateRequest() throws Exception {
    String url = "http://example.com";
    String body = "{\"key\":\"value\"}";
    String contentType = "application/json";
    String httpMethod = "post";
    Map<String, HttpRequestStrategy> strategies = Map.of(
        "GET", new HttpGetStrategy(),
        "POST", new HttpPostStrategy(),
        "PUT", new HttpPutStrategy(),
        "DELETE", new HttpDeleteStrategy());

    HttpRequestStrategy strategy = strategies.getOrDefault(httpMethod.toUpperCase(), new HttpGetStrategy());

    HttpPost request = (HttpPost) strategy.createRequest(url, body, contentType);

    assertEquals(url, request.getURI().toString());
    assertNotNull(request.getEntity());
    assertTrue(request.getEntity() instanceof StringEntity);
    assertEquals(contentType, request.getFirstHeader("Content-type").getValue());
  }

  @Test
  public void testCreateRequestWithoutBodyAndContentType() throws Exception {

    String url = "http://example.com";
    String body = null;
    String contentType = null;
    String httpMethod = "post";
    Map<String, HttpRequestStrategy> strategies = Map.of(
        "GET", new HttpGetStrategy(),
        "POST", new HttpPostStrategy(),
        "PUT", new HttpPutStrategy(),
        "DELETE", new HttpDeleteStrategy());

    HttpRequestStrategy strategy = strategies.getOrDefault(httpMethod.toUpperCase(), new HttpGetStrategy());

    HttpPost request = (HttpPost) strategy.createRequest(url, body, contentType);

    assertEquals(url, request.getURI().toString());
    assertNull(request.getEntity());
  }

  @Test
  public void testNotKnownHttpMethod() {

    Map<String, HttpRequestStrategy> strategies = Map.of(
        "GET", new HttpGetStrategy(),
        "POST", new HttpPostStrategy(),
        "PUT", new HttpPutStrategy(),
        "DELETE", new HttpDeleteStrategy());
    String method = "OHTER_METHOD";

    HttpRequestStrategy strategy = strategies.getOrDefault(method, new HttpGetStrategy());

    assertTrue(strategy instanceof HttpGetStrategy);
  }

  @Test
  public void testCreateRequestWithQueryParams() throws Exception {

    String url = "http://example.com/api?param1=value1&param2=value2";
    String body = null;
    String contentType = null;
    String expectedUrlWithParams = url;

    Map<String, HttpRequestStrategy> strategies = Map.of(
        "GET", new HttpGetStrategy(),
        "POST", new HttpPostStrategy(),
        "PUT", new HttpPutStrategy(),
        "DELETE", new HttpDeleteStrategy());
    String method = "OHTER_METHOD";

    HttpRequestStrategy strategy = strategies.getOrDefault(method, new HttpGetStrategy());

    HttpRequestBase request = strategy.createRequest(url, body, contentType);

    assertEquals(expectedUrlWithParams, request.getURI().toString());
  }

  // Tests for validate success cases when calling an HTTP request
  @Test
  public void testHttpGetSuccessResponse() throws Exception {
    // Arrange
    String url = "https://jsonplaceholder.typicode.com/posts";
    Map<String, HttpRequestStrategy> strategies = Map.of(
        "GET", new HttpGetStrategy(),
        "POST", new HttpPostStrategy(),
        "PUT", new HttpPutStrategy(),
        "DELETE", new HttpDeleteStrategy());
    String httpMethod = "get";

    HttpRequestStrategy strategy = strategies.getOrDefault(httpMethod.toUpperCase(), new HttpGetStrategy());
    CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
    CloseableHttpResponse mockResponse = mock(CloseableHttpResponse.class);

    when(mockResponse.getStatusLine()).thenReturn(new BasicStatusLine(HttpVersion.HTTP_1_1, 200, "OK"));
    when(httpClient.execute(any(HttpGet.class))).thenReturn(mockResponse);

    HttpRequestBase request = strategy.createRequest(url, null, null);
    CloseableHttpResponse response = httpClient.execute(request);

    assertNotNull(response);
    assertEquals(200, response.getStatusLine().getStatusCode());
    assertEquals(url, request.getURI().toString());
  }

  @Test
  public void testHttpPostWithBodySuccessResponse() throws Exception {

    String url = "https://jsonplaceholder.typicode.com/posts";
    String body = "{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}";
    String contentType = "application/json";
    String httpMethod = "post";
    Map<String, HttpRequestStrategy> strategies = Map.of(
        "GET", new HttpGetStrategy(),
        "POST", new HttpPostStrategy(),
        "PUT", new HttpPutStrategy(),
        "DELETE", new HttpDeleteStrategy());

    HttpRequestStrategy strategy = strategies.getOrDefault(httpMethod.toUpperCase(), new HttpGetStrategy());
    CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
    CloseableHttpResponse mockResponse = mock(CloseableHttpResponse.class);

    when(mockResponse.getStatusLine()).thenReturn(new BasicStatusLine(HttpVersion.HTTP_1_1, 201, "OK"));
    when(httpClient.execute(any(HttpPost.class))).thenReturn(mockResponse);

    HttpRequestBase request = strategy.createRequest(url, body, contentType);
    HttpResponse response = httpClient.execute(request);

    assertNotNull(response);
    assertEquals(201, response.getStatusLine().getStatusCode());
    assertEquals(url, request.getURI().toString());
  }

  @Test
  public void testHttpPutWithBodySuccessResponse() throws Exception {

    String url = "https://api.restful-api.dev/objects/7";
    String body = "{\"name\":\"AppleMacBookPro16\",\"data\":{\"year\":2019,\"price\":2049.99,\"CPUmodel\":\"IntelCorei9\",\"Harddisksize\":\"1TB\",\"color\":\"silver\"}}";
    String contentType = "application/json";
    String httpMethod = "put";
    Map<String, HttpRequestStrategy> strategies = Map.of(
        "GET", new HttpGetStrategy(),
        "POST", new HttpPostStrategy(),
        "PUT", new HttpPutStrategy(),
        "DELETE", new HttpDeleteStrategy());

    HttpRequestStrategy strategy = strategies.getOrDefault(httpMethod.toUpperCase(), new HttpGetStrategy());
    CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
    CloseableHttpResponse mockResponse = mock(CloseableHttpResponse.class);

    when(mockResponse.getStatusLine()).thenReturn(new BasicStatusLine(HttpVersion.HTTP_1_1, 200, "OK"));
    when(httpClient.execute(any(HttpPut.class))).thenReturn(mockResponse);

    HttpRequestBase request = strategy.createRequest(url, body, contentType);
    CloseableHttpResponse response = httpClient.execute(request);

    assertNotNull(response);
    assertEquals(200, response.getStatusLine().getStatusCode());
    assertEquals(url, request.getURI().toString());
  }

  @Test
  public void testHttpDelteSuccessResponse() throws Exception {

    String url = "https://api.restful-api.dev/objects/6";
    String body = null;
    String contentType = null;
    String httpMethod = "delete";
    Map<String, HttpRequestStrategy> strategies = Map.of(
        "GET", new HttpGetStrategy(),
        "POST", new HttpPostStrategy(),
        "PUT", new HttpPutStrategy(),
        "DELETE", new HttpDeleteStrategy());

    HttpRequestStrategy strategy = strategies.getOrDefault(httpMethod.toUpperCase(), new HttpGetStrategy());
    CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
    CloseableHttpResponse mockResponse = mock(CloseableHttpResponse.class);

    when(mockResponse.getStatusLine()).thenReturn(new BasicStatusLine(HttpVersion.HTTP_1_1, 200, "OK"));
    when(httpClient.execute(any(HttpDelete.class))).thenReturn(mockResponse);

    HttpRequestBase request = strategy.createRequest(url, body, contentType);
    CloseableHttpResponse response = httpClient.execute(request);

    assertNotNull(response);
    assertEquals(200, response.getStatusLine().getStatusCode());
    assertEquals(url, request.getURI().toString());
  }

  // Tests for validate when the HTTP method response with an error
  @Test
  public void testHttpGetNotFoundResponse() throws Exception {

    String url = "https://api.restful-api.dev/obje";
    Map<String, HttpRequestStrategy> strategies = Map.of(
        "GET", new HttpGetStrategy(),
        "POST", new HttpPostStrategy(),
        "PUT", new HttpPutStrategy(),
        "DELETE", new HttpDeleteStrategy());
    String httpMethod = "get";

    HttpRequestStrategy strategy = strategies.getOrDefault(httpMethod.toUpperCase(), new HttpGetStrategy());
    CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
    CloseableHttpResponse mockResponse = mock(CloseableHttpResponse.class);

    when(mockResponse.getStatusLine()).thenReturn(new BasicStatusLine(HttpVersion.HTTP_1_1, 404, "Not Found"));
    when(httpClient.execute(any(HttpGet.class))).thenReturn(mockResponse);

    HttpRequestBase request = strategy.createRequest(url, null, null);
    CloseableHttpResponse response = httpClient.execute(request);

    assertNotNull(response);
    assertEquals(404, response.getStatusLine().getStatusCode());
    assertEquals(url, request.getURI().toString());
  }

  @Test
  public void testHttpPostWithMalformedBodyResponse() throws Exception {

    String url = "https://jsonplaceholder.typicode.com/posts";
    String body = "{title:'foo',body:'bar',userId:1"; // Malformed body missing closing bracket
    String contentType = "application/json";
    String httpMethod = "post";
    Map<String, HttpRequestStrategy> strategies = Map.of(
        "GET", new HttpGetStrategy(),
        "POST", new HttpPostStrategy(),
        "PUT", new HttpPutStrategy(),
        "DELETE", new HttpDeleteStrategy());

    HttpRequestStrategy strategy = strategies.getOrDefault(httpMethod.toUpperCase(), new HttpGetStrategy());
    CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
    CloseableHttpResponse mockResponse = mock(CloseableHttpResponse.class);

    when(mockResponse.getStatusLine()).thenReturn(new BasicStatusLine(HttpVersion.HTTP_1_1, 400, "Bad Request"));
    when(httpClient.execute(any(HttpPost.class))).thenReturn(mockResponse);

    HttpRequestBase request = strategy.createRequest(url, body, contentType);
    CloseableHttpResponse response = httpClient.execute(request);

    assertNotNull(response);
    assertEquals(400, response.getStatusLine().getStatusCode());
    assertEquals(url, request.getURI().toString());
  }

  // hacer readme
}
