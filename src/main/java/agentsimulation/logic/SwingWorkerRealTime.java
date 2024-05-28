package agentsimulation.logic;

import java.security.Signature;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.SwingWorker;

import agentsimulation.Simulation;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

/**
 * Creates a real-time chart using SwingWorker
 */
public class SwingWorkerRealTime {

    MySwingWorker mySwingWorker;
    SwingWrapper<XYChart> sw;
    XYChart chart;

    public SwingWorkerRealTime() {
        go();
    }

    private void go() {

        // Create Chart
        chart =
                QuickChart.getChart(
                        "Real-time population",
                        "Time",
                        "Population",
                        "randomWalk",
                        new double[]{0},
                        new double[]{0});
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setXAxisTicksVisible(false);

        // Show it
        sw = new SwingWrapper<XYChart>(chart);
        sw.displayChart();

        mySwingWorker = new MySwingWorker();
        mySwingWorker.execute();
    }

    private class MySwingWorker extends SwingWorker<Boolean, double[]> {

        final LinkedList<Double> fifo = new LinkedList<Double>();

        public MySwingWorker() {

            fifo.add((double) Simulation.animalCount);
        }

        @Override
        protected Boolean doInBackground() throws Exception {

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

            chart.updateXYSeries("randomWalk", null, mostRecentDataSet, null);
            sw.repaintChart();

            long start = System.currentTimeMillis();
            long duration = System.currentTimeMillis() - start;
            try {
                //Thread.sleep(40 - duration); // 40 ms ==> 25fps
                //Thread.sleep(400 - duration); // 40 ms ==> 2.5fps
                TimeUnit.MILLISECONDS.sleep(40 - duration);
            } catch (InterruptedException e) {
                System.out.println("InterruptedException occurred.");
            }
        }
    }
}
