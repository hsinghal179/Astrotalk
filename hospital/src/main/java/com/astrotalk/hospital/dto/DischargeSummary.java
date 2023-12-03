package com.astrotalk.hospital.dto;

import lombok.Data;

@Data
public class DischargeSummary {
    private String room;
    private int pendingBills;
}
