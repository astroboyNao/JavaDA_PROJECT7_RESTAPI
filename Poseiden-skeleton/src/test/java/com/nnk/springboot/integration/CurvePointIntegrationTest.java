package com.nnk.springboot.integration;

import com.nnk.springboot.TestApplicationConfig;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplicationConfig.class)
public class CurvePointIntegrationTest {
    @LocalServerPort
    protected int port;
    @Autowired
    protected TestRestTemplate restTemplate;
    @Autowired
    protected RestTemplateBuilder restBuilder;

    public RestTemplate getUserRestTemplate() throws Exception  {
        final String baseUrl = "http://localhost:"+port+"/authenticate";
        URI uriAuthenticate = new URI(baseUrl);

        User user = new User();
        user.setPassword("test");
        user.setUsername("username");

        String token = this.restTemplate.postForObject(uriAuthenticate, user, String.class);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization" , "Bearer "+ token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
        return restBuilder
                .additionalInterceptors((ClientHttpRequestInterceptor) (request, body, execution) -> {
                    request.getHeaders().add("Authorization", "Bearer " + token);
                    return execution.execute(request, body);
                }).build();
    }

    @Test
    @Sql({"/schema.sql", "/user-data.sql", "/curvepoint-data.sql"})
    public void testList() throws Exception {
        final String baseUrl =  "http://localhost:"+port + "/curvepoint/list/";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = this.getUserRestTemplate().getForEntity(uri, String.class);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getBody().contains("<td style=\"width: 10%\">2</td>"));
        Assert.assertEquals(true, result.getBody().contains("<td style=\"width: 10%\">3</td>"));
    }

    @Test
    @Sql({"/schema.sql", "/user-data.sql", "/curvepoint-data.sql"})
    public void testCurvePointValidate() throws Exception {

        final String baseUrl = "http://localhost:"+port+"/curvepoint/validate/";
        URI uri = new URI(baseUrl);
        CurvePoint curvePoint = new CurvePoint(1,2,3);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add ("curveId", "1");
        params.add ("term", "1");
        params.add ("value", "1");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        ResponseEntity<String> result = this.getUserRestTemplate().postForEntity(uri, requestEntity, String.class);

        Assert.assertEquals(302, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getHeaders().get("Location").toString().contains("curvepoint/list"));

    }

    @Test
    @Sql({"/schema.sql", "/user-data.sql", "/curvepoint-data.sql"})
    public void testCurvePointValidateWithError() throws Exception {

        final String baseUrl = "http://localhost:"+port+"/curvepoint/validate/";
        URI uri = new URI(baseUrl);
        CurvePoint curvePoint = new CurvePoint(1,2,3);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add ("term", "1");
        params.add ("value", "1");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        ResponseEntity<String> result = this.getUserRestTemplate().postForEntity(uri, requestEntity, String.class);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getBody().contains("CurevId is mandatory"));

    }

    @Test
    @Sql({"/schema.sql", "/user-data.sql", "/curvepoint-data.sql"})
    public void testGetUpdateCurvePoint() throws Exception {
        final String baseUrl = "http://localhost:"+port+"/curvepoint/update/1";
        URI uri = new URI(baseUrl);
        ResponseEntity<String> result = this.getUserRestTemplate().getForEntity(uri, String.class);

        Assert.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    @Sql({"/schema.sql", "/user-data.sql", "/curvepoint-data.sql"})
    public void testUpdateCurvePoint() throws Exception {
        final String baseUrl = "http://localhost:"+port+"/curvepoint/update/1";
        URI uri = new URI(baseUrl);
        CurvePoint curvePoint = new CurvePoint(1,2,3);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add ("curveId", "1");
        params.add ("term", "1");
        params.add ("value", "1");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        ResponseEntity<String> result = this.getUserRestTemplate().postForEntity(uri, requestEntity, String.class);

        Assert.assertEquals(302, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getHeaders().get("Location").toString().contains("curvepoint/list"));
    }

    @Test
    @Sql({"/schema.sql", "/user-data.sql", "/curvepoint-data.sql"})
    public void testUpdateCurvePointWithError() throws Exception {
        final String baseUrl = "http://localhost:"+port+"/curvepoint/update/1";
        URI uri = new URI(baseUrl);
        CurvePoint curvePoint = new CurvePoint(1,2,3);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add ("term", "1");
        params.add ("value", "1");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        ResponseEntity<String> result = this.getUserRestTemplate().postForEntity(uri, requestEntity, String.class);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getBody().contains("CurevId is mandatory"));
    }

    @Test
    @Sql({"/schema.sql", "/user-data.sql", "/curvepoint-data.sql"})
    public void testDeleteCurvePoint() throws Exception {
        final String baseUrl = "http://localhost:"+port+"/curvepoint/delete/1";
        URI uri = new URI(baseUrl);
        ResponseEntity<String> result = this.getUserRestTemplate().getForEntity(uri, String.class);

        Assert.assertEquals(200, result.getStatusCodeValue());
    }
}
