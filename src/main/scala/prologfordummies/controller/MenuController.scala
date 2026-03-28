package prologfordummies.controller

import prologfordummies.view.UIComponents.showCustomConfirm
import prologfordummies.view.{EditUserPage, LevelsPage, LoginPage, StatsPage}

/** Gestisce la pagina principale dell'applicazione
 * (effettuato il login)
 */
object MenuController {

  def goToLevels(): Unit = {
    prologfordummies.Main.setPage(LevelsPage.asParent)
  }

  def goToStats(): Unit = {
    prologfordummies.Main.setPage(StatsPage.asParent)
  }

  def goToEditUser(): Unit = {
    prologfordummies.Main.setPage(EditUserPage.asParent)
  }

  def logout(): Unit = {
    prologfordummies.Main.setPage(LoginPage.asParent)
  }
  
  def confirmLogout(): Unit = {
    showCustomConfirm(
      head = "Conferma uscita",
      message = "Sei sicuro di voler uscire e tornare al login?",
      confirmButtonMsg = "Esci",
      declinedButtonMsg = "Annulla",
      onConfirm = () => logout()
    )
  }

}