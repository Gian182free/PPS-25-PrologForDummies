package prologfordummies.controller

import prologfordummies.model.User
import prologfordummies.services.{UserCreator, UserRepository, UserRegistration}
import prologfordummies.services.UserServices

object RegistrationController {

  private given creator: UserCreator = UserServices.liveCreator
  private given repository: UserRepository = UserServices.fileRepository

  def handleRegistration(username: String, onSuccess: User => Unit, onError: String => Unit): Unit = {
    try {
      val user = UserRegistration.signUp(username)
      onSuccess(user)
    } catch {
      case e: IllegalArgumentException => 
        onError(e.getMessage)
      case e: Exception => 
        onError(s"Errore imprevisto: ${e.getMessage}")
    }
  }
}