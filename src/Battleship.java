import controller.Game;
import view.GUIView;
import view.TextView;
import view.View;

public class Battleship {

    public static void main(String[] args) {
        if (args.length != 1 || (!args[0].equals("text") && !args[0].equals("gui"))) {
            System.out.println("Please run with argument \"text\" or \"gui\" to select view mode");
            return;
        }

        Game game = new Game();

        View view = args[0].equals("text") ? new TextView() : new GUIView();
        view.setController(game);

        game.setView(view);
        game.start();
    }
}
