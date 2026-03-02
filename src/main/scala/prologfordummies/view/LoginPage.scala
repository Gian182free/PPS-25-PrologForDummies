package prologfordummies.view

import prologfordummies.model.{User, UserSession}
import prologfordummies.services.{UserRepositoryImpl, UserService}
import prologfordummies.view.UIComponents.{logoView, styledButton}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Label, TextField}
import scalafx.scene.layout.{ColumnConstraints, GridPane, Priority, Region, VBox}
import scalafx.scene.text.Font
import scalafx.scene.control.Separator
import scalafx.geometry.Orientation


object LoginPage {
  
  def asParent: Region = new VBox {
    alignment = Pos.Center
    spacing = 10
    padding = Insets(20)
    style = "-fx-background-color: #f4f4f4;"

    val logo = logoView(myFitWidth = 250)

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

      val header = new Label("Inserisci il tuo account") {
        font = Font.font("System", 24)
        style = "-fx-font-weight: bold; -fx-text-fill: #333;"
      }

      val userLabel = new Label("Username:")
      val userField = new TextField {
        promptText = "Inserisci username"
      }

      val loginBtn = styledButton(
        text = "Accedi",
        bgColor = "#4a90e2",
        textColor = "white",
        {
          val username = userField.text.value.trim
          if (username.nonEmpty) {
            val repo = UserRepositoryImpl.fileRepository
            repo.findByName(username) match {
              case Some(user) => UserSession.login(user); prologfordummies.Main.setPage(MenuPage.asParent)
              case None =>
                println(s"Utente $username non trovato")
            }
          }
          
          prologfordummies.Main.setPage(MenuPage.asParent)  
        }
        
      )
      

      val separator = new Separator:
        orientation = Orientation.Horizontal
        maxWidth = Double.MaxValue

      val registrationLabel = new Label("Nuovo Utente?")

      val registerBtn =styledButton(
        text = "Registrati",
        bgColor = "#e0e0e0",
        textColor = "#333",
        prologfordummies.Main.setPage(RegistrationPage.asParent)
      )

      add(logo, 0, 0)
      GridPane.setHalignment(logo, scalafx.geometry.HPos.Center)
      GridPane.setMargin(logo, Insets(0, 0, 20, 0))

      add(header, 0, 1, 2, 1)
      GridPane.setHalignment(header, scalafx.geometry.HPos.Center)
      add(userLabel, 0, 2)
      add(userField, 0, 3)
      add(loginBtn, 0, 4)
      add(separator, 0, 5)
      add(registrationLabel, 0, 6)
      GridPane.setHalignment(registrationLabel, scalafx.geometry.HPos.Center)
      add(registerBtn, 0, 7)
    }

    loginCard.maxWidthProperty().bind(width * 0.8)
    children = Seq(loginCard)
  }
}