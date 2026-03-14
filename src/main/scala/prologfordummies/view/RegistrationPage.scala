package prologfordummies.view

import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Label, TextField}
import prologfordummies.view.UIComponents.{logoView, styledButton}
import scalafx.scene.layout._
import scalafx.scene.text.Font
import prologfordummies.controller.RegistrationController
import prologfordummies.Main
import prologfordummies.view.UIComponents.{logoView, styledButton}
import scalafx.animation.PauseTransition

object RegistrationPage {
  def asParent: Region = new VBox {
    alignment = Pos.Center
    spacing = 10
    padding = Insets(20)
    style = "-fx-background-color: #f4f4f4;"
    val mainCard = new VBox {
      alignment = Pos.Center
      spacing = 20
      padding = Insets(30)
      style = """
            -fx-background-color: white;
            -fx-background-radius: 15;
            -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);
          """
      val headerContainer = new VBox {

        spacing = 10
        padding = Insets(15, 20, 0, 20)

        val header = new HBox {
          alignment = Pos.CenterLeft

          val backBtn = styledButton(
            text = "←",
            bgColor = "transparent",
            textColor = "#333",
            RegistrationController.handleBackToLogin()
          )

          backBtn.style =
            """
            -fx-background-color: #e0e0e0;
            -fx-text-fill: #333;
            -fx-border-color: #999;
            -fx-font-size: 20px;
            -fx-font-weight: bold;
            -fx-cursor: hand;
            """

          children = Seq(backBtn)
        }

        val logoBox = new StackPane {
          alignment = Pos.Center
          children = Seq(logoView(myFitWidth = 250))
        }

        children = Seq(header, logoBox)
      }

      val registrationForm = new GridPane {
        alignment = Pos.Center
        hgap = 10
        vgap = 15
        
        val userLabel = new Label("Username:") {
          font = Font.font("System", 18)
        }

        val userField = new TextField {
          promptText = "User"
          prefWidth = 300
        }

        userField.maxWidth() = 200

        val stateLabel = new Label("") {
          style = "-fx-text-fill: #d32f2f; -fx-font-style: italic;"
          visible = false
          wrapText = true
          maxWidth = 300
        }

        val registerBtn = styledButton(
          text = "Registrati",
          bgColor = "#e0e0e0",
          textColor = "#333",
          {
            val inputName = userField.text.value.trim
            
            RegistrationController.handleRegistration(
              inputName,
              user => {
                stateLabel.visible = true
                stateLabel.text = s"Registrazione avvenuta con successo! Benvenuto ${user.username}!"
                stateLabel.style = "-fx-text-fill: #388e3c; -fx-font-style: italic;"
                val pause = new PauseTransition(scalafx.util.Duration(2000))
                pause.onFinished = _ => RegistrationController.handleBackToLogin()
                pause.play()
              },
              errorMsg => {
                stateLabel.text = errorMsg
                stateLabel.visible = true
                stateLabel.style = "-fx-text-fill: #d32f2f; -fx-font-style: italic;"
              }
            )
          }
        )
        registerBtn.maxWidth() = 200

        add(userLabel, 0, 0)
        add(userField, 1, 0)
        add(stateLabel, 0, 1, 2, 1)
        GridPane.setHalignment(stateLabel, scalafx.geometry.HPos.Center)
        add(registerBtn, 0, 2, 2, 1)
        GridPane.setHalignment(registerBtn, scalafx.geometry.HPos.Center)
        
        val inputName = userField.text.value.trim
      }
      children = Seq(headerContainer, registrationForm)
    }

    mainCard.maxWidthProperty().bind(width * 0.8)

    children = Seq(mainCard)
  }
}