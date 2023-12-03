package com.astrotalk.hospital.repository;

import com.astrotalk.hospital.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,String> {
}
