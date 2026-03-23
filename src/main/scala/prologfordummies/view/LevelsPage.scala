package prologfordummies.view

import prologfordummies.view.UIComponents.{styledButton, backButton}
import scalafx.geometry.{HPos, Insets, Pos}
import scalafx.scene.control.Label
import scalafx.scene.layout.{ColumnConstraints, GridPane, Priority, Region, VBox}
import scalafx.scene.text.Font
import prologfordummies.model.Level
import prologfordummies.controller.LevelsController
import scalafx.scene.layout.HBox


object LevelsPage {

  private val levels: List[Level] = LevelsController.loadLevels()

  def asParent: Region = new VBox {
    alignment = Pos.TopCenter
    spacing = 0
    style = "-fx-background-color: white;"

    val header = new GridPane {
      padding = Insets(10, 20, 10, 20)
      style = "-fx-background-color: #f8f8f8; -fx-border-color: #ddd; -fx-border-width: 0 0 2 0;"
      

      val backBtnContainer = new HBox {
      alignment = Pos.TopLeft
      
      children = Seq(
        backButton(LevelsController.handleBackToMenu())
      )
    }
      
      val title = new Label("Livelli") {
        font = Font.font("System", 20)
        style = "-fx-font-weight: bold;"
      }

      add(backBtnContainer, 0, 0)
      add(title, 1, 0)
      columnConstraints = Seq(
        new ColumnConstraints { percentWidth = 20 },
        new ColumnConstraints { percentWidth = 60; halignment = HPos.Center },
        new ColumnConstraints { percentWidth = 20 }
      )
    }

    val levelsContainer = new VBox {
      children = levels.map { lvl =>
        levelTile(
          lvl.title.asString, 
          lvl.questions.size,
          LevelsController.countCorrectAnswers(lvl),
          LevelsController.isLevelCompleted(lvl),
          LevelsController.loadLevel(lvl)
        )
      }
    }

    val scrollPane = new scalafx.scene.control.ScrollPane {
      content = levelsContainer
      fitToWidth = true
      vbarPolicy = scalafx.scene.control.ScrollPane.ScrollBarPolicy.AsNeeded
      style = "-fx-background-color: transparent; -fx-background: white;"
      vgrow = Priority.Always
    }

    children = Seq(header, scrollPane)
  }
}

private def levelTile(title: String, questionsCount: Int, correctAnswers: Int, completed: Boolean, onInizia: => Unit): Region = new GridPane {
  padding = Insets(15)
  hgap = 20
  vgap = 5
  style = """
    -fx-background-color: white;
    -fx-border-color: #ddd;
    -fx-border-width: 0 0 1 0;
  """
  
  val colInfo = new ColumnConstraints { hgrow = Priority.Always }
  val colAction = new ColumnConstraints { halignment = scalafx.geometry.HPos.Right }
  columnConstraints = Seq(colInfo, colAction)

  val titleLabel = new Label(title) {
    style = "-fx-font-size: 18px; -fx-font-weight: bold;"
  }
  
  val infoLabel = new Label(s"Domande: $questionsCount") {
    style = "-fx-text-fill: #666;"
  }

  val statusLabel = new Label(if (completed) "Completato" else "Non completato") {
    style =
      if (completed) "-fx-text-fill: #2e7d31; -fx-font-weight: bold;"
      else "-fx-text-fill: #b71c1c;"
  }

  val completedLabel = new Label(s"$correctAnswers risposte corrette su $questionsCount")

  val startLvlBtn = styledButton("Inizia ▷", "#ffffff", "#333", onInizia)
  startLvlBtn.style = startLvlBtn.style.value + "-fx-border-color: #333; -fx-border-radius: 5;"
  startLvlBtn.maxWidth() = 200

  add(titleLabel, 0, 0)
  add(infoLabel, 0, 1)
  add(completedLabel, 1, 1)
  add(statusLabel, 0, 2)
  add(startLvlBtn, 2, 0, 1, 3)
}