====== Informasjon om prosjektet ======

1) Filen 'halfmax.rle' må ligge i samme mappe som .jar-filen for at standardbrettet skal dukke opp når man trykker på 'Default pattern'.
Disse filene ligger allerede i egen mappe kalt 'jar'. Når man genererer .gif-filer dukker disse opp i samme mappe som .jar-filen.

2) Prosjektet bruker det eksterne bibliotektet lieng.GIFWriter. Bibliotektet 'GIFLib.jar' ligger i mappen 'GOL/src/lib'.

3) Main-metoden ligger i filen 'Main.java' i mappen 'GOL/src/model'.

4) Android-oppgaven ligger som en egen mappe kalt 'Android'

5) Eksempler på genererte .gif-filer ligger i mappen 'GIF_eksempler'.

6) Javadoc ligger i mappen 'GOL/src/doc'.


====== Utførte oppgaver ======

Oppgavesett 1 til og med 7 er utført. Kjernen til programmet er i oppgavene 1 til og med 5 og de to siste
oppgavesettene implementerer dynamisk brett og samtidig (threaded) utførelse av nextGeneration-metoden.

Utvidelsesoppgaver som er utført er: Android-app, 3D-implementasjon av spillet (oppg.sett 2),
alternative regler for spillet (oppg.sett 4) og statistikkoppgaven inkludert skriving til GIF ved bruk av
tilfeldighetsvariabel.

Mulighet til å flytte på brettet (oppg.sett 5) er delvis utført ved at
brukeren kan bruke knappene 'w', 'a', 's' og 'd' på tastaturet til å bevege seg rundt.

Ekstraoppgaven i oppg.sett 7 (bruk av thread pool) er implementert.

Fra utvidelsen 'manipulering og GIF' har vi implementert deloppgaven 'Manipulering via point-and-click interface'.
Vår implementasjon lar brukeren tegne en og en celle på brettet eller fjerne eksisterende celler ved å trykke på brettet.