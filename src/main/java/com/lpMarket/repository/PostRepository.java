package com.lpMarket.repository;

import com.lpMarket.domain.community.Post;
import com.lpMarket.web.request.PostSearch;
import com.lpMarket.web.request.UpdatePostDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PostRepository{

    private final EntityManager em;

    /**
     * 저장
     * 조회
     * 수정
     * 삭제
     */

    /**
     * 저장
     */
    public void save(Post post){
        em.persist(post);
    }

    /**
     * 조회
     */
    public Post findOne(Long id){
        return em.find(Post.class, id);
    }
    public List<Post> findAll(){
        return em.createQuery("select p from Post p", Post.class).getResultList();
    }

    //page처리
    public List<Post> getList(PostSearch postSearch){
        String jpql = "SELECT p FROM Post p ORDER BY p.id DESC";
        TypedQuery<Post> query = em.createQuery(jpql, Post.class);

        query.setFirstResult((int)postSearch.getOffset()); //조회 시작 위치(0부터 시작)
        query.setMaxResults(postSearch.getSize()); // 조회할 데이터 수
        return query.getResultList();
    }

    //db총 개수
    public long count() {
        String jpql = "select COUNT(p) from Post p";
        return (long) em.createQuery(jpql).getSingleResult();
    }

    public long getTotalCount() {
        String jpql = "SELECT COUNT(p) FROM Post p";
        return em.createQuery(jpql, Long.class).getSingleResult();
    }

}
