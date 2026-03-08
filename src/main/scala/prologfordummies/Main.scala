package prologfordummies

import prologfordummies.view.{LoginPage, SplashView}
import scalafx.application.JFXApp3
import scalafx.scene
import scalafx.scene.Scene
import scalafx.scene.layout.Region
import scalafx.animation.PauseTransition
import scalafx.util.Duration
import prologfordummies.view.RegistrationPage
import prologfordummies.view.EditUserPage
import prologfordummies.model.User
import java.util.UUID
import java.time.LocalDateTime
import prologfordummies.model.UserSession
import prologfordummies.services.UserRepositoryImpl

object Main extends JFXApp3 {

  def setPage(newRoot: Region): Unit = {
    stage.scene = new Scene {
      root = newRoot
    }
  }

  override def start(): Unit = {
    val repo = UserRepositoryImpl.fileRepository
    val testUser = repo.loadAll().headOption
    UserSession.login(testUser.getOrElse(
      User(
        id = User.Id(UUID.fromString("78487944-0f3a-4eb3-bcaf-e4d58b45b442")),
        username = User.Name("NoUserFound"),
        registrationDate = LocalDateTime.parse("2026-02-25T23:00:56.638427600")
      )
    ))
    stage = new JFXApp3.PrimaryStage {
      title = "Prolog For Dummies"
      width = 800
      height = 600
      maximized = true 
      minWidth = 600
      minHeight = 800
      scene = new Scene {
        root = SplashView.asParent
      }
    }

    val waitTimer = new PauseTransition(Duration(3000)) {
      onFinished = _ => {
        setPage(LoginPage.asParent)

      }
    }
    waitTimer.play()
  }
}