package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.responses.CaptchaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CaptchaService {

    private final RestTemplate restTemplate;

    @Value("${google.recaptcha.url}")
    private String url;

    public boolean isValid(String captchaToken){
        if(!StringUtils.hasText(captchaToken)){
            return false;
        }

        try {
            String requestUrl = url + captchaToken;

            CaptchaResponse response = restTemplate.postForObject(requestUrl, null, CaptchaResponse.class);

            if(response == null){
                return false;
            }

            return response.isSuccess();

        } catch (Exception e) {
            return false;
        }
    }

}
