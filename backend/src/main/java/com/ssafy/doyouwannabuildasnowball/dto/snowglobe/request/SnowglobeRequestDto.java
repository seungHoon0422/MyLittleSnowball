package com.ssafy.doyouwannabuildasnowball.dto.snowglobe.request;

import com.ssafy.doyouwannabuildasnowball.domain.Music;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SnowglobeRequestDto {
    private Long snowglobeId;
    private String screenshot;
    private Music music;
    private String deco;
}
