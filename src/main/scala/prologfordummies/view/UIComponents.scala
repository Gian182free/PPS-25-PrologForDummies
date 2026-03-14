package prologfordummies.view

import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Node
import scalafx.scene.control.Button
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout._
import scalafx.stage.{Modality, Stage}
import prologfordummies.Main
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.stage.StageStyle
object UIComponents {
  private val defaultBg = "-fx-background-color: #f4f4f4;"
  private val cardStyle =
    """
      -fx-background-color: white;
      -fx-background-radius: 10;
      -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);
    """

  def page(items: Node*): VBox = new VBox {
    alignment = Pos.Center
    spacing = 10
    padding = Insets(20)
    style = defaultBg
    children = items
  }

  def logoView(myFitWidth: Double = 300): ImageView = new ImageView {
    image = new Image(
      getClass.getResourceAsStream("/logo_pfd.png"),
      400,
      0,
      true,
      true
    )
    preserveRatio = true
    this.fitWidth = myFitWidth
    smooth = true
  }

  def styledButton(
      text: String,
      bgColor: String = "#e0e0e0",
      textColor: String = "#333",
      onClick: => Unit = {}
  ): Button =
    new Button(text) {
      maxWidth = Double.MaxValue
      style = s"""
           -fx-background-color: $bgColor;
           -fx-text-fill: $textColor;
           -fx-font-weight: bold;
           -fx-cursor: hand;
        """
      onAction() = _ => onClick
    }

  /*
   * Modale con titolo, messaggio e testo dei bottoni customizzabile.
   */
  def showCustomConfirm(
      head: String,
      message: String,
      confirmButtonMsg: String,
      declinedButtonMsg: String,
      onConfirm: () => Unit
  ): Unit = {
    val dialog: Stage = new Stage {
      initModality(Modality.ApplicationModal)
      initOwner(Main.stage)
      initStyle(StageStyle.Undecorated)
      resizable = false

      scene = new Scene(500, 300) {
        root = new VBox {
          alignment = Pos.Center
          spacing = 25
          padding = Insets(30)
          style = """
            -fx-background-color: white;
            -fx-background-radius: 0; 
            -fx-border-color: #333333;
            -fx-border-width: 1;
            -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 10, 0, 0, 0);
        """
          children = Seq(
            new Label(head) {
              style =
                "-fx-font-weight: bold; -fx-font-size: 22px; -fx-text-fill: #333;"
            },
            new Label(message) {
              wrapText = true
              alignment = Pos.Center
              maxWidth = 440
              style = "-fx-font-size: 15px; -fx-text-fill: #555;"
            },
            new HBox {
              alignment = Pos.Center
              spacing = 20
              children = Seq(
                {
                  val buttonCancel = styledButton(
                    declinedButtonMsg,
                    "#eeeeee",
                    "#333", {
                      delegate.getScene.getWindow.hide()
                    }
                  )
                  buttonCancel.minWidth = 120
                  buttonCancel.style =
                    buttonCancel.style.value + "-fx-background-radius: 0;"
                  buttonCancel
                }, {
                  val buttonConfirm = styledButton(
                    confirmButtonMsg,
                    "#d32f2f",
                    "white", {
                      onConfirm()
                      delegate.getScene.getWindow.hide()
                    }
                  )
                  buttonConfirm.minWidth = 120
                  buttonConfirm.style =
                    buttonConfirm.style.value + "-fx-background-radius: 0;"
                  buttonConfirm
                }
              )
            }
          )
        }
      }
    }

    dialog.onShowing = _ => {
      val owner = Main.stage
      dialog.x = owner.x() + (owner.width() - 500) / 2
      dialog.y = owner.y() + (owner.height() - 300) / 2
    }
    dialog.showAndWait()
  }

  def cardGrid(myHGap: Double = 10, myVGap: Double = 10)(
      init: GridPane => Unit
  ): GridPane =
    new GridPane {
      alignment = Pos.Center
      this.hgap = myHGap
      this.vgap = myVGap
      padding = Insets(25)
      style = cardStyle

      val col = new ColumnConstraints()
      col.hgrow = Priority.Always
      columnConstraints.add(col)

      init(this)
    }

  def backButton(onClick: => Unit): Button = {
    val btn = new Button("←") {
      style = """
        -fx-background-color: #e0e0e0;
        -fx-text-fill: #333;
        -fx-border-color: #999;
        -fx-font-size: 20px;
        -fx-font-weight: bold;
        -fx-cursor: hand;
        -fx-background-radius: 0;
        -fx-border-radius: 0;
    """
      onAction() = _ => onClick

      onMouseEntered = _ =>
        style = style.value + "-fx-background-color: #cccccc;"
      onMouseExited = _ =>
        style = style.value + "-fx-background-color: #e0e0e0;"
    }
    btn
  }
}
