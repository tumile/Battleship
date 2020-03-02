import controller.Game;
import controller.View;
import view.GUIView;
import view.TextView;

public class Battleship {

    public static void main(String[] args) {
        View view;

//        if (args[1].equals("Text")) {
//            view = new TextView();
//        } else if (args[1].equals("GUI")) {
//            view = new GUIView();
//        } else {
//            System.out.println("Please run with argument \"Text\" or \"GUI\" to select view mode");
//            return;
//        }

        view = new TextView();
        new Game(view);
    }
}
