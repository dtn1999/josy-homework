package com.project.mediahub.model.payload;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterOptions {
    private String tag;
    private String title;
    private LocalDate to;
    private LocalDate from;
}
