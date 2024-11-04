package com.lpMarket.repository;

import com.lpMarket.domain.community.Comment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryOld {


    private final EntityManager em;

    public void save(Comment comment){
        em.persist(comment);
    }

    public Optional<Comment> findById(Long id){
        List<Comment> result = em.createQuery("select c from Comment c where c.id = :id", Comment.class)
                .setParameter("id", id)
                .getResultList();
        return result.stream().findFirst();
    }

    public List<Comment> findAll(){
        return em.createQuery("select c from Comment c", Comment.class)
                .getResultList();
    }

    public void delete(Long id){
        Comment comment = em.find(Comment.class, id);
        if(comment != null){
            em.remove(comment);
        }
    }
}
