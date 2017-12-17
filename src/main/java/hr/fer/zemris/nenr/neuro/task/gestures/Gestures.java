package hr.fer.zemris.nenr.neuro.task.gestures;

import hr.fer.zemris.nenr.neuro.Example;
import hr.fer.zemris.nenr.neuro.NeuralNetwork;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Gestures extends JFrame {

    private static final int EXAMPLE_COUNT = 3;
    private static final int GESTURE_PART_COUNT = 10;
    private static final double LEARNING_RATE = 0.1;
    private static final int MAX_ITER = 10000;
    private static final double MIN_ERROR = 0.0005;

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
            gestures.get(gestureNames.get(gestureIndex)).add(new Gesture(canvas.getPoints(), GESTURE_PART_COUNT));
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
        List<Example> examples = new ArrayList<>();
        gestures.forEach((key, value) -> value.forEach(gesture -> examples.add(new Example(gesture.getPoints(), gestureOutput(gestureNames.indexOf(key))))));
        NeuralNetwork neuralNetwork = new NeuralNetwork(LEARNING_RATE, 2 * GESTURE_PART_COUNT, 2, 6, 5);
        System.out.println("Training started");
        neuralNetwork.train(MIN_ERROR, MAX_ITER, examples);
        System.out.println("Training ended");

        footer.remove(add);

        Button check = new Button("Check");
        check.addActionListener(e -> label.setText(getPrediction(neuralNetwork, new Gesture(canvas.getPoints(), GESTURE_PART_COUNT))));
        footer.add(check);

        label.setText("");
    }

    private String getPrediction(NeuralNetwork neuralNetwork, Gesture input) {
        double[] output = neuralNetwork.calc(input.getPoints());
        double max = 0;
        int maxIndex = 0;

        for (int i = 0; i < output.length; i++) {
            if (output[i] > max) {
                max = output[i];
                maxIndex = i;
            }
        }
        return gestureNames.get(maxIndex);
    }

    private String gestureCode(int index) {
        double[] values = new double[gestureNames.size()];
        values[index] = 1;
        String s = Arrays.toString(values).replaceAll(" ", "");
        return s.substring(1, s.length() - 1);
    }

    private double[] gestureOutput(int index) {
        double[] values = new double[gestureNames.size()];
        values[index] = 1;
        return values;
    }
}
