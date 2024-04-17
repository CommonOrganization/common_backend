package com.junghun.common.domain.daily.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyUploadDto {

    private Long writerId;
    private Long commentId;
    private String content;
}
