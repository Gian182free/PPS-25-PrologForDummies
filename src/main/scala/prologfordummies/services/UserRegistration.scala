package prologfordummies.services

import prologfordummies.model.User
import prologfordummies.services.UserCreator

object UserRegistration:
  
  /** 
   * Gestisce la registrazione di un nuovo utente nell'applicazione
   */
  def signUp(name: String)(using creator: UserCreator, repository: UserRepository): User =
    name.trim match
      case "" => 
        throw new IllegalArgumentException("Errore: il nome utente non può essere vuoto.")
      case trimmedName => 
        repository.findByName(trimmedName) match
          case Some(_) => 
            throw new IllegalArgumentException(s"Errore: il nome '$trimmedName' è già occupato.")
          case None => 
            val newUser = creator.create(trimmedName)
            repository.save(newUser)
            newUser