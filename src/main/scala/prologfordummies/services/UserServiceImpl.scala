package prologfordummies.services

import prologfordummies.model.User
import java.time.LocalDateTime
import java.nio.file.{Files, Paths}
import prologfordummies.model.UserCodec.given
import upickle.default.*
import java.time.LocalDateTime
import java.util.UUID

/** Implementazione concreta di UserService che utilizza un UserRepository per
  * gestire le operazioni sugli utenti.
  */
object UserServiceImpl:

  given liveService(using repo: UserRepository): UserService with

    override def signUp(name: String): Either[String, User] =
      name.trim match
        case "" =>
          Left("Errore: il nome utente non può essere vuoto.")
        case trimmedName if repo.findByName(trimmedName).isDefined =>
          Left(s"Errore: il nome '$trimmedName' è già utilizzato.")
        case trimmedName =>
          val newUser =
            User(User.Id.random, User.Name(trimmedName), LocalDateTime.now())
          repo.save(newUser)
          Right(newUser)

    override def updateUser(user: User): Either[String, Unit] =
      user.username.asString.trim match
        case "" =>
          Left("Il nome non può essere vuoto.")
        case trimmedName =>
          repo.findByName(trimmedName) match
            case Some(otherUser) if otherUser.id != user.id =>
              Left(s"Errore: il nome '$trimmedName' è già utilizzato.")
            case _ =>
              repo.update(user)
              Right(())

    override def deleteUser(user: User): Either[String, Unit] =
      try
        repo.delete(user)
        Right(())
      catch
        case e: Exception =>
          Left(s"Errore durante l'eliminazione: ${e.getMessage}")
