package prologfordummies.services

import prologfordummies.model.User
import java.time.LocalDateTime
import java.nio.file.{Files, Paths}
import prologfordummies.model.UserCodec.given
import upickle.default.*
import java.time.LocalDateTime
import java.util.UUID

/** 
 * Interfaccia che definisce le operazioni di alto livello per la gestione degli utenti
 */
trait UserService:
  def signUp(name: String): Either[String, User]
  def updateUser(user: User): Either[String, Unit]
  def deleteUser(user: User): Either[String, Unit]