package com.junghun.common.domain.daily.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentUploadDto {

    private Long writerId;
    private String content;

}
