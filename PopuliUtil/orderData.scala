package myScalaLibs.PopuliUtil

import java.time.{LocalDateTime, ZoneOffset}


class orderData (iMagic:Int, iVolume:Double, iOpen:Double=0) {

  // Automa:
  // (RequestOpen on create)    magic Pending OpenOrder -> trader to marketOperator
  // (OpenExecuted)             magic Opened NoAction -> marketOperator to trader
  // (RequestClose)             magic Opened CloseOrder -> trader to marketOperator
  // (CloseExecuted)            magic Closed NoAction -> marketOperator to trader

  object StateOrder extends Enumeration {
    type StateOrder = Value
    val Pending, Opened, Closed = Value
  }

  object Action extends Enumeration {
    type Action = Value
    val NoAction, OpenOrder, CloseOrder = Value
  }

  import StateOrder._
  import Action._

  private var magic:Int = iMagic
  private var state:StateOrder = Pending
  private var action:Action = OpenOrder
  private var open:Double = iOpen
  private var openData:LocalDateTime = LocalDateTime.now(ZoneOffset.UTC)
  private var close:Double = 0
  private var closeData:LocalDateTime = LocalDateTime.now(ZoneOffset.UTC)
  private var volume:Double = iVolume
  private def setOpen(input:Double){open=input; setOpenData()}
  private def setClose(input:Double){close=input; setCloseData()}
  private def setState(input:StateOrder){state=input}
  private def setAction(input:Action){action=input}
  private def setOpenData(){openData = LocalDateTime.now(ZoneOffset.UTC)}
  private def setCloseData(){closeData = LocalDateTime.now(ZoneOffset.UTC)}
  def getOpen:Double=open
  def getClose:Double=close
  def getState:StateOrder=state
  def getAction:Action=action
  def goToOpenExecuted(iOpen:Double): Unit ={

    setOpen(iOpen)
    setState(Opened)
    setAction(NoAction)
    loggerManager.writeLine("goToOpenExecuted")
  }
  def goToRequestClose(iClose:Double=0): Unit ={
    setClose(iClose)
    setAction(CloseOrder)
    loggerManager.writeLine("goToRequestClose")
  }
  def goToCloseExecuted(iClose:Double): Unit ={
    setClose(iClose)
    setState(Closed)
    setAction(NoAction)
    loggerManager.writeLine("goToCloseExecuted")
  }

  loggerManager.writeLine("orderData created")

}
