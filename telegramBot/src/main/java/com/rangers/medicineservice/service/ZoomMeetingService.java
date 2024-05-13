package com.rangers.medicineservice.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Service
public class ZoomMeetingService {


    private final RestTemplate restTemplate;

    private final ZoomAuthService zoomAuthService;

    public ZoomMeetingService(RestTemplate restTemplate, ZoomAuthService zoomAuthService) {
        this.restTemplate = restTemplate;
        this.zoomAuthService = zoomAuthService;
    }

    public String createZoomMeeting(String startTime) {

        String accessToken = zoomAuthService.getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = getStringHttpEntity(startTime, headers);


        var response = restTemplate.postForEntity(
                "https://api.zoom.us/v2/users/me/meetings",
                request,
                Map.class
        );
        if (response.getStatusCode().is2xxSuccessful()) {
            return (String) Objects.requireNonNull(response.getBody()).get("join_url");
        } else {

            throw new RuntimeException("Failed to create Zoom meeting");
        }
    }

    private static @NotNull HttpEntity<String> getStringHttpEntity(String startTime, HttpHeaders headers) {
        String requestBody = "{\n" +
                "    \"topic\":\"You have an appointment with a doctor ______\",\n" +
                "    \"type\":\"2\",\n" +
                "    \"start_time\":\"" + startTime + "\",\n" +
                "    \"duration\":\"60\",\n" +
                "    \"timezone\":\"Europe/Berlin\",\n" +
                "    \"password\":\"123\",\n" +
                "    \"agenda\":\"You have an appointment with a doctor\",\n" +
                "    \"settings\":{\n" +
                "\n" +
                "        \"host_video\":\"true\",\n" +
                "        \"partisipant_video\":\"true\",\n" +
                "        \"join_before_host\":\"true\",\n" +
                "        \"mute_upon_entry\":\"true\",\n" +
                "        \"breakout_room\":{\n" +
                "            \"enable\":true\n" +
                "        }\n" +
                "    }\n" +
                "}";


        return new HttpEntity<>(requestBody, headers);
    }

}
