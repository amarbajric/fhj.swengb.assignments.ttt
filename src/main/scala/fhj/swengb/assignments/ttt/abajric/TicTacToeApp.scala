package fhj.swengb.assignments.ttt.abajric



import javafx.beans.value.{ObservableValue, ChangeListener}
import javafx.event.EventHandler
import javafx.scene.control.{ToggleGroup, Toggle, ToggleButton, Label, Button}
import javafx.scene.effect.DropShadow
import javafx.scene.input.MouseEvent
import java.net.URL
import javafx.scene.image.{Image, ImageView}
import java.util.ResourceBundle
import javafx.application.Application
import javafx.scene.layout.{GridPane, Pane}
import javafx.stage.Stage
import javafx.fxml.{FXML, FXMLLoader, Initializable}
import javafx.scene._
import scala.util.control.NonFatal


object TicTacToeApp {
  def main(args: Array[String]) {
    Application.launch(classOf[TicTacToeApp], args: _*)
  }
}


class TicTacToeApp extends javafx.application.Application {


  val Css = "/fhj/swengb/assignments/ttt/TicTacToe.css"
  val Fxml = "/fhj/swengb/assignments/ttt/TicTacToeApp.fxml"


  val loader = new FXMLLoader(getClass.getResource(Fxml))

  override def start(stage: Stage): Unit =
    try {
      stage.setTitle("TicTacHoe")
      loader.load[Parent]() // side effect
      val scene = new Scene(loader.getRoot[Parent]) //loads the default scene
      stage.setScene(scene)
      stage.setResizable(false) //window cannot be rescaled
      stage.getScene.getStylesheets.add(Css)
      stage.show()
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }
}


class TicTacToeAppController extends Initializable {

  @FXML var main_pane: Pane = _
  @FXML var grid_pane: GridPane = _
  @FXML var iv_topleft: ImageView = _
  @FXML var iv_topcenter: ImageView = _
  @FXML var iv_topright: ImageView = _
  @FXML var iv_middleleft: ImageView = _
  @FXML var iv_middlecenter: ImageView = _
  @FXML var iv_middleright: ImageView = _
  @FXML var iv_bottomleft: ImageView = _
  @FXML var iv_bottomcenter: ImageView = _
  @FXML var iv_bottomright: ImageView = _
  @FXML var switch_button: ToggleButton = _
  @FXML var btn_newGame: Button = _
  @FXML var log_msg: Label = _

