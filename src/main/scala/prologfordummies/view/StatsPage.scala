package prologfordummies.view

import prologfordummies.controller.{LevelsController, StatsController}
import prologfordummies.view.UIComponents.backButton
import scalafx.geometry.{HPos, Insets, Pos}
import scalafx.scene.control.Label
import scalafx.scene.layout.{ColumnConstraints, GridPane, Priority, Region, VBox}
import scalafx.scene.text.Font

object StatsPage {

  def asParent: Region = new VBox {
    alignment = Pos.Center
    spacing = 10
    padding = Insets(20)
    style = "-fx-background-color: #f4f4f4;"

    val statsCard = new GridPane {
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
      column1.percentWidth = 100
      columnConstraints.add(column1)

      val header = new Label("Statistiche") {
        font = Font.font("System", 24)
        style = "-fx-font-weight: bold; -fx-text-fill: #333;"
      }

      val completedLevelsBox = new VBox {
        spacing = 4
        children = Seq(
          new Label("Livelli completati") {
            style = "-fx-text-fill: #666; -fx-font-size: 14px;"
          },
          new Label(StatsController.completedLevelsCount.toString + " di " + StatsController.totalLevelsCount.toString) {
            style = "-fx-text-fill: #222; -fx-font-size: 20px; -fx-font-weight: bold;"
          }
        )
      }

      val accuracyBox = new VBox {
        spacing = 4
        children = Seq(
          new Label("Percentuale risposte corrette") {
            style = "-fx-text-fill: #666; -fx-font-size: 14px;"
          },
          new Label(f"${StatsController.totalCorrectsPercentage}%.2f %%") {
            style = "-fx-text-fill: #222; -fx-font-size: 20px; -fx-font-weight: bold;"
          }
        )
      }

      val attemptsBox = new VBox {
        spacing = 4
        children = Seq(
          new Label("Tentativi totali") {
            style = "-fx-text-fill: #666; -fx-font-size: 14px;"
          },
          new Label(StatsController.totalAttempts.toString) {
            style = "-fx-text-fill: #222; -fx-font-size: 20px; -fx-font-weight: bold;"
          }
        )
      }

      val hoursBox = new VBox {
        spacing = 4
        children = Seq(
          new Label("Ore di utilizzo") {
            style = "-fx-text-fill: #666; -fx-font-size: 14px;"
          },
          new Label(f"${StatsController.totalHoursUsed}%.2f h") {
            style = "-fx-text-fill: #222; -fx-font-size: 20px; -fx-font-weight: bold;"
          }
        )
      }

      val backBtn = backButton(LevelsController.handleBackToMenu())

      add(header, 0, 0)
      GridPane.setHalignment(header, HPos.Center)

      add(completedLevelsBox, 0, 1)
      add(accuracyBox, 0, 2)
      add(attemptsBox, 0, 3)
      add(hoursBox, 0, 4)

      add(backBtn, 0, 5)
      GridPane.setHalignment(backBtn, HPos.Center)
    }

    statsCard.maxWidthProperty().bind(width * 0.8)
    children = Seq(statsCard)
  }
}
