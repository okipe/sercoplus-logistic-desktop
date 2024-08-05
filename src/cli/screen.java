package cli;

public class screen {
    public static void clearscreen() {
        try {
//            System.out.print("\033[H\033[2J");
//            System.out.flush();
            String os = System.getProperty("os.name").toLowerCase();
            ProcessBuilder pb;
            if (os.contains("win")) {
                pb = new ProcessBuilder("cmd", "/c", "cls");
            } else {
                pb = new ProcessBuilder("clear");
            }
            Process startProcess = pb.inheritIO().start();
            startProcess.waitFor();
        } catch (Exception e) {
        // Manejar la excepción
        for (int i = 0; i < 100; i++) {
            System.out.println();
            }
        }
    }
}
