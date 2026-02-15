package prologfordummies.services

import prologfordummies.model.User
import prologfordummies.services.UserCreator

object UserRegistration:
  
  /** 
   * Gestisce la registrazione di un nuovo utente nell'applicazione
   */
  def signUp(name: String)(using creator: UserCreator, repository: UserRepository): User =
    if (name.trim.isEmpty) throw new IllegalArgumentException("Nome vuoto")
    val newUser = creator.create(name)
    repository.save(newUser)
    newUser