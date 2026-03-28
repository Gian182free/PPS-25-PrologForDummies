package prologfordummies.view

import prologfordummies.controller.MenuController
import prologfordummies.model.{User, UserSession}
import prologfordummies.view.UIComponents.{logoView, styledButton}
import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.control.{Label, Separator}
import scalafx.scene.layout.*
import scalafx.scene.text.Font

/** Schermata principale dopo il login per accedere alle sezioni dell'app. */
object MenuPage {

  def currentUserName: String =
    UserSession.currentSessionUser match
      case Some(user) => user.username.asString
      case None       => "Guest"

  def asParent: Region = new VBox {
    alignment = Pos.Center
    spacing = 10
    padding = Insets(20)
    style = "-fx-background-color: #f4f4f4;"

    val logo = logoView(myFitWidth = 250)

    val menuCard = new GridPane {
      alignment = Pos.Center
      hgap = 10
      vgap = 15
      padding = Insets(25)
      style = """
          -fx-background-color: white;
          -fx-background-radius: 10;
          -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);
      """

      val column1 = new ColumnConstraints()
      column1.hgrow = Priority.Always
      columnConstraints.add(column1)

      val header = new Label(s"Ciao, $currentUserName") {
        font = Font.font("System", 24)
        style = "-fx-font-weight: bold; -fx-text-fill: #333;"
      }

      val startBtn = styledButton(
        text = "Vai ai livelli",
        bgColor = "#4a90e2",
        textColor = "white",
        MenuController.goToLevels()
      )

      startBtn.maxWidth() = 200
      val separator = new Separator:
        orientation = Orientation.Horizontal
        maxWidth = Double.MaxValue

      val statsBtn = styledButton(
        text = "Statistiche",
        bgColor = "#e0e0e0",
        textColor = "#333",
        MenuController.goToStats()
      )

      statsBtn.maxWidth() = 200

      val optionsBtn = styledButton(
        text = "Modifica Utente",
        bgColor = "#e0e0e0",
        textColor = "#333",
        MenuController.goToEditUser()
      )

      optionsBtn.maxWidth() = 200

      val exitBtn = styledButton(
        text = "Esci",
        bgColor = "#e0e0e0",
        textColor = "#333",
        MenuController.confirmLogout()
      )

      exitBtn.maxWidth() = 200

      add(logo, 0, 0)
      GridPane.setHalignment(logo, scalafx.geometry.HPos.Center)
      GridPane.setMargin(logo, Insets(0, 0, 20, 0))

      add(header, 0, 1, 2, 1)
      GridPane.setHalignment(header, scalafx.geometry.HPos.Center)
      add(startBtn, 0, 2)
      GridPane.setHalignment(startBtn, scalafx.geometry.HPos.Center)
      add(separator, 0, 3)
      add(statsBtn, 0, 4)
      GridPane.setHalignment(statsBtn, scalafx.geometry.HPos.Center)
      add(optionsBtn, 0, 6)
      GridPane.setHalignment(optionsBtn, scalafx.geometry.HPos.Center)
      add(exitBtn, 0, 8)
      GridPane.setHalignment(exitBtn, scalafx.geometry.HPos.Center)

    }

    menuCard.maxWidthProperty().bind(width * 0.8)
    children = Seq(menuCard)
  }
}
