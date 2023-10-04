package com.example.marketbackend.repository;

import com.example.marketbackend.entity.NeighborComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NeighborCommentRepository extends JpaRepository<NeighborComment, Long> {

    @Query(value = "select c from NeighborComment c join fetch c.user join fetch c.post where c.post.id = :postId and c.isDeleted = false ",
        countQuery = "select count(c) from NeighborComment c where c.isDeleted = false")
    Page<NeighborComment> findComments(@Param("postId") long postId, Pageable pageable);

}
