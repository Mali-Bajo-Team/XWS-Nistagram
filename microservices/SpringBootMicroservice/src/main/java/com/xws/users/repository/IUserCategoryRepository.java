package com.xws.users.repository;

import com.xws.users.users.model.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserCategoryRepository extends JpaRepository<UserCategory, Long> {
    UserCategory findByName(String name);
}
