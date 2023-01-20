package com.blog.applicatication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.applicatication.entities.Comment;
import com.blog.applicatication.entities.Post;
import com.blog.applicatication.entities.User;
import com.blog.applicatication.payload.CommentDto;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Query(value = "select * from comments c where c.user_id =:user and c.post_id =:post and c.comment_id =:commentId", nativeQuery = true)
	Comment findByUserAndPostAndCommentId(@Param("user") User user, @Param("post") Post post, @Param("commentId") int commentId);

}
