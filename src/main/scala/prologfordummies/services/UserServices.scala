package prologfordummies.services

import prologfordummies.model.User
import java.time.LocalDateTime
import java.nio.file.{Files, Paths}
import prologfordummies.model.UserCodec.given
import upickle.default.*

object UserServices:
  
  // Implementazione Real
  given liveCreator: UserCreator with
    override def create(name: String): User =
      User(
        id = User.Id.random, 
        username = User.Name(name), 
        registrationDate = LocalDateTime.now()
      )

  given fileRepository: UserRepository with
    private val path = Paths.get("users.json")

    override def save(user: User): Unit =
      val allUsers = loadAll() :+ user
      Files.writeString(path, write(allUsers, indent = 4))

    override def loadAll(): List[User] =
      if (!Files.exists(path) || Files.readString(path).isBlank) List.empty
      else try read[List[User]](Files.readString(path)) catch case _ => List.empty

    override def findByName(name: String): Option[User] = 
      loadAll().find(_.username.asString == name)

  // Implementazione Mock
  def mockCreator(fixedTime: LocalDateTime): UserCreator = 
    new UserCreator:
      override def create(name: String): User =
        User(User.Id.random, User.Name(name), fixedTime)