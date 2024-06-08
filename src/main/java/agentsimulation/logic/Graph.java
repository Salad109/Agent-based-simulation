package agentsimulation.logic;

import agentsimulation.Simulation;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Real-time chart displaying the combined population
 */
public class Graph extends JPanel {

    MySwingWorker mySwingWorker;
    XYChart chart;
    XChartPanel<XYChart> chartPanel;

    public Graph() {
        initChart();
    }

    private void initChart() {
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

        // Create and add chart panel
        chartPanel = new XChartPanel<>(chart);
        this.setLayout(new BorderLayout());
        this.add(chartPanel, BorderLayout.CENTER);

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
                    TimeUnit.MILLISECONDS.sleep(40);
                } catch (InterruptedException e) {
                    System.out.println("MySwingWorker shut down.");
                }
            }
            return true;
        }

        @Override
        protected void process(List<double[]> chunks) {
            double[] mostRecentDataSet = chunks.getLast();
            chart.updateXYSeries("series1", null, mostRecentDataSet, null);
            chartPanel.repaint();
        }
    }
}
