package fhj.swengb.assignments.ttt.abajric


import javafx.beans.value.{ObservableValue, ChangeListener}
import javafx.event.EventHandler
import javafx.scene.control._
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
import scala.util.Random


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
  @FXML var image_view_p1: ImageView = _
  @FXML var image_view_p2: ImageView = _
  @FXML var text_field_p1: TextField = _
  @FXML var text_field_p2: TextField = _
  @FXML var switch_button: ToggleButton = _
  @FXML var clr_text_fields: Button = _
  @FXML var btn_newGame: Button = _
  //@FXML var btn_newGame_sp: Button = _
  @FXML var log_msg: Label = _

  val dropShadow = new DropShadow()

  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    initializePane()
  }


  //the two symbols which are set as a new image when a turn is made
  val urlX :String = "fhj/swengb/assignments/ttt/symbolX.png"
  val urlO : String = "fhj/swengb/assignments/ttt/symbolO.png"

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


    //eventhandler for showing shadow effects while hoovering
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

  //the map is needed in order to check the userData behind every ImageView with this map to set the next turn and the image at the right place (mouseeventhadler)
  val checkMap: Map[Int, (ImageView, TMove)] = Map(0 -> (iv_topleft,TopLeft), 1 -> (iv_topcenter,TopCenter), 2 -> (iv_topright,TopRight), 3 -> (iv_middleleft,MiddleLeft),
    4 -> (iv_middlecenter,MiddleCenter),5 -> (iv_middleright,MiddleRight), 6 -> (iv_bottomleft,BottomLeft), 7 -> (iv_bottomcenter,BottomCenter), 8 -> (iv_bottomright,BottomRight))

  //eventhandler for onclick
    val mouseEventHandler: EventHandler[_ >: MouseEvent] = new EventHandler[MouseEvent] {

      override def handle(event: MouseEvent): Unit = {
        event.getSource match {
          case onclick: ImageView => {

            if (newGame.gameOver)
              gameOver()

            else if (newGame.nextPlayer == PlayerA) {
              val l = checkMap.get(onclick.getUserData.toString.toInt)
              val x = checkMap.get(onclick.getUserData.toString.toInt)

              if(newGame.remainingMoves.contains(l.get._2)){
                onclick.setImage(new Image(urlX))
                newGame = newGame.turn(l.get._2,newGame.nextPlayer)
                log_msg.setText(s"${text_field_p2.getText.toUpperCase()} - its your turn!")
                gameOver()
              }
              else
                log_msg.setText("Field already set!")
              }

            else if (newGame.nextPlayer == PlayerB) {

              if(newGame.remainingMoves.contains(checkMap(onclick.getUserData.toString.toInt)._2)){
                onclick.setImage(new Image(urlO))
                newGame = newGame.turn(checkMap(onclick.getUserData.toString.toInt)._2,newGame.nextPlayer)
                log_msg.setText(s"${text_field_p1.getText.toUpperCase} - its your turn!")
                gameOver()
              }
              else
                log_msg.setText("Field already set!")
              }
          }

          case btnGame: Button if btn_newGame == btn_newGame => {

            if(!text_field_p1.getText().trim().isEmpty && !text_field_p2.getText().trim().isEmpty ) {
              if(text_field_p1.getText().trim == text_field_p2.getText().trim){
                log_msg.setText("Players should have different names!")
              }
              else {
                //disabling the textfields after the button is clicked
                text_field_p1.setDisable(true)
                text_field_p2.setDisable(true)
                //enabling gridpane
                grid_pane.setDisable(false)
                //disabling the clear button
                clr_text_fields.setDisable(true)
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
                log_msg.setText("New MPGame loaded!")
              }
            }
            else
              log_msg.setText("Please enter your names first!")
          }
          case _ => assert(false)
        }
      }
    }

  def gameOver(): Unit = {
    //get the text from the playernames form to display them
    val p1: String = text_field_p1.getText
    val p2: String = text_field_p2.getText
    if (newGame.gameOver){

      //the textfields are getting enabled again to maybe insert new Playernames
      text_field_p1.setDisable(false)
      text_field_p2.setDisable(false)
      //enable the clear button again
      clr_text_fields.setDisable(false)

      if(!newGame.checkIfWon(PlayerA) && !newGame.checkIfWon(PlayerB)) {
        log_msg.setText("It's a draw!")
        grid_pane.setDisable(true)
      }
      else if(newGame.winner.get._1.equals(PlayerA)) {
        log_msg.setText(s"'$p1' won!")
        grid_pane.setDisable(true)
      }
      else if(newGame.winner.get._1.equals(PlayerB)) {
        log_msg.setText(s"'$p2' won!")
        grid_pane.setDisable(true)
      }

    }
  }


  //clears the textfields
  def clr(): Unit = {
    text_field_p1.clear()
    text_field_p2.clear()
  }



