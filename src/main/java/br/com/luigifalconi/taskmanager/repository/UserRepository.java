package br.com.luigifalconi.taskmanager.repository;

import br.com.luigifalconi.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
