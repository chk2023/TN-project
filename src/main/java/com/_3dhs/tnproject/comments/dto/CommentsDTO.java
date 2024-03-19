package com._3dhs.tnproject.comments.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentsDTO {
    private int cmtCode;
    private int postCode;
    private String cmtText;
    private LocalDateTime cmtWriDate;
    private int memberCode;
    private Integer parentsCode;
    private LocalDateTime cmtModDate;
    private LocalDateTime cmtDeleDate;
    private boolean isDeleted;
}
