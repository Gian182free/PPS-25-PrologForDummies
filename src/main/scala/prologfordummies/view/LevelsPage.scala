package prologfordummies.view

import prologfordummies.controller
import prologfordummies.view.UIComponents.{logoView, styledButton}
import scalafx.geometry.{HPos, Insets, Pos}
import scalafx.scene.control.{Label, TextField}
import scalafx.scene.layout.{ColumnConstraints, GridPane, Priority, Region, VBox}
import scalafx.scene.text.Font
import scalafx.scene.control.Separator
import scalafx.geometry.Orientation
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
      children = styledButton(
        text = "←",
        bgColor = "#white",
        textColor = "#333",
        // TODO: reindirizzare al menu
        LevelsController.handleBackToMenu()
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
          //println(s"Inizia livello: ${lvl.title.asString}");
          LevelsController.loadLevel()
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

private def levelTile(title: String, questionsCount: Int, onInizia: => Unit): Region = new GridPane {
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

  val startLvlBtn = styledButton("Inizia ▷", "#ffffff", "#333", onInizia)
  startLvlBtn.style = startLvlBtn.style.value + "-fx-border-color: #333; -fx-border-radius: 5;"

  add(titleLabel, 0, 0)
  add(infoLabel, 0, 1)
  add(startLvlBtn, 1, 0, 1, 2)
}