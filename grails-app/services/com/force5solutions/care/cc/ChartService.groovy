package com.force5solutions.care.cc

import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.ChartUtilities
import org.jfree.chart.JFreeChart
import org.jfree.chart.axis.CategoryAxis
import org.jfree.chart.axis.CategoryLabelPositions
import org.jfree.chart.axis.NumberAxis
import org.jfree.chart.axis.ValueAxis
import org.jfree.chart.renderer.category.BarRenderer3D
import org.jfree.data.category.CategoryDataset
import org.jfree.data.category.DefaultCategoryDataset
import org.jfree.data.general.DefaultPieDataset
import org.jfree.data.xy.XYDataset
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import org.jfree.ui.RectangleEdge
import org.jfree.ui.RectangleInsets
import org.jfree.util.Rotation
import org.jfree.chart.plot.*

class ChartService {

    boolean transactional = true

    byte[] drawPieChart(List keys, List values, Integer width, Integer height) {

        DefaultPieDataset pieDataset = new DefaultPieDataset();

        keys.eachWithIndex {String data, Integer index ->
            pieDataset.setValue(data, values[index]);
        }

        PiePlot3D plot = new PiePlot3D(pieDataset);

        Color[] colors;
        colors = new Color[5];
        colors[0] = new Color(93, 169, 246)
        colors[1] = new Color(208, 69, 99)
        colors[2] = new Color(158, 234, 65)
        colors[3] = new Color(135, 112, 242)
        colors[4] = new Color(230, 135, 50)

        PieRenderer renderer = new PieRenderer(colors)
        renderer.setColor(plot, pieDataset);

        plot.setDirection(Rotation.CLOCKWISE)
        plot.setStartAngle(90)
        plot.setDepthFactor(0.09)
        plot.setShadowYOffset(0.1)
        plot.setForegroundAlpha(1.0f)
        plot.setOutlineVisible(false)
        plot.setBackgroundPaint(Color.white)
        plot.setDarkerSides(false)
        plot.setLabelBackgroundPaint(Color.white)
        plot.setLabelLinksVisible(true)
        plot.setLabelGenerator(new CustomLabelGenerator())
        plot.setLabelOutlinePaint(Color.WHITE)
        plot.setLabelShadowPaint(Color.WHITE)
        plot.setCircular(true)
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 10))
        plot.setSectionOutlinesVisible(false)
        plot.setIgnoreZeroValues(true)
        plot.setIgnoreNullValues(true)
        plot.setLabelGap(0.02)
        plot.setLegendItemShape(Plot.DEFAULT_LEGEND_ITEM_BOX)

        JFreeChart chart = new JFreeChart(plot)
        chart.setBorderVisible(false)
        chart.setBackgroundPaint(Color.white)
        chart.setBorderPaint(null)
        chart.legend.setPosition(RectangleEdge.TOP)
        chart.legend.setBorder(0, 0, 0, 0)
        chart.legend.setPadding(0, 20, 0, 20)
        chart.legend.setItemLabelPadding(new RectangleInsets(0, 2, 0, 8))

        BufferedImage bi = chart.createBufferedImage(width, height)
        def bytes = ChartUtilities.encodeAsPNG(bi)

        return bytes
    }

    byte[] drawXYChartByMonth(List data, Integer width, Integer height) {
        XYSeries series = new XYSeries("")
        data.eachWithIndex {Integer value, Integer index ->
            series.add(index, value)
        }
        XYDataset xyDataset = new XYSeriesCollection(series)
        JFreeChart chart = ChartFactory.createXYAreaChart("", "", "", xyDataset, PlotOrientation.VERTICAL, false, false, false);
        final XYPlot plot = chart.getXYPlot()
        plot.setOutlineVisible(false)
        plot.setForegroundAlpha(1.0f)
        plot.setBackgroundPaint(new Color(250, 252, 251))
        plot.setDomainGridlinesVisible(false)
        plot.setDomainGridlinePaint(Color.white)
        plot.setRangeGridlinesVisible(false)
        plot.setRangeGridlinePaint(Color.white)
        plot.setInsets(new RectangleInsets(0, 0, 0, 0))

        final ValueAxis domainAxis = plot.getDomainAxis()
        domainAxis.setAxisLineVisible(false)
        domainAxis.setVisible(false)

        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis()
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits())
        rangeAxis.setAxisLineVisible(false)
        rangeAxis.setVisible(false)
        chart.setBackgroundPaint(new Color(250, 252, 251))
        chart.removeLegend()

        BufferedImage bi = chart.createBufferedImage(width, height)
        def bytes = ChartUtilities.encodeAsPNG(bi)
        return bytes
    }

    byte[] drawStackedBarChartForOutstandingRequests(List<ActionRequestVO> actionRequestVOs, Integer width, Integer height) {
        final CategoryDataset dataset = createDataset(actionRequestVOs)
        int maxCount = ((actionRequestVOs.collect {it.outstandingRequests.toLong()}.max()) as Integer) ?: 0
        final JFreeChart chart = createChart(dataset, maxCount)
        final ChartPanel chartPanel = new ChartPanel(chart)
        chartPanel.setPreferredSize(new java.awt.Dimension(590, 350))

        BufferedImage bi = chart.createBufferedImage(width, height)
        def bytes = ChartUtilities.encodeAsPNG(bi)
        return bytes
    }

    private CategoryDataset createDataset(List<ActionRequestVO> actionRequestVOs) {
        DefaultCategoryDataset result = new DefaultCategoryDataset()
        actionRequestVOs.each {ActionRequestVO requestVO ->
            result.addValue(requestVO.outstandingRequests.toDouble(), "Outstanding", requestVO.requestName)
        }
        return result;
    }

    private JFreeChart createChart(final CategoryDataset dataset, int maxCount) {
        final JFreeChart chart = ChartFactory.createStackedBarChart3D(
                "",  // chart title
                "",                  // domain axis label
                "Count",                     // range axis label
                dataset,                     // data
                PlotOrientation.VERTICAL,    // the plot orientation
                true,                        // legend
                false,                        // tooltips
                false                        // urls
        );

        CategoryPlot plot = (CategoryPlot) chart.getPlot()
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis()
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits())
        rangeAxis.setRangeWithMargins(0, maxCount > 5 ? (((maxCount / 10).toInteger() + 1) * 10) : 10) // rounds up to nearest multiple of 10

        final BarRenderer3D renderer = (BarRenderer3D) plot.getRenderer()
        renderer.setItemMargin(0.0)
        renderer.setSeriesPaint(0, new Color(0xFF, 0xFF, 0x00))
        renderer.setDrawBarOutline(true)
        plot.setRenderer(renderer)
        final CategoryAxis domainAxis = plot.getDomainAxis()
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45)
        chart.removeLegend()
        return chart
    }
}
