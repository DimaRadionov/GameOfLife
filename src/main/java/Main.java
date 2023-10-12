public class Main {
    public static void main(String[] args) {
        int rows = 10; // Anzahl der Zeilen im Gitter
        int columns = 10; // Anzahl der Spalten im Raster
        int generations = 10; // Zahl der zu simulierenden Generationen

        // Erstellen Sie das Ausgangsgitter mit zufälligen lebenden Zellen
        boolean[][] grid = new boolean[rows][columns];
        initializeGrid(grid);

        // Simulieren Sie die Generationen
        for (int generation = 1; generation <= generations; generation++) {
            System.out.println("Generation " + generation);
            printGrid(grid);
            grid = nextGeneration(grid);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Initialisierung des Gitters mit zufälligen lebenden Zellen
    public static void initializeGrid(boolean[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = Math.random() < 0.5; // 50% Chance, am Leben zu sein
            }
        }
    }

    // Druckt den aktuellen Zustand des Gitters
    public static void printGrid(boolean[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(grid[i][j] ? "O " : "* "); // O wie lebendig, * wie tot
            }
            System.out.println();
        }
        System.out.println();
    }

    // Berechnen Sie die nächste Generation des Gitters auf der Grundlage der Regeln
    public static boolean[][] nextGeneration(boolean[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] newGrid = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int liveNeighbors = countLiveNeighbors(grid, i, j);
                // Jede lebende Zelle mit mehr als drei lebenden Nachbarn stirbt wie bei einer Überbevölkerung
                if (grid[i][j]) {
                    // Jede lebende Zelle mit weniger als zwei lebenden Nachbarn stirbt wie an Unverträglichkeit
                    // Jede lebende Zelle mit zwei oder drei lebenden Nachbarn lebt in der nächsten Generation weiter
                    newGrid[i][j] = liveNeighbors == 2 || liveNeighbors == 3;
                } else {
                    //  Jede tote Zelle mit genau drei lebenden Nachbarn wird zu einer lebenden Zelle, als ob sie geboren worden wäre
                    newGrid[i][j] = liveNeighbors == 3;
                }
            }
        }
        return newGrid;
    }

    // Zählen Sie die Anzahl der lebenden Nachbarn für eine Zelle
    // Es geht die acht Nachbarn jeder Zelle in der Umgebung durch und zählt, wie viele von ihnen lebendig sind.

    // boolean[][] grid: Dies ist ein zweidimensionales Array von booleschen Werten, die das Spielgitter darstellen
    // int x und int y: Dies sind die Koordinaten der Zelle, für die wir die lebenden Nachbarn zählen wollen.
    public static int countLiveNeighbors(boolean[][] grid, int x, int y) {
        int rows = grid.length;     // Diese Variablen speichern die Anzahl der Zeilen und Spalten im Spielgitter.
        int cols = grid[0].length;
        int count = 0;              // Diese Variable speichert die Anzahl der lebenden Nachbarn für eine bestimmte Zelle.

        // Dies ist ein zweidimensionales Array, das die relativen Koordinaten der acht Nachbarn für jede Zelle definiert.
        // Jedes Element des Arrays stellt ein Paar von relativen Koordinaten {dx, dy} für jeden der acht Nachbarn dar.
        int[][] neighbors = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},           {0, 1},
                {1, -1},  {1, 0},  {1, 1}
        };
        // In einer Schleife werden alle acht Nachbarn für eine bestimmte Zelle mit Hilfe des Arrays neighbours durchlaufen.
        // Für jeden Nachbarn definieren wir seine relativen Koordinaten dx und dy und berechnen dann die absoluten Koordinaten newX und newY für diesen Nachbarn relativ zu der gegebenen Zelle.
        for (int[] neighbor : neighbors) {
            int dx = neighbor[0];
            int dy = neighbor[1];
            int newX = x + dx;
            int newY = y + dy;
                // Wir prüfen, ob der Nachbar innerhalb der Gittergrenzen liegt und ob er noch lebt oder nicht
            if ((newX >= 0 && newX < rows && newY >= 0 && newY < cols) && grid[newX][newY]) {
                count++;
            }
        }
        return count;
    }
}