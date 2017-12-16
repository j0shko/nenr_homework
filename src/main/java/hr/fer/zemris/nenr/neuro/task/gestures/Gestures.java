package hr.fer.zemris.nenr.neuro.task.gestures;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Gestures extends JFrame {

    private static final int EXAMPLE_COUNT = 5;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Gestures().setVisible(true));
    }

    private Map<String, List<Gesture>> gestures;
    private List<String> gestureNames = List.of("alpha", "beta", "gamma", "delta", "epsilon");
    private int gestureIndex;
    private int counter;

    private Canvas canvas;
    private JLabel label;
    private Button add;
    private JPanel footer;


    private Gestures() {
        setSize(500,500);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        canvas = new Canvas();
        setLayout(new BorderLayout());
        getContentPane().add(canvas, BorderLayout.CENTER);

        label = new JLabel();
        label.setText(gestureNames.get(gestureIndex) + " " + Integer.toString(counter + 1));

        add = new Button("Add");
        add.addActionListener(e -> {
            gestures.get(gestureNames.get(gestureIndex)).add(new Gesture(canvas.getPoints()));
            canvas.reset();
            if (counter == EXAMPLE_COUNT - 1) {
                counter = 0;
                gestureIndex++;
                if (gestureIndex == gestureNames.size()) {
                    phaseTwo();
                    return;
                }
            } else {
                counter++;
            }
            label.setText(gestureNames.get(gestureIndex) + " " + Integer.toString(counter + 1));
        });

        Button reset = new Button("Reset");
        reset.addActionListener(e -> canvas.reset());

        footer = new JPanel();
        footer.setLayout(new FlowLayout());
        getContentPane().add(footer, BorderLayout.SOUTH);
        footer.add(label);
        footer.add(reset);
        footer.add(add);

        gestures = new HashMap<>();
        gestureNames.forEach(s -> gestures.put(s, new ArrayList<>()));
    }

    private void phaseTwo() {
        footer.remove(add);

        Button check = new Button("Check");
        check.addActionListener(e -> label.setText(gestureNames.get(new Random().nextInt(gestureNames.size()))));
        footer.add(check);

        label.setText("");
    }
}
