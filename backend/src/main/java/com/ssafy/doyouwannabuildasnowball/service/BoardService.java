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

        // ????????? ??????
        String imageURL = writeBoardRequest.getPicture();

        // ????????? ??????
        Member member = null;
        if(writeBoardRequest.getWriterId() != null)
            member = memberRepository.findById(writeBoardRequest.getWriterId()).orElse(null);

        // ????????? ??????
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

        // ????????? ?????? pk??? ????????? pk??? ?????? ??????
        if(board.getWriter().getMemberId() != boardDto.getWriterId()) {
            throw new CustomException(UNMATCHED_MEMBER);
        }

        String imageURL = boardDto.getPicture();
        board.contentUpdate(boardDto.getContent(), imageURL);
        boardRepository.updateBoardContent(board.getContent(), board.getPicture(), board.getBoardId());

    }

    @Transactional
    public void removeContent(Long boardId, Long memberId) {

        // ????????? ??????
        Board board = boardRepository.findById(boardId);
//        Board board = boardRepository.findById(boardId).orElseThrow(() -> new CustomException(BOARD_NOT_FOUND));


        Long snowglobeId = board.getSnowglobe().getSnowglobeId();
        // ????????? ???????????? ??????
        Snowglobe snowglobe = snowglobeRepository.findById(snowglobeId).orElseThrow(() -> new CustomException(SNOWGLOBE_NOT_FOUND));
        Long receiverId = snowglobe.getReceiver().getMemberId();


        // ????????? ????????? ??? ??????????????? ???????????? ????????? ?????? ?????? ??????
        if(Objects.equals(memberId, receiverId) || Objects.equals(memberId, board.getWriter().getMemberId()))
            boardRepository.deleteById(boardId);
        else
            throw new CustomException(MEMBER_NOT_FOUND);
    }
}
