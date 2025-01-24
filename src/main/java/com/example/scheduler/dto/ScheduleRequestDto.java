package com.example.scheduler.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class ScheduleRequestDto {
    @Getter
    public static class ScheduleCreateDto {
        @NotNull
        private Long authorId;
        @NotBlank
        @Size(max = 30)
        private String password;
        @NotBlank
        @Size(max = 200)
        private String task;
    }

    @Getter
    public static class ScheduleUpdateDto {
        @NotBlank
        @Size(max = 30)
        private String authorName;
        @NotBlank
        @Size(max = 30)
        private String password;
        @NotBlank
        @Size(max = 200)
        private String task;
    }

    @Getter
    public static class ScheduleDeleteDto {
        @NotBlank
        @Size(max = 30)
        private String password;
    }
}
