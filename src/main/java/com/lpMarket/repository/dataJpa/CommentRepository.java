package com.lpMarket.repository.dataJpa;

import com.lpMarket.domain.community.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
