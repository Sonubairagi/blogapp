package com.blog.applicatication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.applicatication.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
