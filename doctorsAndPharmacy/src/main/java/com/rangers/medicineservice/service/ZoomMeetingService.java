package com.rangers.medicineservice.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

/**
 * The {@code ZoomMeetingService} class provides functionality to create Zoom meetings.
 * It uses {@link ZoomAuthService} to obtain an access token for Zoom API requests.
 * <p>
 * This class is annotated with {@link Service} to indicate that it's a service component
 * in the Spring context.
 * </p>
 * <p>
 * Usage:
 * <pre>
 * {@code
 * @Autowired
 * private ZoomMeetingService zoomMeetingService;
 *
 * String meetingLink = zoomMeetingService.createZoomMeeting(startTime);
 * }
 * </pre>
 * </p>
 *
 * @author Oleksii Chilibiiskyi
 * @see RestTemplate
 * @see ZoomAuthService
 * @see HttpHeaders
 * @see HttpEntity
 */
@Service
public class ZoomMeetingService {


    private final RestTemplate restTemplate;

    private final ZoomAuthService zoomAuthService;

    public ZoomMeetingService(@Qualifier("zoomRestTemplate") RestTemplate restTemplate, ZoomAuthService zoomAuthService) {
        this.restTemplate = restTemplate;
        this.zoomAuthService = zoomAuthService;
    }

    /**
     * Creates a Zoom meeting.
     * <p>
     * This method sends an HTTP POST request to the Zoom API to create a meeting using the specified start time.
     * The request includes the necessary headers and body with the meeting details.
     * </p>
     *
     * @param startTime the start time of the meeting in ISO_LOCAL_DATE_TIME format with a 'Z' suffix
     * @return the URL to join the created Zoom meeting
     * @throws RuntimeException if the request to create the Zoom meeting fails
     */
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

    /**
     * Constructs an {@code HttpEntity} with the meeting details.
     * <p>
     * This method constructs the request body with the meeting details including the topic, type, start time, duration,
     * timezone, password, agenda, and settings. The headers are also included in the {@code HttpEntity}.
     * </p>
     *
     * @param startTime the start time of the meeting in ISO_LOCAL_DATE_TIME format with a 'Z' suffix
     * @param headers   the headers to include in the request
     * @return the constructed {@code HttpEntity} with the request body and headers
     */
    private static @NotNull HttpEntity<String> getStringHttpEntity(String startTime, HttpHeaders headers) {
        String requestBody = "{\n" +
                "    \"topic\":\"You have an appointment with a doctor \",\n" +
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
