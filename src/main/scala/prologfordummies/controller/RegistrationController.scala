package prologfordummies.controller

import prologfordummies.model.User
import prologfordummies.services.{
  UserService,
  UserServiceImpl,
  UserRepositoryImpl
}
import prologfordummies.view.LoginPage

/** Controller dedicato alla registrazione dei nuovi utenti.
  *
  * Agisce come intermediario tra la view di registrazione e la logica di
  * business, definita nel [[UserService]].
  */
object RegistrationController {

  private given repo: prologfordummies.services.UserRepository =
    UserRepositoryImpl.fileRepository
  private given service: UserService = UserServiceImpl.liveService

  def handleRegistration(
      username: String,
      onSuccess: User => Unit,
      onError: String => Unit
  ): Unit = {
    service.signUp(username) match {
      case Right(user) =>
        onSuccess(user)
      case Left(errorMessage) =>
        onError(errorMessage)
    }
  }

  def handleBackToLogin(): Unit = {
    prologfordummies.Main.setPage(LoginPage.asParent)
  }
}
