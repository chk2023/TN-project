package com._3dhs.tnproject.comments.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentDTO {
    private int cmtCode;
    private int postCode;
    private String cmtDetail;
    private LocalDateTime cmtWriDate;
    private int memberCode;
    private Integer parentsCode;
    private LocalDateTime cmtModDate;
    private LocalDateTime cmtDeleDate;
    private boolean isDeleted;
}
