package hr.fer.zemris.nenr.neuro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class NeuralNetwork {

    public class Layer {
        private List<TrainableNeuron> neurons;
        private double[] output;

        public Layer(int neuronCount, int weightCount) {
            this.neurons = new ArrayList<>(neuronCount);
            for (int i = 0; i < neuronCount; i++) {
                this.neurons.add(new TrainableNeuron(weightCount));
            }
        }

        public double[] calc(double[] input) {
            double[] result = new double[neurons.size()];
            for (int i = 0; i < neurons.size(); i++) {
                TrainableNeuron neuron = neurons.get(i);
                result[i] = neuron.calc(input);
            }
            this.output = result;
            return this.output;
        }

        public void setDeltas(double[] deltas) {
            for (int i = 0; i < neurons.size(); i++) {
                neurons.get(i).setDelta(deltas[i]);
            }
        }

        public void updateDelta(Layer next) {
            double[] deltas = new double[neurons.size()];
            for (int j = 0; j < neurons.size(); j++) {
                double error = 0;
                for (TrainableNeuron neuron : next.neurons) {
                    error += neuron.getW()[j] * neuron.getDelta();
                }
                deltas[j] = error * f.calcDerivation(neurons.get(j).getOutput());
            }
            setDeltas(deltas);
        }

        public void updateWeights() {
            for (TrainableNeuron neuron : neurons) {
                neuron.updateWeight(learningRate);
            }
        }

        public void updateDeltaWs(double[] input) {
            for (TrainableNeuron neuron : neurons) {
                neuron.updateDeltaW(input);
            }
        }
    }

    private List<Layer> layers;
    private int initialLayerCount;
    private double learningRate;
    private ActivationFunction f = new SigmoidActivationFunction();

    public NeuralNetwork(double learningRate, int... neuronCounts) {
        if (neuronCounts.length < 2) throw new IllegalArgumentException("You must have at least 2 layers");
        this.learningRate = learningRate;
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

    public void train(double minError, int maxIterations, List<List<Example>> trainSet) {
        List<Example> examples = trainSet.stream().flatMap(Collection::stream) .collect(Collectors.toList());
        double mse = mse(examples);
        int k = 0;
        while (mse > minError && k < maxIterations) {
            System.out.println("Iter " + (k + 1) + " error " + mse);

            for (Example example : trainSet.get(k % trainSet.size())) {
                double[] output = calc(example.input);

                // last layer
                Layer lastLayer = layers.get(layers.size() - 1);
                double[] deltas = new double[output.length];
                for (int i = 0; i < deltas.length; i++) {
                    deltas[i] = (example.output[i] - output[i]) * f.calcDerivation(output[i]);
                }
                lastLayer.setDeltas(deltas);

                // propagate error back
                for (int i = layers.size() - 2; i >= 0; i--) {
                    Layer current = layers.get(i);
                    Layer next = layers.get(i + 1);
                    current.updateDelta(next);
                }

                // update weights
                for (int i = 0; i < layers.size(); i++) {
                    Layer layer = layers.get(i);
                    double[] input = i == 0 ? example.input : layers.get(i - 1).output;
                    layer.updateDeltaWs(input);
                }
            }

            for (Layer layer : layers) {
                layer.updateWeights();
            }

            mse = mse(examples);
            k++;
        }
    }

    private double[] getExampleError(double[] expected, double[] computed) {
        double[] error = new double[expected.length];
        for (int i = 0; i < expected.length; i++) {
            error[i] = (expected[i] - computed[i]) * f.calcDerivation(computed[i]);
        }
        return error;
    }

    public double mse(List<Example> examples) {
        double mse = 0;

        for (Example example : examples) {
            double[] calculated = this.calc(example.input);

            for (int i = 0; i < example.output.length; i++) {
                mse += Math.pow(example.output[i] - calculated[i], 2);
            }
        }
        return mse / (2 * examples.size());
    }
}
