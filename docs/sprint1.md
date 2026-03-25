---
layout: default
title: Sprint 1
nav_order: 10
---

# Sprint Planning 1
**Periodo:** 01/02/2026 – 11/02/2026  

**Backlog:**  

| ID | Priorità | Task | ISE |
|----|----------|------|----------------------|
| 1  | Alta    | Modellazione utente | 3 |
| 2  | Alta    | Autenticazione utente | 2 |
| 3  | Alta    | Registrazione utente | 1 |
| 4  | Alta    | Eliminazione utente | 1 |
| 5  | Alta    | Modifica utente | 1 |
| 6  | Alta    | Interfaccia grafica utente (Registrazione) | 4 |
| 7  | Alta    | Interfaccia grafica utente (Login) | 4 |
| 8  | Alta    | Interfaccia grafica utente (Modifica ed eliminazione) | 4 |
| 9  | Alta    | Sistema persistenza utenti | 4 |
| 10 | Alta    | Modellazione Quiz a risposta multipla | 3 |
| 11 | Alta    | Modellazione Quiz "Fill-in-the-blank" | 3 |
| 12 | Alta    | Interfaccia grafica interazione Utente-Quiz | 6 |
| 13 | Media   | Valutazione risposte | 8 |
| 14 | Media   | Sistema persistenza Quiz | 4 |
| 15 | Media   | Modellazione Level | 3 |
| 16 | Media   | Sistema persistenza Level | 4 |
| 17 | Media   | Interfaccia grafica percorso didattico | 4 |
| 18 | Media   | Sistema persistenza progressi Utente | 6 |
| 19 | Media   | Testing logica applicativa | 8 |
| 20 | Media   | Dashboard progressi utente | 5 |
| 21 | Media   | Contenuti teorici livelli | 5 |
| 22 | Media   | Contenuti Quiz Domande e Risposte | 5 |
| 23 | Bassa   | Rifinitura UX | 5 |
| 24 | Bassa   | Modalità Survival | 15 |
| 25 | Bassa   | Playground Prolog | 20 |

---

## Sprint Goal
Configurazione iniziale del progetto, creazione dei Mockup, definizione della documentazione di avvio, pianificazione strategica (Backlog) e implementazione del nucleo funzionale dell’utente (Modello, CRUD e interfaccia di Login).

---

## Deadline
La scadenza dello sprint è il **11/02/2026**.

---

## Task completati nello Sprint

### Project Management
**Backlog & Planning** — Analisi dei requisiti e ultimazione della tabella di backlog con priorità e stime.

### Documentazione
**Kickoff & Index** — Stesura del documento di kickoff e creazione dell’indice strutturato per la documentazione tecnica.

### Architettura
**Struttura MVC** — Definizione dei package e configurazione dell’architettura Model-View-Controller.

### Brand Identity
**Visual Design** — Creazione del logo ufficiale “Prolog For Dummies” e integrazione degli asset grafici.

### Sviluppo Core
**User Model & CRUD** — Modellazione dell’entità User e implementazione della logica di gestione dati (Create, Read, Update, Delete).

### GUI
**ScalaFX & Login** — Configurazione del framework ScalaFX e sviluppo della prima versione della LoginPage.

### Quality Assurance
**Unit Testing** — Creazione della suite di test per validare il modello e le operazioni CRUD dell’utente.

---

## Sprint Review

### Pianificazione
La redazione del backlog fornisce una visione chiara dei task prioritari e una stima realistica per i futuri cicli di sviluppo.

### Architettura
Imbastita la struttura MVC per il progetto e a specchio anche nella sezione Test.

### User Logic
Il modello utente è stabile, testato tramite unit test e pronto per l’integrazione con sistemi di persistenza.

### Interfaccia
La LoginPage è funzionale e coerente con l’identità visiva.

---

## Sprint Retrospective

### Cosa è andato bene
- Organizzazione — Il backlog definito subito ha ridotto l’incertezza decisionale.
- Approccio TDD — I test implementati insieme al modello User hanno aumentato l’affidabilità del core.
- Documentazione — Kickoff e indice creati all’inizio hanno migliorato tracciabilità e chiarezza.

### Difficoltà incontrate
- Setup ScalaFX — Tempo necessario per configurare correttamente le dipendenze in Scala 3.
- Prioritarizzazione — La stima iniziale dei task ha richiesto confronti per allineare la percezione della complessità.

---

## Miglioramenti previsti per Sprint 2
- Estensione GUI — Implementare della pagian di Registraizione mantenendo coerenza stilistica con la Login.
- Persistenza — Evolvere il CRUD dell'utente per avere persistenza dei dati
- Navigazione — Implementare gestione dinamica del cambio scena (scene switching).
