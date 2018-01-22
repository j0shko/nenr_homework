package hr.fer.zemris.nenr.neurogenetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class NeuralNetwork {

    public class Layer implements Iterable<Neuron> {
        private List<Neuron> neurons;

        public Layer(List<Neuron> neurons) {
            this.neurons = neurons;
        }

        public double[] calc(double[] input) {
            double[] result = new double[neurons.size()];
            for (int i = 0; i < neurons.size(); i++) {
                result[i] = neurons.get(i).calc(input);
            }
            return result;
        }

        public int size() {
            return neurons.size();
        }

        public List<Neuron> getNeurons() {
            return neurons;
        }

        @Override
        public Iterator<Neuron> iterator() {
            return neurons.iterator();
        }
    }

    private List<Layer> layers;
    private int initialLayerCount;

    public NeuralNetwork(int... neuronCounts) {
        if (neuronCounts.length < 2) throw new IllegalArgumentException("You must have at least 2 layers");
        this.layers = new ArrayList<>(neuronCounts.length - 1);
        this.initialLayerCount = neuronCounts[0];
        for (int i = 1; i < neuronCounts.length; i++) {
            int weightCount = this.layers.isEmpty() ? initialLayerCount : this.layers.get(this.layers.size() - 1).neurons.size();
            List<Neuron> neurons = new ArrayList<>();
            for (int j = 0; j < neuronCounts[i]; j++) {
                if (i == 1) {
                    neurons.add(new SimilarityNeuron(weightCount));
                } else {
                    neurons.add(new WeightNeuron(weightCount));
                }
            }
            this.layers.add(new Layer(neurons));
        }
    }

    public int paramCount() {
        return this.layers.stream().flatMap(l -> l.neurons.stream()).mapToInt(Neuron::getParamsSize).sum();
    }

    public double[] getParams() {
        return this.layers.stream().flatMap(l -> l.neurons.stream()).flatMapToDouble(n -> Arrays.stream(n.getParams())).toArray();
    }

    public void setParams(double[] params) {
        int i = 0;
        for (Layer layer : layers) {
            for (Neuron neuron : layer) {
                int size = neuron.getParamsSize();
                double[] neuronParams = Arrays.copyOfRange(params, i, i + size);
                neuron.setParams(neuronParams);
                i += size;
            }
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

    public double[] calc(double[] params, double[] input) {
        setParams(params);
        return calc(input);
    }

    public List<Layer> getLayers() {
        return layers;
    }
}
