package com.example.SunBase.Controller;


import com.example.SunBase.Dtos.RequestDto.SunbaseRemoteApiAuthDto;
import com.example.SunBase.Dtos.ResponseDto.JwtResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/sunbase")
@CrossOrigin(origins = "http://127.0.0.1:5500") // replace your domain name
public class SunbaseRemoteApi {

//    @Autowired
    RestTemplate restTemplate=new RestTemplate();


    @PostMapping("/token")
    public ResponseEntity generateToken(@RequestBody SunbaseRemoteApiAuthDto sunbaseRemoteApiAuthDto){

        try{
            HttpHeaders headers=new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<SunbaseRemoteApiAuthDto>request=new HttpEntity<>(sunbaseRemoteApiAuthDto,headers);

            String url="https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
            ResponseEntity<String >generatedToken=restTemplate.postForEntity(url,request,String.class);

            String responseBody = generatedToken.getBody();
            int startIndex = responseBody.indexOf(':') + 2; // Skip the first double quote and the colon
            int endIndex = responseBody.lastIndexOf('"'); // Exclude the last double quote
            String token = responseBody.substring(startIndex, endIndex);

            System.out.println(token);

            return new ResponseEntity(new JwtResponseDTO(token),HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/customer-list")
    public ResponseEntity getAllData(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_JSON);
            header.set("Authorization", "Bearer " +authorizationHeader);
            //set query paramerters..
            String url = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";


            HttpEntity entity = new HttpEntity(header);

            ResponseEntity<Object> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Object.class
            );

            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }




}
