package prologfordummies.services

import prologfordummies.model.User
import java.time.LocalDateTime

object UserServices:
  
  // Implementazione Real
  given liveCreator: UserCreator with
    override def create(name: String): User =
      User(
        id = User.Id.random, 
        username = User.Name(name), 
        registrationDate = LocalDateTime.now()
      )

  // Implementazione Mock
  def mockCreator(fixedTime: LocalDateTime): UserCreator = 
    new UserCreator:
      override def create(name: String): User =
        User(User.Id.random, User.Name(name), fixedTime)