---
layout: default
title: Requisiti
nav_order: 2
---

# Requisiti

Di seguito viene definita la **vision del progetto** e l’insieme dei **requisiti** che l’applicazione dovrà soddisfare.

---

## Business

Si vuole sviluppare un’applicazione desktop a supporto dell’apprendimento del linguaggio **Prolog**, rivolta a utenti che si avvicinano per la prima volta alla programmazione logica.

L’obiettivo principale è fornire un **percorso didattico guidato e progressivo**, strutturato per livelli che consenta all’utente di acquisire le nozioni fondamentali del linguaggio.

Le principali finalità includono:

- **Apprendimento guidato**: facilitare la comprensione dei concetti chiave di Prolog (fatti, regole, query, ricorsione, backtracking).
- **Verifica delle conoscenze**: consentire il consolidamento dell’apprendimento tramite quiz interattivi con valutazione automatica.
- **Motivazione e progressione**: incentivare la continuità nello studio attraverso il completamento dei livelli e il tracciamento dei progressi.
- **Accessibilità didattica**: offrire uno strumento semplice e intuitivo, utilizzabile anche da utenti senza esperienza pregressa di programmazione logica.

---

## Modello di dominio

Gli elementi principali del sistema sono:

- **User**: rappresenta l’utilizzatore dell’applicazione, dove sarà possibile gestirne l'identità e lo storico dei progressi di apprendimento.
- **Level**: rappresenta un livello del percorso didattico, dedicato a specifici concetti del linguaggio Prolog.
- **Theory**: contenuto testuale che introduce la teoria per svolgere il livello.
- **Question**: singola domanda di un quiz, a risposta multipla o di completamento.
- **Answer**: risposta fornita dall’utente a una domanda.
- **Progress**: stato di avanzamento dell’utente, comprensivo di livelli completati, punteggio e percentuale di completamento.


---

## Requisiti funzionali

### Utente

- L’utente deve poter accedere all’applicazione tramite **autenticazione**.
- L’utente deve poter registrarsi
- L’utente deve poter editare il proprio Nome
- L’utente deve poter eliminare la propria utenza
- L’utente deve poter visualizzare un **percorso didattico strutturato per livelli**.
- L’utente deve poter svolgere esercizi di verifica sotto forma di:
  - quiz a risposta multipla;
  - quiz di completamento (*fill-in-the-blank*).
- L’utente deve ricevere un **feedback immediato** sull’esito delle risposte.
- L’utente deve poter visualizzare i propri **progressi**, inclusi:
  - Conteggio livelli completati
  - Percentuale di risposte corrette
  - Conteggio quiz tentati
  - Conteggio ore di utilizzo.

---

### Sistema

- Il sistema deve gestire l’autenticazione e la memorizzazione dei profili utente.
- Il sistema deve memorizzare e presentare all’utente i livelli ed i quiz disponibili.
- Il sistema deve valutare automaticamente le risposte fornite nei quiz.
- Il sistema deve aggiornare e memorizzare lo stato di avanzamento dell’utente.
- Il sistema deve fornire una **interfaccia grafica** per la fruizione dei contenuti e dei quiz.

---

## Requisiti non funzionali

- **Usabilità**: l’interfaccia grafica deve essere intuitiva e facilmente utilizzabile
- **Estendibilità**: il sistema deve gestire l’aggiunta di nuovi livelli e quiz 
- **Testabilità**: devono essere presenti test per verificare il corretto funzionamento delle funzionalità principali
- **Portabilità**: l’applicazione deve poter essere eseguita su diversi sistemi operativi desktop
- **Performance**: il sistema deve garantire tempi di risposta rapidi durante la navigazione e la valutazione dei quiz
- **Manutenibilità**: il codice deve essere strutturato in modo modulare e facilmente manutenibile

---

## Implementazione

- **Linguaggio**: Scala 3.8.1
- **GUI**: Scala FX 23.0.1-R34
- **Testing**: ScalaTest 3.2.19  
- **Build Tool**: SBT 1.12.1
- **Java**: 21.0.10
- **uPickle**: 3.1.0

---

## Requisiti opzionali

- **Playground Prolog** per la scrittura ed esecuzione di query.
- **Modalità di gioco “sopravvivenza”**, in cui l’utente risponde a una sequenza casuale di domande che termina al primo errore.

---

<div style="display: flex; justify-content: space-between; margin-top: 50px;">
  <a href="index.html">← Indietro: Introduzione</a>
  <a href="sprint1.html">Prossimo: Sprint 1 →</a>
</div>
