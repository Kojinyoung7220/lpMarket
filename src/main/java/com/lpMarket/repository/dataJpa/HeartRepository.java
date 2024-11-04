package com.lpMarket.repository.dataJpa;

import com.lpMarket.domain.community.Heart;
import jakarta.persistence.NoResultException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    @Query("select h from Heart h where h.member.id = :memberId and h.post.id = :postId")
    Optional<Heart> findHeartByMemberAndPost(@Param("memberId") Long memberId, @Param("postId") Long postId);
}
