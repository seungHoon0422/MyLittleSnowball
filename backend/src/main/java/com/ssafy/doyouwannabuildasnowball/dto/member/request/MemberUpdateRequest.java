package com.ssafy.doyouwannabuildasnowball.dto.member.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequest {

    @ApiModelProperty(notes = "멤버 아이디")
    private Long memberId;
    @ApiModelProperty(notes = "닉네임")
    private String nickname;
}