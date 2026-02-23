package prologfordummies

import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.layout.Region
import prologfordummies.view.RegistrationPage

object Main extends JFXApp3 {

  def setPage(newRoot: Region): Unit = {
    stage.scene = new Scene {
      root = newRoot
    }
  }

  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title = "Prolog For Dummies"
      width = 800
      height = 600
      scene = new Scene {
        root = RegistrationPage.asParent
      }
    }
  }
}