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



  def animationOut(Pane: AnchorPane): Unit = {

    val path: Path = new Path()

    path.getElements.add(new MoveTo(300,400))
    path.getElements().add(new CubicCurveTo(350, 400, 500, 400,1000,400))

    val pathTrans: PathTransition = new PathTransition ()
    pathTrans.setDuration (new Duration(350) )
    pathTrans.setNode(Pane)
    pathTrans.setPath(path)
    pathTrans.setAutoReverse(false)
    pathTrans.play()
  }

  def animationIn(Pane: AnchorPane): Unit = {

    val path: Path = new Path()

    path.getElements.add(new MoveTo(1000,400))
    path.getElements().add(new CubicCurveTo(500, 400, 350, 400,300,400))

    val pathTrans: PathTransition = new PathTransition ()
    pathTrans.setDuration (new Duration(350) )
    pathTrans.setNode(Pane)
    pathTrans.setPath(path)
    pathTrans.setAutoReverse(false)
    pathTrans.play()
  }





  def spPane(): Unit = {
    animationOut(start_pane)
    animationIn(sp_pane)



  }


  def spback(): Unit = {

    animationOut(start_pane)
  }













}
