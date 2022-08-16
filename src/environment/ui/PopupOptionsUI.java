package environment.ui;

import javax.swing.*;

public class PopupOptionsUI extends JOptionPane {

    public PopupOptionsUI(String title, String message, PopupOption...options){
        Thread t = new Thread(() -> {
            int result = JOptionPane.showOptionDialog(null,
                    message,
                    title,
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    null);

            options[result].onClick();
        });

        t.start();
    }

    public static abstract class PopupOption{
        private String text;

        public PopupOption(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public abstract void onClick();

        @Override
        public String toString() {
            return text;
        }
    }
}
