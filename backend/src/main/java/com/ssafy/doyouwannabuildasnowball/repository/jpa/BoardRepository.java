package com.ssafy.doyouwannabuildasnowball.repository.jpa;

import com.ssafy.doyouwannabuildasnowball.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepository{

    private final EntityManager em;


    public void save(Board board) {
        em.persist(board);
    }

    public Board findById(Long boardId) {
        return em.find(Board.class, boardId);
    }

    public void deleteById(Long boardId) {
        em.remove(em.find(Board.class, boardId));
    }


    public List<Board> findAll(Long snowglobeId) {
        return em.createQuery("select b from board b where b.snowglobeId = :snowglobeId", Board.class)
                .setParameter("snowglobeId", snowglobeId)
                .getResultList();
    }

    public void updateBoardContent(String content, String imageUrl, Long boardId) {
        em.createQuery("Update Board b SET b.content = :content, b.picture = :imageUrl WHERE b.boardId = :boardId")
                .setParameter("content", content)
                .setParameter("imageUrl", imageUrl)
                .setParameter("boardId", boardId);
    }

}

//public interface BoardRepository extends JpaRepository<Board, Long> {
//
//
//    @Query("select b from Board b where b.snowglobe.id =:snowglobeId")
//    Optional<List<Board>> findAllContents(Long snowglobeId);
//    @Transactional
//    @Modifying
//    @Query("UPDATE Board b SET b.content = :content, b.picture = :picture WHERE b.boardId = :boardId")
//    void updateBoardContent(String content, String picture, Long boardId);
//
//}
