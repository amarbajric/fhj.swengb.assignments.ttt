package fhj.swengb.assignments.ttt.abajric

import java.awt.Button
import java.beans.Visibility
import javafx.scene.shape.{Path,MoveTo,CubicCurveTo}
import java.net.URL
import javafx.util.Duration
import java.util.ResourceBundle
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage
import javafx.animation._
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
      //stage.getScene.getStylesheets.add(Css)
      stage.show()
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }
}



class TicTacToeAppController extends TicTacToeApp {

  @FXML var start_pane: AnchorPane = _
  @FXML var sp_pane: AnchorPane = _



  def paneAnimation(Pane: AnchorPane, slide: Boolean, xMitte:Int =0, yMitte:Int = 400): Unit = {

    val end = 1200
    val path: Path = new Path()

    slide match {
      case yes if slide == true => {
        path.getElements.add(new MoveTo(xMitte, yMitte))
        path.getElements().add(new CubicCurveTo(xMitte, yMitte, xMitte, yMitte, xMitte + end, yMitte))
      }
      case no if slide == false => {
        path.getElements.add(new MoveTo(xMitte + end, yMitte))
        path.getElements().add(new CubicCurveTo(xMitte, yMitte, xMitte, yMitte, xMitte, yMitte))
      }
    }
    val pathTrans: PathTransition = new PathTransition ()
    pathTrans.setDuration (new Duration(350) )
    pathTrans.setNode(Pane)
    pathTrans.setPath(path)
    pathTrans.setAutoReverse(false)
    pathTrans.play()
  }





  def spPane(): Unit = {
    paneAnimation(start_pane,false,300)
    paneAnimation(sp_pane,true)
    start_pane.setVisible(false)


  }


  def spback(): Unit = {
    paneAnimation(start_pane,true,100)
    paneAnimation(sp_pane,false)
    //start_pane.setVisible(true)
  }













}
