package hr.fer.zemris.nenr.neuro;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    public class Layer {
        private List<Neuron> neurons;
        private int weightCount;

        public Layer(int neuronCount, int weightCount) {
            this.neurons = new ArrayList<>(neuronCount);
            for (int i = 0; i < neuronCount; i++) {
                this.neurons.add(new Neuron(weightCount));
            }
            this.weightCount = weightCount;
        }

        public double[] calc(double[] input) {
            double[] result = new double[neurons.size()];
            for (int i = 0; i < neurons.size(); i++) {
                Neuron neuron = neurons.get(i);
                result[i] = neuron.calc(input);
            }
            return result;
        }
    }

    private List<Layer> layers;
    private int initialLayerCount;

    public NeuralNetwork(int... neuronCounts) {
        if (neuronCounts.length < 2) throw new IllegalArgumentException("You must have at least 2 layers");
        this.layers = new ArrayList<>(neuronCounts.length - 1);
        this.initialLayerCount = neuronCounts[0];
        for (int i = 1; i < neuronCounts.length; i++) {
            this.layers.add(new Layer(neuronCounts[i], neuronCounts[i - 1]));
        }
    }

    public double[] calc(double[] input) {
        if (input.length != initialLayerCount) throw new IllegalArgumentException("Input size does not fit with first layer size");
        double[] layerInput = input;
        for (Layer layer : layers) {
            layerInput = layer.calc(layerInput);
        }
        return layerInput;
    }
}
