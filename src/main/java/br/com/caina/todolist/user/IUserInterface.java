package br.com.caina.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

//passa model e o tipo do id UUID
public interface IUserInterface extends JpaRepository<UserModel,UUID> { //REPRESENTAÇÃO DOS MÉTODOS DAS CLASSES
    UserModel findByUsername(String username);
}
