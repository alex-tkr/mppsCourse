package org.example.services.Diagrams;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PieChart extends ApplicationFrame{
    public PieChart(String title) {
        super(title);
    }

    public PieChart(String title, Map<String,Integer> list) {
        super( title );
        setContentPane(createDemoPanel(title,list));
    }
    public JPanel createDemoPanel(String title, Map<String,Integer> list) {
        JFreeChart chart = createChart(createDataset(list),title);
        return new ChartPanel( chart );
    }
    private static JFreeChart createChart(PieDataset dataset,String titles ) {
        JFreeChart chart = ChartFactory.createPieChart(
                titles,   // chart title
                dataset,          // data
                true,             // include legend
                true,
                false);
        return chart;
    }
    private  PieDataset createDataset(Map<String,Integer> list) {
        DefaultPieDataset dataset = new DefaultPieDataset( );
        for (Map.Entry<String, Integer> entry : list.entrySet()) {
            dataset.setValue( entry.getKey()+" "+entry.getValue() , entry.getValue());
        }
        return dataset;
    }

    public void createPngFile( Map<String,Integer> list,String nameFile) throws IOException {
        JFreeChart chart = ChartFactory.createPieChart(
                getTitle(),   // chart title
                createDataset(list),          // data
                true,             // include legend
                true,
                false);

        int width = 640;   /* Width of the image */
        int height = 640;  /* Height of the image */
        File pieChart = new File( nameFile+".jpg" );
        ChartUtilities.saveChartAsJPEG( pieChart , chart , width , height );
    }

    public static void main( String[ ] args ) throws IOException {
        Map<String ,Integer> list=new HashMap<>();
        list.put("fff",56);
        list.put("ff4566f",56);
        new PieChart("diggfgg").createPngFile(
             list,"testfile"
        );
    }
}
