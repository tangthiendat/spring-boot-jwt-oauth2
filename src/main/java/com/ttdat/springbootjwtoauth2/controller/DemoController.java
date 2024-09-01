package com.ttdat.springbootjwtoauth2.controller;

import com.ttdat.springbootjwtoauth2.dto.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DemoController {

    @GetMapping
    public ApiResponse<String> demo() {
        return ApiResponse.<String>builder()
                .status(200)
                .data("Protected data for authenticated users")
                .build();
    }
}
