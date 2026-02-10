package prologfordummies.services

import prologfordummies.model.User

/** 
  * Algebra per la creazione di utenti. 
  */
trait UserCreator:
  def create(name: String): User