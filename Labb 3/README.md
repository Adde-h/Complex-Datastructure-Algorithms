## Flöden och matchningar

Målen för labb 3 är att du ska

- öva att programmera efter en detaljerad algoritmisk specifikation,
- programmera en algoritm som använder en hjälpalgoritm som en svart låda (dvs en reduktion),
- implementera en effektiv algoritm för flödesproblemet,
- implementera en effektiv algoritm för bipartit matchning.

Du ska i tre steg skriva ett program som får en bipartit graf som indata och producerar en matchning av maximal storlek som utdata genom att reducera (transformera) matchningsproblemet till flödesproblemet.

### Steg 1: Reducera problemet till flödesproblemet

Du ska skriva ett program som löser matchningsproblemet med hjälp av en svart låda som löser flödesproblemet. Programmet ska fungera enligt denna översiktliga programstruktur:

Läs indata för matchningsproblemet från standard input.
Översätt matchningsinstansen till en flödesinstans.
Skriv indata för flödesproblemet till standard output (se till att utdata flushas).
Den svarta lådan löser flödesproblemet.
Läs utdata för flödesproblemet från standard input.
Översätt lösningen på flödesproblemet till en lösning på matchningsproblemet.
Skriv utdata för matchningsproblemet till standard output.
Se nedan hur in- och utdataformaten för matchnings- och flödesproblemen ser ut.

Ditt program ska lösa problemet effektivt.

### Steg 2: Lös flödesproblemet

Nu ska du skriva ett program som löser flödesproblemet. Programmet ska läsa indata från standard input och skriva lösningen till standard output. Se nedan hur in- och utdataformaten för flödesproblemet ser ut.

Ditt program ska lösa problemet effektivt.
### Steg 3: Kombinera steg 1 & 2
I steg 1 löste du matchningsproblemet med hjälp av en lösning till flödesproblemet. I steg 2 löste du flödesproblemet. Nu ska du kombinera dessa lösningar till ett enda program genom att byta ut kommunikationen av flödesinstansen över standard input och standard output till ett funktionsanrop. Programmet ska fortfarande läsa indata från standard input och skriva lösningen till standard output.

Ditt program ska lösa problemet effektivt.

### Matchningsproblemet
Givet en bipartit graf G = (X,Y,E) finn en maximal matchning.

#### Indata
Den första raden består av två heltal som anger antalet hörn i X respektive Y.
Den andra raden består av ett tal som anger |E|, det vill säga antalet kanter i grafen.
De följande |E| raderna består var och en av två heltal som svarar mot en kant.

Hörnen numreras från 1 och uppåt. Om man angett a hörn i X och b hörn i Y så låter vi X = {1, 2,..., a} och Y = {a+1, a+2,..., a+b}. En kant anges med ändpunkterna (först X-hörnet och sedan Y-hörnet).

Exempel: En graf kan till exempel kodas så här.

```bash
2 3
4
1 3
1 4
2 3
2 5
```
Denna graf har alltså X = {1, 2} och Y = {3, 4, 5}. Kantmängden E innehåller kanterna (1, 3), (1, 4), (2, 3) och (2, 5).

#### Utdata
Först skrivs en rad som är densamma som den första i indata, och därefter en rad med ett heltal som anger antalet kanter i den funna matchningen. Därefter skrivs en rad för varje kant som ingår i matchningen. Kanten beskrivs av ett talpar på samma sätt som i indata.

Exempel: Om vi har grafen ovan som indata så kan utdata se ut så här.

```bash
2 3
2
1 3
2 5
```

### Flödesproblemet
Givet en flödesgraf G = (V,E) finn ett maximalt flöde. Lös flödesproblemet med Edmonds-Karps algoritm, det vill säga Ford-Fulkersons algoritm där den kortaste stigen hittas med breddenförstsökning.

#### Ford-Fulkersons algoritm i pseudokod

    c[u,v] är kapaciteten från u till v, f[u,v] är flödet, cf[u,v] är restkapaciteten.

    for varje kant (u,v) i grafen do 
        f[u,v]:=0; f[v,u]:=0 
        cf[u,v]:=c[u,v]; cf[v,u]:=c[v,u] 
    
    while det finns en stig p från s till t i restflödesgrafen do 
        r:=min(cf[u,v]: (u,v) ingår i p) 
        for varje kant (u,v) i p do 
            f[u,v]:=f[u,v]+r; f[v,u]:= -f[u,v] 
            cf[u,v]:=c[u,v] - f[u,v]; cf[v,u]:=c[v,u] - f[v,u]

#### Indata
Den första raden består av ett heltal som anger antalet hörn i V.
Den andra raden består av två heltal s och t som anger vilka hörn som är källa respektive utlopp.
Den tredje raden består av ett tal som anger |E|, det vill säga antalet kanter i grafen.
De följande |E| raderna består var och en av tre heltal som svarar mot en kant.

Hörnen numreras från 1 och uppåt. Om man angett a hörn i V så låter vi V = {1, 2,..., a}. En kant anges med ändpunkterna (först från-hörnet och sedan till-hörnet) följt av dess kapacitet.

Exempel: En graf kan till exempel kodas så här.

```bash
4
1 4
5
1 2 1
1 3 2
2 4 2
3 2 2
3 4 1
```

#### Utdata
Den första raden består av ett heltal som anger antalet hörn i V.
Den andra raden består av tre heltal s,t, samt flödet från s till t.
Den tredje raden består av ett heltal som anger antalet kanter med positivt flöde.
Därefter skrivs en rad för varje sådan kant. Kanten beskrivs av tre tal på liknande sätt som i indata, men i stället för kapacitet har vi nu flöde.

Exempel: Om vi har grafen ovan som indata så kan utdata se ut så här.
```bash
4
1 4 3
5
1 2 1
1 3 2
2 4 2
3 2 1
3 4 1
```