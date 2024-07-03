package com.rangers.medicineservice;

import com.rangers.medicineservice.service.ZoomAuthService;
import com.rangers.medicineservice.service.ZoomMeetingService;
import com.rangers.medicineservice.util.ZoomUtil;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
@TestConfiguration
public class TestConfig {
    @MockBean
    ZoomAuthService zoomAuthService;
    @MockBean
    ZoomMeetingService zoomMeetingService;
}
