package com.example.orderms.service.client;

import com.example.orderms.model.dto.response.AnnouncementResponse;
import com.example.orderms.model.dto.response.ProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "announcement-ms", url = "http://localhost:8086/announcement-ms/api")
public interface AnnouncementClient {

    @GetMapping("/v1/announcements/{id}")
    AnnouncementResponse getAnnouncementById(@PathVariable Long id);

    @GetMapping("/v1/announcements")
    List<AnnouncementResponse> getAllAnnouncements();
}
