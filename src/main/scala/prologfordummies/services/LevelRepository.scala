package prologfordummies.services

import prologfordummies.model.Level
import java.util.UUID

/** 
 * Interfaccia di Level Repository per la gestione dei dati dei livelli 
 * con metodi read-only
 */
trait LevelRepository:
  def loadAll(): List[Level]
  def findById(id: Level.Id): Option[Level]

