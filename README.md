# Gene-Mining

### Running from IntelliJ or Eclipse using SBT 
1. Clone Repository or Download Source Files
2. From command line, navigate to source file and run `sbt`
3. Build js file in dev mode with `fastOptJS`, full production with `fullOptJS`
4. Navigate to index.html or `localhost:63342/Gene-Mining/src/main/Web/`

### Running from Simple Server in Command Line
1. Clone Repository or download source files
2. `cd src\main\web`
3. start simple server (I use `python -m SimpleHTTPServer` or `py3 -m http.server`)
4. Navigate to `locahost:8000` or specified port and you should be able to start searching for genes from there!