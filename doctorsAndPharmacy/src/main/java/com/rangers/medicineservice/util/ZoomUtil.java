package com.rangers.medicineservice.util;

import com.rangers.medicineservice.service.ZoomMeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Service
public class ZoomUtil {
    private final ZoomMeetingService zoomMeetingService;

    public ZoomUtil(ZoomMeetingService zoomMeetingService) {
        this.zoomMeetingService = zoomMeetingService;
    }

    public String generateZoomLink() {
        LocalDateTime dateTime = LocalDateTime.now();
        String startTime = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "Z";
        return zoomMeetingService.createZoomMeeting(startTime);
    }

}
