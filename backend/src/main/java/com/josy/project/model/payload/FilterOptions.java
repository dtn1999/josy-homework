package com.josy.project.model.payload;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FilterOptions {
    private String tag;
    private String title;
    private LocalDate to;
    private LocalDate from;
}
