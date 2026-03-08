package prologfordummies.controller

import prologfordummies.model.User
import prologfordummies.services.{UserService, UserServiceImpl, UserRepositoryImpl}
import prologfordummies.view.LoginPage

object RegistrationController {

  private given repo: prologfordummies.services.UserRepository = UserRepositoryImpl.fileRepository
  private given service: UserService = UserServiceImpl.liveService

  def handleRegistration(username: String, onSuccess: User => Unit, onError: String => Unit): Unit = {
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