def initializePane(): Unit = {
  //initialize the Board
  iv_topleft.setOnMouseClicked(mouseEventHandler) ;iv_topleft.setOnMouseEntered(effect) ;iv_topleft.setOnMouseExited(effect); iv_topleft.setUserData(0)
  iv_topcenter.setOnMouseClicked(mouseEventHandler); iv_topcenter.setOnMouseEntered(effect); iv_topcenter.setOnMouseExited(effect) ; iv_topcenter.setUserData(1)
  iv_topright.setOnMouseClicked(mouseEventHandler); iv_topright.setOnMouseEntered(effect); iv_topright.setOnMouseExited(effect); iv_topright.setUserData(2)
  iv_middleleft.setOnMouseClicked(mouseEventHandler); iv_middleleft.setOnMouseEntered(effect); iv_middleleft.setOnMouseExited(effect); iv_middleleft.setUserData(3)
  iv_middlecenter.setOnMouseClicked(mouseEventHandler); iv_middlecenter.setOnMouseEntered(effect); iv_middlecenter.setOnMouseExited(effect); iv_middlecenter.setUserData(4)
  iv_middleright.setOnMouseClicked(mouseEventHandler) ; iv_middleright.setOnMouseEntered(effect); iv_middleright.setOnMouseExited(effect); iv_middleright.setUserData(5)
  iv_bottomleft.setOnMouseClicked(mouseEventHandler) ; iv_bottomleft.setOnMouseEntered(effect); iv_bottomleft.setOnMouseExited(effect); iv_bottomleft.setUserData(6)
  iv_bottomcenter.setOnMouseClicked(mouseEventHandler) ; iv_bottomcenter.setOnMouseEntered(effect); iv_bottomcenter.setOnMouseExited(effect); iv_bottomcenter.setUserData(7)
  iv_bottomright.setOnMouseClicked(mouseEventHandler) ;  iv_bottomright.setOnMouseClicked(mouseEventHandler) ; iv_bottomright.setOnMouseExited(effect); iv_bottomright.setUserData(8)

  //initialize Switch Buttons
  switch_button.setToggleGroup(group)
  switch_button.setSelected(false)

  //disable the grid pane at beginning till any button is clicked
  grid_pane.setDisable(true)

  //initialize button for new Game
  btn_newGame.setOnMouseClicked(mouseEventHandler)
  //btn_newGame_sp.setMouseClicked(mouseEventHandler)

  //initialize the input and small images for the two players
  log_msg.setText("Enter your names and click on New Game!")
  image_view_p1.setImage(new Image("fhj/swengb/assignments/ttt/symbolX.png")); image_view_p1.setFitHeight(40); image_view_p1.setFitWidth(40)
  image_view_p2.setImage(new Image("fhj/swengb/assignments/ttt/symbolO.png")); image_view_p2.setFitHeight(40); image_view_p2.setFitWidth(40)

}



}
