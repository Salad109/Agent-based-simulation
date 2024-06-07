package agentsimulation.logic;

import agentsimulation.Simulation;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XChartPanel;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Creates a real-time chart using SwingWorker
 */
public class Graph {

    private MySwingWorker mySwingWorker;
    private XYChart chart;

    public Graph(JPanel chartPanel) {
        go(chartPanel);
    }

    private void go(JPanel chartPanel) {

        // Create Chart
        chart = QuickChart.getChart(
                "Real-time combined population",
                "Time",
                "Population",
                "series1",
                new double[]{0},
                new double[]{0});
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setXAxisTicksVisible(false);

        // Create a ChartPanel and add it to the provided JPanel
        XChartPanel<XYChart> chartPanelComponent = new XChartPanel<>(chart);
        chartPanel.setLayout(new java.awt.BorderLayout());
        chartPanel.add(chartPanelComponent, java.awt.BorderLayout.CENTER);

        mySwingWorker = new MySwingWorker();
        mySwingWorker.execute();
    }

    private class MySwingWorker extends SwingWorker<Boolean, double[]> {

        final LinkedList<Double> fifo = new LinkedList<>();

        public MySwingWorker() {
            fifo.add((double) Simulation.animalCount);
        }

        @Override
        protected Boolean doInBackground() {

            while (!isCancelled()) {

                fifo.add((double) Simulation.animalCount);
                if (fifo.size() > 500) {
                    fifo.removeFirst();
                }

                double[] array = new double[fifo.size()];
                for (int i = 0; i < fifo.size(); i++) {
                    array[i] = fifo.get(i);
                }
                publish(array);

                try {
                    //Thread.sleep(5);
                    TimeUnit.MILLISECONDS.sleep(40);
                } catch (InterruptedException e) {
                    // eat it. caught when interrupt is called
                    System.out.println("MySwingWorker shut down.");
                }
            }

            return true;
        }

        @Override
        protected void process(List<double[]> chunks) {
            double[] mostRecentDataSet = chunks.get(chunks.size() - 1);

            chart.updateXYSeries("series1", null, mostRecentDataSet, null);
            // No need to repaint as XChartPanel handles it
        }
    }
}
