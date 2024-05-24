package com.rangers.medicineservice.util;

import com.rangers.medicineservice.service.ZoomMeetingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The {@code ZoomUtil} class provides functionality to generate Zoom meeting links.
 * It uses {@link ZoomMeetingService} to create the Zoom meetings.
 * <p>
 * This class is annotated with {@link Service} to indicate that it's a service component
 * in the Spring context.
 * </p>
 * <p>
 * The Zoom meeting creation service is injected through the constructor.
 * </p>
 * <p>
 * Usage:
 * <pre>
 * {@code
 * @Autowired
 * private ZoomUtil zoomUtil;
 *
 * String zoomLink = zoomUtil.generateZoomLink();
 * }
 * </pre>
 * </p>
 *
 * @author Oleksii Chilibiiskyi
 * @see ZoomMeetingService
 */
@Service
public class ZoomUtil {
    /**
     * The {@code ZoomMeetingService} instance used to create Zoom meetings.
     */
    private final ZoomMeetingService zoomMeetingService;

    /**
     * Constructs a {@code ZoomUtil} with the specified {@code ZoomMeetingService}.
     *
     * @param zoomMeetingService the service used to create Zoom meetings
     */
    public ZoomUtil(ZoomMeetingService zoomMeetingService) {
        this.zoomMeetingService = zoomMeetingService;
    }

    /**
     * Generates a Zoom meeting link.
     * <p>
     * This method generates the current date and time, formats it in the ISO_LOCAL_DATE_TIME format,
     * appends a 'Z' to indicate UTC time, and uses the {@link ZoomMeetingService} to create a Zoom meeting.
     * </p>
     *
     * @return the generated Zoom meeting link
     */
    public String generateZoomLink() {
        LocalDateTime dateTime = LocalDateTime.now();
        String startTime = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "Z";
        return zoomMeetingService.createZoomMeeting(startTime);
    }

}
