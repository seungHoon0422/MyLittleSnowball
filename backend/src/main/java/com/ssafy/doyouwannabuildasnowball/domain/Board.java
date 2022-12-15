package com.ssafy.doyouwannabuildasnowball.domain;

import com.ssafy.doyouwannabuildasnowball.domain.base.BaseEntity;
import com.ssafy.doyouwannabuildasnowball.dto.board.response.BoardAllResponse;
import com.ssafy.doyouwannabuildasnowball.dto.board.response.BoardResponse;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Builder
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "snowglobe_id")
    private Snowglobe snowglobe;

    @Column(length = 200)
    private String content;

    @Column(length = 200)
    private String picture;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @Builder
    public Board(Long boardId, Snowglobe snowglobe, String content, String picture, Member writer) {
        this.boardId = boardId;
        this.snowglobe = snowglobe;
        this.content = content;
        this.picture = picture;
        this.writer = writer;
    }

    //==비즈니스 로직==//
    public static BoardAllResponse createBoardResponse(List<Board> boardList) {
        List<BoardResponse> boardResponses = new ArrayList<>();
        boardList.forEach(board -> boardResponses.add(BoardResponse.builder()
                .createdTime(board.getCreatedTime())
                .modifiedTime(board.getModifiedTime())
                .boardId(board.getBoardId())
                .snowglobeId(board.getSnowglobe().getSnowglobeId())
                .writerId(board.getWriter().getMemberId())
                .content(board.getContent())
                .imageUrl(board.getPicture()).build()
        ));
        return new BoardAllResponse(boardResponses);
    }

    //==Board 객체 생성 메서드==//
//    public static Board createBoard(Snowglobe snowglobe, String content, String imageURL, Member member) {
//        return Board.builder()
//                .snowglobe(snowglobe)
//                .content(content)
//                .picture(imageURL)
//                .writer(member)
//                .build();
//    }

    public void contentUpdate(String content, String picture) {

        this.content = content;
        this.picture = picture;
    }
}
