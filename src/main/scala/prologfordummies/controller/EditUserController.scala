package prologfordummies.controller

import prologfordummies.model.User
import prologfordummies.services.{UserService, UserServiceImpl, UserRepositoryImpl}
import prologfordummies.model.UserSession
import prologfordummies.view.RegistrationPage
import prologfordummies.view.MenuPage
import prologfordummies.Main

object EditUserController {

  private given repo: prologfordummies.services.UserRepository = UserRepositoryImpl.fileRepository
  private given service: UserService = UserServiceImpl.liveService

    def handleEdit(updatedUser: User, onSuccess: User => Unit, onError: String => Unit): Unit = {
      service.updateUser(updatedUser) match {
      case Right(_) => 
        UserSession.login(updatedUser) 
        onSuccess(updatedUser)
      case Left(errorMessage) => 
        onError(errorMessage)
      }
    }

    def handleDelete(deletedUser: User, onSuccess: User => Unit, onError: String => Unit): Unit = {
      service.deleteUser(deletedUser) match {
      case Right(_) => 
        UserSession.logout()
        Main.setPage(RegistrationPage.asParent)
        onSuccess(deletedUser)
      case Left(errorMessage) => 
        onError(errorMessage)
      }
    }

    def handleBackToMenu(): Unit = {
      Main.setPage(MenuPage.asParent)
    }

    def currentUserName: String = 
      UserSession.currentSessionUser match
        case Some(user) => user.username.asString
        case None => "Guest"

    def currentUser: User = 
      UserSession.currentSessionUser match
        case Some(user) => user
        case None => throw new RuntimeException("No user logged in")

    def backToRegistration(): Unit = {
      Main.setPage(RegistrationPage.asParent)
    }

}