package com.example.SunBase.Service;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class RemoteApiService {

    public String RequestAapiCall(String apiUrl, String requestBody) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        String responseBody = responseEntity.getBody();

        return responseBody;
    }

    public List<Object> getALlCustomersPresentInDatabase(String token, String apiUrl){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // adding Authorization as a header
        headers.set("Authorization", "Bearer " + token);


        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Object[]> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity, Object[].class );
         // object type referes return statement
        Object[] responseBody = responseEntity.getBody();
        // here we are  return list of custoemrs
        return List.of(responseBody);

    }

    public static final String loginurl = "https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";

    public static final String customersApi = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";

    public Object[] getToken(){
        String requestBody = "{ \"login_id\": \"test@sunbasedata.com\", \"password\": \"Test@123\" }";
        String token = RequestAapiCall(loginurl, requestBody);
        // get the token
        String acessToken = token.substring(19, token.length()-3);
        List<Object> customers = getALlCustomersPresentInDatabase(acessToken, customersApi);

        Object[] customersReceived = customers.toArray();
        return customersReceived;
    }



}
