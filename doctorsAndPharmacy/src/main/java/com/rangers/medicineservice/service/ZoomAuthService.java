package com.rangers.medicineservice.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

/**
 * The {@code ZoomAuthService} class provides functionality to authenticate with the Zoom API
 * and obtain an access token.
 * <p>
 * This class is annotated with {@link Service} to indicate that it's a service component
 * in the Spring context.
 * </p>
 * <p>
 * The Zoom API credentials (client ID, account ID, and client secret) are injected from the
 * application properties using the dotenv.
 * </p>
 * <p>
 * Usage:
 * <pre>
 * {@code
 * @Autowired
 * private ZoomAuthService zoomAuthService;
 *
 * String accessToken = zoomAuthService.getAccessToken();
 * }
 * </pre>
 * </p>
 *
 * @author Oleksii Chilibiiskyi
 * @see RestTemplate
 * @see HttpHeaders
 * @see HttpEntity
 */
@Service
public class ZoomAuthService {

    Dotenv dotenv = Dotenv.load();

    private final String clientId = dotenv.get("ZOOM_CLIENT_ID");

    private final String accountId = dotenv.get("ZOOM_ACCOUNT_ID");

    private final String clientSecret = dotenv.get("ZOOM_CLIENT_SECRET");

    private final RestTemplate restTemplate;

    public ZoomAuthService(@Qualifier("zoomRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Obtains an access token from the Zoom API.
     * <p>
     * This method sends an HTTP POST request to the Zoom API to obtain an access token
     * using the account credentials grant type. The credentials are included in the
     * request headers.
     * </p>
     *
     * @return the access token as a {@code String}
     * @throws RuntimeException if the request to the Zoom API fails
     */
    public String getAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);

        String requestBody = "";

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        var response = restTemplate.postForEntity(
                "https://zoom.us/oauth/token?grant_type=account_credentials&account_id=" + accountId,
                request,
                Map.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return (String) Objects.requireNonNull(response.getBody()).get("access_token");
        } else {
            throw new RuntimeException("Failed to get Zoom access token");
        }
    }
}