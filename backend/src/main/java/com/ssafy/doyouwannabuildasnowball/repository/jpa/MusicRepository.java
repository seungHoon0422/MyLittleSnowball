package com.ssafy.doyouwannabuildasnowball.repository.jpa;

import com.ssafy.doyouwannabuildasnowball.domain.Music;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MusicRepository {

    private final EntityManager em;

    public Music findById(Long musicId) {
        return em.find(Music.class, musicId);
    }

    public List<Music> findAll() {
        return em.createQuery("SELECT m FROM Music m", Music.class)
                .getResultList();
    }
}


//public interface MusicRepository extends JpaRepository<Music, Long> {
//}
