package prologfordummies.view

import prologfordummies.controller.LoginController
import prologfordummies.view.UIComponents.{logoView, styledButton}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, Label, Separator, TextField}
import scalafx.scene.layout.{
  ColumnConstraints,
  GridPane,
  Priority,
  Region,
  VBox
}
import scalafx.scene.text.Font
import scalafx.geometry.Orientation
import scalafx.scene.image.ImageView

/** Schermata di login dell'applicazione. */
object LoginPage {

  def asParent: Region = new VBox {
    alignment = Pos.Center
    spacing = 10
    padding = Insets(20)
    style = "-fx-background-color: #f4f4f4;"

    val logo: ImageView = logoView(myFitWidth = 250)

    val loginCard: GridPane = new GridPane {
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

      val header: Label = new Label("Inserisci il tuo account") {
        font = Font.font("System", 24)
        style = "-fx-font-weight: bold; -fx-text-fill: #333;"
      }

      val userLabel = new Label("Username:")
      val userField: TextField = new TextField {
        promptText = "Inserisci username"
      }

      userField.maxWidth() = 300

      val stateLabel: Label = new Label("") {
        style = "-fx-text-fill: #d32f2f; -fx-font-style: italic;"
        visible = false
      }

      val loginBtn: Button = styledButton(
        text = "Accedi",
        bgColor = "#4a90e2",
        textColor = "white",
        LoginController.login(userField.text.value.trim) match {
          case LoginController.Success(user) =>
            LoginController.goToMenu()
          case LoginController.Error(message) =>
            stateLabel.text = message
            stateLabel.visible = true
        }
      )

      loginBtn.maxWidth() = 200
      val separator: Separator = new Separator {
        orientation = Orientation.Horizontal
        maxWidth = Double.MaxValue
      }

      val registrationLabel = new Label("Nuovo Utente?")

      val registerBtn: Button = styledButton(
        text = "Registrati",
        bgColor = "#e0e0e0",
        textColor = "#333",
        LoginController.goToRegistration()
      )

      registerBtn.maxWidth() = 200

      add(logo, 0, 0)
      GridPane.setHalignment(logo, scalafx.geometry.HPos.Center)
      GridPane.setMargin(logo, Insets(0, 0, 20, 0))

      add(header, 0, 1, 2, 1)
      GridPane.setHalignment(header, scalafx.geometry.HPos.Center)
      add(userLabel, 0, 2)
      GridPane.setHalignment(userLabel, scalafx.geometry.HPos.Center)
      add(userField, 0, 3)
      GridPane.setHalignment(userField, scalafx.geometry.HPos.Center)
      add(loginBtn, 0, 4)
      GridPane.setHalignment(loginBtn, scalafx.geometry.HPos.Center)
      add(stateLabel, 0, 5)
      GridPane.setHalignment(stateLabel, scalafx.geometry.HPos.Center)
      add(separator, 0, 6)
      add(registrationLabel, 0, 7)
      GridPane.setHalignment(registrationLabel, scalafx.geometry.HPos.Center)
      add(registerBtn, 0, 8)
      GridPane.setHalignment(registerBtn, scalafx.geometry.HPos.Center)
    }

    loginCard.maxWidthProperty().bind(width * 0.8)
    children = Seq(loginCard)
  }
}
