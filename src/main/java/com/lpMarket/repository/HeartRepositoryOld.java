package com.lpMarket.repository;

import com.lpMarket.domain.community.Heart;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HeartRepositoryOld {

    private final EntityManager em;

    public void save(Heart heart){
        em.persist(heart);
    }
    public void delete(Heart heart) {
        em.remove(heart);
    }
    public Heart findHeartByMemberAndPost(Long memberId, Long postId){
        try{
            return em.createQuery("select h from Heart h where h.member.id = :memberId and h.post.id = :postId", Heart.class)
                    .setParameter("memberId", memberId)
                    .setParameter("postId", postId)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

}
