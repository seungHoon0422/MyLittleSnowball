package com.ssafy.doyouwannabuildasnowball.service;

import com.ssafy.doyouwannabuildasnowball.common.exception.CustomException;
import com.ssafy.doyouwannabuildasnowball.domain.Board;
import com.ssafy.doyouwannabuildasnowball.domain.Member;
import com.ssafy.doyouwannabuildasnowball.domain.Snowglobe;
import com.ssafy.doyouwannabuildasnowball.dto.board.BoardDto;
import com.ssafy.doyouwannabuildasnowball.dto.board.request.WriteBoardRequest;
import com.ssafy.doyouwannabuildasnowball.dto.board.response.BoardAllResponse;
import com.ssafy.doyouwannabuildasnowball.dto.board.response.BoardResponse;
import com.ssafy.doyouwannabuildasnowball.repository.jpa.BoardRepository;
import com.ssafy.doyouwannabuildasnowball.repository.jpa.MemberRepository;
import com.ssafy.doyouwannabuildasnowball.repository.jpa.SnowglobeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ssafy.doyouwannabuildasnowball.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BoardService {


    private final BoardRepository boardRepository;
    private final SnowglobeRepository snowglobeRepository;
    private final MemberRepository memberRepository;

    public BoardAllResponse findAllContentsBySnowglobe(Long snowglobeId) {

//        List<Board> boardList = boardRepository.findAll(snowglobeId)
//                .orElseThrow(() -> new CustomException(BOARD_NOT_FOUND));
        List<Board> boardList = boardRepository.findAll(snowglobeId);
        return Board.createBoardResponse(boardList);
    }

    @Transactional
    public void saveContent(WriteBoardRequest writeBoardRequest){

        // find snowball
        Snowglobe snowglobe = snowglobeRepository.findById(writeBoardRequest.getSnowglobeId())
                .orElseThrow(()->new CustomException(SNOWGLOBE_NOT_FOUND));

        // 이미지 설정
        String imageURL = writeBoardRequest.getPicture();

        // 작성자 세팅
        Member member = null;
        if(writeBoardRequest.getWriterId() != null)
            member = memberRepository.findById(writeBoardRequest.getWriterId()).orElse(null);

        // 게시글 저장
        boardRepository.save(
                Board.builder()
                .content(writeBoardRequest.getContent())
                .picture(imageURL)
                .snowglobe(snowglobe)
                .writer(member)
                .build());
    }


    @Transactional
    public void modifyCotnent(BoardDto boardDto) {

        Board board = boardRepository.findById(boardDto.getBoardId());
//        Board board = boardRepository.findById(boardDto.getBoardId())
//                .orElseThrow(() -> new CustomException(BOARD_NOT_FOUND));

        // 게시글 수정 pk와 게시글 pk가 다른 경우
        if(board.getWriter().getMemberId() != boardDto.getWriterId()) {
            throw new CustomException(UNMATCHED_MEMBER);
        }

        String imageURL = boardDto.getPicture();
        board.contentUpdate(boardDto.getContent(), imageURL);
        boardRepository.updateBoardContent(board.getContent(), board.getPicture(), board.getBoardId());

    }

    @Transactional
    public void removeContent(Long boardId, Long memberId) {

        // 게시글 검색
        Board board = boardRepository.findById(boardId);
//        Board board = boardRepository.findById(boardId).orElseThrow(() -> new CustomException(BOARD_NOT_FOUND));


        Long snowglobeId = board.getSnowglobe().getSnowglobeId();
        // 게시글 스노우볼 검색
        Snowglobe snowglobe = snowglobeRepository.findById(snowglobeId).orElseThrow(() -> new CustomException(SNOWGLOBE_NOT_FOUND));
        Long receiverId = snowglobe.getReceiver().getMemberId();


        // 방명록 작성자 인 경우이거나 스노우볼 주인일 경우 삭제 가능
        if(Objects.equals(memberId, receiverId) || Objects.equals(memberId, board.getWriter().getMemberId()))
            boardRepository.deleteById(boardId);
        else
            throw new CustomException(MEMBER_NOT_FOUND);
    }
}
