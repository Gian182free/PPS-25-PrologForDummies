package prologfordummies

import prologfordummies.view.{LoginPage, SplashView}
import scalafx.application.JFXApp3
import scalafx.scene
import scalafx.scene.Scene
import scalafx.scene.layout.Region
import scalafx.animation.PauseTransition
import scalafx.util.Duration
import scalafx.scene.image.Image

object Main extends JFXApp3 {

  def setPage(newRoot: Region): Unit = {
    stage.scene = new Scene {
      root = newRoot
    }
  }

  override def start(): Unit = { 
    stage = new JFXApp3.PrimaryStage {
      title = "Prolog For Dummies"
      icons += new Image(getClass.getResourceAsStream("/logo_pfd.png"))
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