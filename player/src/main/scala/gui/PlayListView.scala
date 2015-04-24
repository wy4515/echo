import javafx.event.EventHandler

import module.SongModel

import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.geometry.{Insets, Pos, Orientation}
import scalafx.scene.Node
import scalafx.scene.control.{Button, Label, ListView}
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{HBox, BorderPane, StackPane}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import scalafx.stage.Popup

/**
 * Created by yangwu on 4/5/15.
 */

class PlayListView (config:ClusterConfig) {
  /**
   * need actual play list here
   */
  def initView(): Node = {
    val seq = Seq("Rolling In The Deep","Heartbreaker")

    val l = new ListView[String] {
      id = "playList"
      items = ObservableBuffer(seq)
      orientation = Orientation.VERTICAL
      maxWidth = 150

      selectionModel().selectedItem.onChange {
        (_, _, newValue) => {
          val p = new PlayList
          p.select(newValue)
          println("- CUR. SRC:"+MusicName.name)
          println("SELECT: "+newValue)
        }
      }

    }

    l;
  }

  def voting(song:String) : Unit = {
      println("VOTE: "+song)
      val pop = createAlertPopup(song)
      println("- PLAY LIST VOTING ; THREAD -")
      pop.show(Main.stage,
        (Main.stage.width() - pop.width()) / 2.0 + Main.stage.x(),
        (Main.stage.height() - pop.height()) / 2.0 + Main.stage.y())
  }

  def showup(song:String): Unit = {
    val popWindow = createAlertPopup(song)
    popWindow.show(Main.stage,
      (Main.stage.width() - popWindow.width()) / 2.0 + Main.stage.x(),
      (Main.stage.height() - popWindow.height()) / 2.0 + Main.stage.y())
  }

  private def createAlertPopup(popupText: String) = new Popup {
    inner =>
    println("- CREATING -")
    content.add(new StackPane {
      children = List(
        new Rectangle {
          width = 300
          height = 200
          arcWidth = 15
          arcHeight = 15
          fill = Color.Black
          stroke = Color.Gray
          strokeWidth = 2
        },
        new BorderPane {
          center = new Label {
            text = "Proposed Song: "+popupText
            wrapText = true
            maxWidth = 280
            maxHeight = 140
          }
          bottom = new HBox {
            maxWidth = 280
            maxHeight = 140
            children = List(
              new Button("OK") {
                onAction = { e: ActionEvent => {
                    config.agree()
                    inner.hide()
                  }
                }
                alignmentInParent = Pos.BOTTOM_LEFT
                margin = Insets(10, 0, 10, 0)
              },
              new Button("PASS") {
                onAction = {e: ActionEvent => {
                    config.disagree()
                    inner.hide()
                  }
                }
                alignmentInParent = Pos.BOTTOM_RIGHT
                margin = Insets(10, 0, 10, 0)
              }
            )
          }
        }
      )
    }.delegate
    )
  }
}