package prologfordummies.view

import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, Label, TextField}
import scalafx.scene.image.{Image, ImageView}
import prologfordummies.view.UIComponents.{logoView, styledButton, showCustomConfirm, backButton}
import scalafx.scene.layout._
import scalafx.scene.layout.{ColumnConstraints, GridPane, Priority, VBox, Region}
import scalafx.scene.text.Font
import scalafx.scene.control.Separator
import prologfordummies.Main
import scalafx.geometry.Orientation
import prologfordummies.model.UserSession
import prologfordummies.controller.EditUserController
import scalafx.animation.PauseTransition
import prologfordummies.model.User
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.ButtonType

object EditUserPage {
  
  def asParent: Region = new VBox {
    alignment = Pos.Center
    spacing = 10
    padding = Insets(20)
    style = "-fx-background-color: #f4f4f4;"

    val headerContainer = new VBox {

        spacing = 10
        padding = Insets(15, 20, 0, 20)

        val header = new HBox {
          alignment = Pos.CenterLeft
          children = Seq(
            backButton(EditUserController.handleBackToMenu())
          )
        }

        val logoBox = new StackPane {
          alignment = Pos.Center
          children = Seq(logoView(myFitWidth = 250))
        }

        children = Seq(header, logoBox)
    }
    val loginCard = new GridPane {
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

      val header = new Label(EditUserController.currentUserName) {
        font = Font.font("System", 24)
        style = "-fx-font-weight: bold; -fx-text-fill: #333;"
      }

      val userLabel = new Label("Inserisci il nuovo nome:")
      val userField = new TextField {
        promptText = "Inserisci username"
        maxWidth() = 300
      }

      val stateLabel = new Label("") {
        style = "-fx-text-fill: #66d32f; -fx-font-style: italic;"
        visible = false
      }

      val editBtn = styledButton(
        text = "Modifica Utenza",
        bgColor = "#4a90e2",
        textColor = "white",
        {
          val inputName = userField.text.value.trim

          UserSession.currentSessionUser match {
            case Some(currentUser) =>
              val updatedUser = currentUser.copy(username = User.Name(inputName))

              EditUserController.handleEdit(
                updatedUser,
                user => {
                  stateLabel.visible = true
                  userField.clear()
                  stateLabel.text = s"Utente aggiornato!"
                  stateLabel.style = "-fx-text-fill: #388e3c; -fx-font-weight: bold;"
                  
                  header.text = user.username.asString

                  val pause = new PauseTransition(scalafx.util.Duration(2000))
                  pause.onFinished = _ => stateLabel.visible = false
                  pause.play()
                },
                errorMsg => {
                  stateLabel.text = errorMsg
                  stateLabel.style = "-fx-text-fill: #d32f2f; -fx-font-weight: bold;"
                  stateLabel.visible = true
                }
              )
            case None =>
              stateLabel.text = "Nessun utente loggato!"
              stateLabel.visible = true
          }
        }
      )

      editBtn.maxWidth() = 200
      val separator = new Separator:
        orientation = Orientation.Horizontal
        maxWidth = Double.MaxValue

      val deleteBtn = styledButton(
        text = "Elimina Utenza",
        bgColor = "#f8f9fa",
        textColor = "#d32f2f",
        {
          UserSession.currentSessionUser.foreach { user =>
            showCustomConfirm(
              "Conferma Eliminazione",
              s"Sei sicuro di voler eliminare l'utente: ${user.username.asString}?",
              "Elimina",
              "Annulla",
              () => {
                EditUserController.handleDelete(
                  user,
                  _ => {
                    stateLabel.text = "Utente eliminato!"
                    stateLabel.style = "-fx-text-fill: #388e3c; -fx-font-weight: bold;"
                    stateLabel.visible = true
                    
                    val pause = new PauseTransition(scalafx.util.Duration(2000))
                    pause.onFinished = _ => EditUserController.backToRegistration()
                    pause.play()
                  },
                  error => {
                    stateLabel.text = error
                    stateLabel.style = "-fx-text-fill: #d32f2f; -fx-font-weight: bold;"
                    stateLabel.visible = true
                  }
                )
              }
            )
          }
        }
      )
      deleteBtn.maxWidth() = 200

      add(headerContainer, 0, 0)
      GridPane.setHalignment(headerContainer, scalafx.geometry.HPos.Center)
      add(header, 0, 1)
      GridPane.setHalignment(header, scalafx.geometry.HPos.Center)
      add(userLabel, 0, 2)
      GridPane.setHalignment(userLabel, scalafx.geometry.HPos.Center)
      add(userField, 0, 3)
      GridPane.setHalignment(userField, scalafx.geometry.HPos.Center)
      add(editBtn, 0, 4)
      GridPane.setHalignment(editBtn, scalafx.geometry.HPos.Center)
      add(stateLabel, 0, 5)
      GridPane.setHalignment(stateLabel, scalafx.geometry.HPos.Center)
      add(separator, 0, 6)
      GridPane.setHalignment(separator, scalafx.geometry.HPos.Center)
      add(deleteBtn, 0, 7) 
      GridPane.setHalignment(deleteBtn, scalafx.geometry.HPos.Center)
    
    }

    loginCard.maxWidthProperty().bind(width * 0.8)
    children = Seq(loginCard)
  }
}