package com.blog.applicatication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.applicatication.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
