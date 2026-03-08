package prologfordummies.controller

import prologfordummies.view.{EditUserPage, LevelsPage, LoginPage}

object MenuController {

  def goToLevels(): Unit = {
    prologfordummies.Main.setPage(LevelsPage.asParent)
  }

  def goToStats(): Unit = {
    println("Statistiche")
  }

  def goToEditUser(): Unit = {
    prologfordummies.Main.setPage(EditUserPage.asParent)
  }

  def logout(): Unit = {
    prologfordummies.Main.setPage(LoginPage.asParent)
  }

}