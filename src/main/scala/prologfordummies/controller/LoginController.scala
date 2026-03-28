package prologfordummies.controller

import prologfordummies.model.{User, UserSession}
import prologfordummies.services.UserRepositoryImpl
import prologfordummies.view.{MenuPage, RegistrationPage}

/** Gestisce il login dell'utente
 * e la navigazione alle pagine di registrazione e menu.
 */
object LoginController {

  trait LoginResult
  case class Success(user: User) extends LoginResult
  case class Error(message: String) extends LoginResult

  /** Esegue il login cercando l'utente per username. */
  def login(username: String): LoginResult = {
    username match {
      case "" => Error("Inserire un nome utente")
      case u =>
        val repo = UserRepositoryImpl.fileRepository
        repo.findByName(username) match {
          case Some(user) =>
            UserSession.login(user)
            Success(user)
          case None => Error("Utente non trovato")
        }
    }
  }

  def goToRegistration(): Unit = prologfordummies.Main.setPage(RegistrationPage.asParent)

  def goToMenu(): Unit = prologfordummies.Main.setPage(MenuPage.asParent)

}