  val dropShadow = new DropShadow()

  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    initializePane()

  }

  //val styleOrange: String = "-fx-background-color: orange;\n    -fx-border-color: black;\n    -fx-border-width: 0.1pt;"
  val styleRed: String = "-fx-background-color: linear-gradient(#ff5400, #be1d00);\n   -fx-background-insets: 0;\n    -fx-text-fill: white;"
  val grayStyle: String = "-fx-background-color: #D3D3D3;\n    -fx-border-color: black;\n    -fx-border-width: 0.1pt;"

  //Group for my ToggleButton to switch the design
  val group: ToggleGroup = new ToggleGroup()
  //ToggleListener which is switching the design onclick
  group.selectedToggleProperty().addListener(new ChangeListener[Toggle] {
    override def changed(observable: ObservableValue[_ <: Toggle], oldValue: Toggle, newValue: Toggle): Unit = {

      if (newValue != null)
        main_pane.setStyle(styleRed)
      else
        main_pane.setStyle(grayStyle)
    }
  })



  val effect: EventHandler[_ >: MouseEvent] = new EventHandler[MouseEvent] {
    override def handle(event: MouseEvent): Unit = {
      event.getSource match {
        case hoover: ImageView if hoover.getEffect == null => hoover.setEffect(dropShadow)
        case exit: ImageView if exit.getEffect != null => exit.setEffect(null)
        case _ => assert(false)
      }
    }
  }

  //initializes a new game
  var newGame = TicTacToe.apply()
  //eventhandler for onclick
  val mouseEventHandler: EventHandler[_ >: MouseEvent] = new EventHandler[MouseEvent] {

    override def handle(event: MouseEvent): Unit = {
      event.getSource match {
        case onClick: ImageView => {

          if(newGame.gameOver)
            gameOver()

          else if(newGame.nextPlayer == PlayerA) {
            onClick match {
              case tl if tl == iv_topleft && newGame.remainingMoves.contains(TopLeft)  => setPicture(iv_topleft,true); newGame = newGame.turn(TopLeft,newGame.nextPlayer); gameOver()
              case tc if tc == iv_topcenter && newGame.remainingMoves.contains(TopCenter) => setPicture(iv_topcenter,true); newGame =newGame.turn(TopCenter,newGame.nextPlayer) ; gameOver()
              case tr if tr == iv_topright && newGame.remainingMoves.contains(TopRight) => setPicture(iv_topright,true); newGame =newGame.turn(TopRight,newGame.nextPlayer); gameOver()
              case ml if ml == iv_middleleft && newGame.remainingMoves.contains(MiddleLeft) => setPicture(iv_middleleft,true); newGame =newGame.turn(MiddleLeft,newGame.nextPlayer) ; gameOver()
              case mc if mc == iv_middlecenter && newGame.remainingMoves.contains(MiddleCenter) => setPicture(iv_middlecenter,true); newGame =newGame.turn(MiddleCenter,newGame.nextPlayer); gameOver()
              case mr if mr == iv_middleright && newGame.remainingMoves.contains(MiddleRight) => setPicture(iv_middleright,true); newGame =newGame.turn(MiddleRight,newGame.nextPlayer); gameOver()
              case bl if bl == iv_bottomleft && newGame.remainingMoves.contains(BottomLeft) => setPicture(iv_bottomleft,true); newGame =newGame.turn(BottomLeft,newGame.nextPlayer); gameOver()
              case bc if bc == iv_bottomcenter && newGame.remainingMoves.contains(BottomCenter) => setPicture(iv_bottomcenter,true); newGame =newGame.turn(BottomCenter,newGame.nextPlayer); gameOver()
              case br if br == iv_bottomright && newGame.remainingMoves.contains(BottomRight) => setPicture(iv_bottomright,true); newGame = newGame.turn(BottomRight,newGame.nextPlayer); gameOver()
              case _ => log_msg.setText("Field already set!")
            }
          }
          else if(newGame.nextPlayer == PlayerB) {
            onClick match {
              case tl if tl == iv_topleft && newGame.remainingMoves.contains(TopLeft)  => setPicture(iv_topleft,false); newGame =newGame.turn(TopLeft,newGame.nextPlayer); gameOver()
              case tc if tc == iv_topcenter && newGame.remainingMoves.contains(TopCenter) => setPicture(iv_topcenter,false);newGame = newGame.turn(TopCenter,newGame.nextPlayer); gameOver()
              case tr if tr == iv_topright && newGame.remainingMoves.contains(TopRight) => setPicture(iv_topright,false); newGame =newGame.turn(TopRight,newGame.nextPlayer); gameOver()
              case ml if ml == iv_middleleft && newGame.remainingMoves.contains(MiddleLeft) => setPicture(iv_middleleft,false); newGame = newGame.turn(MiddleLeft,newGame.nextPlayer); gameOver()
              case mc if mc == iv_middlecenter && newGame.remainingMoves.contains(MiddleCenter) => setPicture(iv_middlecenter,false); newGame =newGame.turn(MiddleCenter,newGame.nextPlayer); gameOver()
              case mr if mr == iv_middleright && newGame.remainingMoves.contains(MiddleRight) => setPicture(iv_middleright,false); newGame =newGame.turn(MiddleRight,newGame.nextPlayer); gameOver()
              case bl if bl == iv_bottomleft && newGame.remainingMoves.contains(BottomLeft) => setPicture(iv_bottomleft,false); newGame =newGame.turn(BottomLeft,newGame.nextPlayer); gameOver()
              case bc if bc == iv_bottomcenter && newGame.remainingMoves.contains(BottomCenter) => setPicture(iv_bottomcenter,false); newGame =newGame.turn(BottomCenter,newGame.nextPlayer); gameOver()
              case br if br == iv_bottomright && newGame.remainingMoves.contains(BottomRight) => setPicture(iv_bottomright,false); newGame = newGame.turn(BottomRight,newGame.nextPlayer); gameOver()
              case _ => log_msg.setText("Field already set!")
            }

          }
        }
        case clickNewGame: Button => if (clickNewGame == btn_newGame){
           newGame = TicTacToe.apply()
          grid_pane.setDisable(false)
          iv_bottomcenter.setImage(null)
          iv_bottomleft.setImage(null)
          iv_bottomright.setImage(null)
          iv_middleleft.setImage(null)
          iv_middlecenter.setImage(null)
          iv_middleright.setImage(null)
          iv_topleft.setImage(null)
          iv_topcenter.setImage(null)
          iv_topright.setImage(null)
          log_msg.setText("New Game loaded!")

        }
        case _ => assert(false)
      }
    }
  }

  def gameOver(): Unit = {
    if (newGame.gameOver){
      if(!newGame.checkIfWon(PlayerA) && !newGame.checkIfWon(PlayerB)) {
        log_msg.setText("It's a draw!")
        grid_pane.setDisable(true)
      }
      else if(newGame.winner.get._1.equals(PlayerA)) {
        log_msg.setText("PlayerA won")
        grid_pane.setDisable(true)
      }
      else if(newGame.winner.get._1.equals(PlayerB)) {
        log_msg.setText("PlayerB won")
        grid_pane.setDisable(true)
      }

    }
  }


  def setPicture(iv: ImageView,pic: Boolean): Unit = {
    val urlX :String = "fhj/swengb/assignments/ttt/symbolX.png"
    val urlO : String = "fhj/swengb/assignments/ttt/symbolO.png"
    //if pic is true then set an "X"!!
    if (pic) {
      iv.setImage(new Image(urlX))
      iv.setFitHeight(190)
      iv.setFitWidth(190)
    }
    else {
      iv.setImage(new Image(urlO))
      iv.setFitHeight(190)
      iv.setFitWidth(190)
    }
    }


