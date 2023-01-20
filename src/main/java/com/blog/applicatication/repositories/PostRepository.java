package com.blog.applicatication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.blog.applicatication.entities.Category;
import com.blog.applicatication.entities.Post;
import com.blog.applicatication.entities.User;

public interface PostRepository extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user);

	List<Post> findByCategory(Category category);
	
	@Query(value = "select * from posts p where p.category_id =:categoryId", nativeQuery = true)
	List<Post> findByCategoryId(@Param("categoryId") int categoryId);

	@Query(value = "select * from posts p where p.user_id =:user and p.category_id =:category", nativeQuery = true)
	List<Post> findByUserAndCatagory(@Param("user") User user, @Param("category") Category category);

//	@Query(value = "select * from post p where p.post_title like =:key", nativeQuery = true)
//	List<Post> searchByPostTitle(@Param("key") String keyword);
	
	List<Post> findByPostTitleContaining(String keyword);

}
