package prologfordummies.view

import prologfordummies.controller.QuizController
import prologfordummies.model.Level
import prologfordummies.view.UIComponents.{logoView, styledButton}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, Label, TextArea}
import scalafx.scene.layout.{ColumnConstraints, GridPane, Priority, Region, VBox}
import scalafx.scene.text.Font

object QuizPage {

  def asParent(level: Level, index: Int = 0): Region = new VBox {
    alignment = Pos.Center
    spacing = 10
    padding = Insets(20)
    style = "-fx-background-color: #f4f4f4;"

    private val currentQuestion = level.questions(index)

    val logo = logoView(myFitWidth = 250)

    val quizCard = new GridPane {
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
      column1.fillWidth = true
      column1.percentWidth = 50
      columnConstraints.add(column1)

      val column2 = new ColumnConstraints()
      column2.hgrow = Priority.Always
      column2.fillWidth = true
      column2.percentWidth = 50
      columnConstraints.add(column2)

      val textArea = new TextArea {
        text = level.theory.asString
        editable = false
        wrapText = true
        prefWidth = 500
        prefHeight = 350
        minWidth = 300
      }

      val header = new Label(s"Quiz: ${level.title.asString}") {
        font = Font.font("System", 24)
        style = "-fx-font-weight: bold; -fx-text-fill: #333;"
      }

      val questionLabel = new Label(currentQuestion.question) {
        font = Font.font("System", 18)
        style = "-fx-text-fill: #555;"
        wrapText = true
      }

      val answers = currentQuestion.answers.padTo(4, "")

      lazy val option1Btn: Button = styledButton(
        text = answers.head,
        bgColor = "#e0e0e0",
        textColor = "#333",
        QuizController.submitAnswer(option1Btn, currentQuestion, answers.head, level, index)
      )

      lazy val option2Btn: Button = styledButton(
        text = answers(1),
        bgColor = "#e0e0e0",
        textColor = "#333",
        QuizController.submitAnswer(option2Btn, currentQuestion, answers(1), level, index)
      )

      lazy val option3Btn: Button = styledButton(
        text = answers(2),
        bgColor = "#e0e0e0",
        textColor = "#333",
        QuizController.submitAnswer(option3Btn, currentQuestion, answers(2), level, index)
      )

      lazy val option4Btn: Button = styledButton(
        text = answers(3),
        bgColor = "#e0e0e0",
        textColor = "#333",
        QuizController.submitAnswer(option4Btn, currentQuestion, answers(3), level, index)
      )

      val backBtn = styledButton(
        text = "Torna ai Livelli",
        bgColor = "#e0e0e0",
        textColor = "#333",
        QuizController.backToLevels()
      )

      add(logo, 0, 0, 2, 1)
      GridPane.setHalignment(logo, scalafx.geometry.HPos.Center)
      GridPane.setMargin(logo, Insets(0, 0, 20, 0))

      add(textArea, 0, 1, 2, 1)
      GridPane.setHalignment(textArea, scalafx.geometry.HPos.Center)

      add(header, 0, 2, 2, 1)
      GridPane.setHalignment(header, scalafx.geometry.HPos.Center)
      add(questionLabel, 0, 3, 2, 1)
      GridPane.setHalignment(questionLabel, scalafx.geometry.HPos.Center)
      add(option1Btn, 0, 4)
      add(option2Btn, 1, 4)
      add(option3Btn, 0, 5)
      add(option4Btn, 1, 5)
      add(backBtn, 0, 6, 2, 1)
      GridPane.setHalignment(backBtn, scalafx.geometry.HPos.Center)
    }

    quizCard.maxWidthProperty().bind(width * 0.8)
    children = Seq(quizCard)
  }
}