lazy val initPic : String = ""
def initializePane(): Unit = {
  //initialize the Board
  iv_topleft.setOnMouseClicked(mouseEventHandler) ;iv_topleft.setOnMouseEntered(effect) ;iv_topleft.setOnMouseExited(effect)
  iv_topcenter.setOnMouseClicked(mouseEventHandler); iv_topcenter.setOnMouseEntered(effect); iv_topcenter.setOnMouseExited(effect)
  iv_topright.setOnMouseClicked(mouseEventHandler); iv_topright.setOnMouseEntered(effect); iv_topright.setOnMouseExited(effect)
  iv_middleleft.setOnMouseClicked(mouseEventHandler); iv_middleleft.setOnMouseEntered(effect); iv_middleleft.setOnMouseExited(effect)
  iv_middlecenter.setOnMouseClicked(mouseEventHandler); iv_middlecenter.setOnMouseEntered(effect); iv_middlecenter.setOnMouseExited(effect)
  iv_middleright.setOnMouseClicked(mouseEventHandler) ; iv_middleright.setOnMouseEntered(effect); iv_middleright.setOnMouseExited(effect)
  iv_bottomleft.setOnMouseClicked(mouseEventHandler) ; iv_bottomleft.setOnMouseEntered(effect); iv_bottomleft.setOnMouseExited(effect)
  iv_bottomcenter.setOnMouseClicked(mouseEventHandler) ; iv_bottomcenter.setOnMouseEntered(effect); iv_bottomcenter.setOnMouseExited(effect)
  iv_bottomright.setOnMouseClicked(mouseEventHandler) ;  iv_bottomright.setOnMouseClicked(mouseEventHandler) ; iv_bottomright.setOnMouseExited(effect)

  //initialize Switch Buttons
  switch_button.setToggleGroup(group)
  switch_button.setSelected(false)

  //initialize button for new Game
  btn_newGame.setOnMouseClicked(mouseEventHandler)

}



}
