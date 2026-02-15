package prologfordummies.services

import prologfordummies.model.User

/** 
 * Interfaccia di repository per la gestione dei dati utente.
 */
trait UserRepository:
  def save(user: User): Unit
  def loadAll(): List[User]
  def findByName(name: String): Option[User]