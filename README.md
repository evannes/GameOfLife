# Game of Life

## Beskrivelse

I dette prosjektet har vi laget en versjon av **Conway's Game of Life**. Vi har laget en implementasjon av dette, samt utvidelser utover hovedoppgaven. Våre utvidelser har bestått av en Android-app, statistikk, skriving til GIF ved bruk av tilfeldighetsvariabel, alternative regler for spillet, og en 3D-versjon. Det er mulighet til å flytte på brettet ved at brukeren kan bruke knappene 'w', 'a', 's' og 'd' på tastaturet til å bevege seg rundt. Det er også implementert mulighet til å laste inn en fil som har et Game of Life-mønster, som da kan vises i spillet, og brukt thread pool i utføringen av programmet. Vår implementasjon lar brukeren tegne en og en celle på brettet eller fjerne eksisterende celler ved å trykke på brettet.

## ====== Praktisk informasjon om prosjektet ======

* Filen 'halfmax.rle' må ligge i samme mappe som .jar-filen for at standardbrettet skal dukke opp når man trykker på 'Default pattern'.
Disse filene ligger allerede i egen mappe kalt 'jar'. Når man genererer .gif-filer dukker disse opp i samme mappe som .jar-filen.

* Prosjektet bruker det eksterne bibliotektet lieng.GIFWriter. Bibliotektet 'GIFLib.jar' ligger i mappen 'GOL/src/lib'.

* Main-metoden ligger i filen 'Main.java' i mappen 'GOL/src/model'.

* Android-oppgaven ligger som en egen mappe kalt 'Android'
 
* Eksempler på genererte .gif-filer ligger i mappen 'GIF_eksempler'.

* Javadoc ligger i mappen 'GOL/src/doc'.
