package prologfordummies.view

import prologfordummies.controller.QuizController
import prologfordummies.model.Level
import prologfordummies.view.UIComponents.{logoView, styledButton}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, Label, TextArea}
import scalafx.scene.layout.{ColumnConstraints, GridPane, Priority, Region, VBox}
import scalafx.scene.text.Font

object OpenQuizPage {

  def asParent(level: Level, index: Int = 0): Region = new VBox {
    alignment = Pos.Center
    spacing = 10
    padding = Insets(20)
    style = "-fx-background-color: #f4f4f4;"

    // Domanda corrente
    private val currentQuestion = level.questions(index)

    val logo = logoView(myFitWidth = 250)

    val quizCard = new GridPane {
      alignment = Pos.Center
      hgap = 10
      vgap = 15
      padding = Insets(25)
      style =
        """
          -fx-background-color: white;
          -fx-background-radius: 10;
          -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);
        """

      // Colonne per responsive layout (non obbligatorie ma coerenti con QuizPage)
      val column1 = new ColumnConstraints()
      column1.hgrow = Priority.Always
      column1.percentWidth = 100
      columnConstraints.add(column1)

      val header = new Label(s"Quiz: ${level.title.asString}") {
        font = Font.font("System", 24)
        style = "-fx-font-weight: bold; -fx-text-fill: #333;"
      }

      val questionLabel = new Label(currentQuestion.question) {
        font = Font.font("System", 18)
        style = "-fx-text-fill: #555;"
        wrapText = true
      }

      val answerArea = new TextArea {
        promptText = "Scrivi qui la tua risposta..."
        wrapText = true
        prefWidth = 500
        prefHeight = 120
      }

      // Pulsante conferma
      lazy val confirmBtn: Button = styledButton(
        text = "Conferma",
        bgColor = "#e0e0e0",
        textColor = "#333",
        {
          val userAnswer = answerArea.text.value.trim

          QuizController.submitAnswer(confirmBtn, currentQuestion, userAnswer, level, index)
        }
      )

      val backBtn = styledButton(
        text = "Torna ai Livelli",
        bgColor = "#e0e0e0",
        textColor = "#333",
        QuizController.confirmBackToLevels()
      )

      // Layout
      add(logo, 0, 0)
      GridPane.setMargin(logo, Insets(0, 0, 20, 0))
      GridPane.setHalignment(logo, scalafx.geometry.HPos.Center)

      add(header, 0, 1)
      GridPane.setHalignment(header, scalafx.geometry.HPos.Center)

      add(questionLabel, 0, 2)

      add(answerArea, 0, 3)

      add(confirmBtn, 0, 4)
      GridPane.setHalignment(confirmBtn, scalafx.geometry.HPos.Center)

      add(backBtn, 0, 5)
      GridPane.setHalignment(backBtn, scalafx.geometry.HPos.Center)
    }

    quizCard.maxWidthProperty().bind(width * 0.8)
    children = Seq(quizCard)
  }
}