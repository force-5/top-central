package com.force5solutions.care.cc

import org.jfree.chart.labels.PieSectionLabelGenerator
import org.jfree.chart.plot.PiePlot
import org.jfree.data.general.DefaultPieDataset
import org.jfree.data.general.PieDataset
import java.awt.Color
import java.text.AttributedString

class ChartController {
    def chartService
    def dashboardService

    def index = { }

    def pie = {
        def bytes = chartService.drawPieChart(params.list('labels'), params.list('counts')*.toInteger(), params.width.toInteger(), params.height.toInteger())
        response.setContentLength(bytes.size())
        OutputStream out = response.outputStream
        out.write(bytes)
        out.close()
    }

    def area = {
        def bytes = chartService.drawXYChartByMonth(params.list('data')*.toInteger(), params.width.toInteger(), params.height.toInteger())
        response.setContentLength(bytes.size())
        OutputStream out = response.outputStream
        out.write(bytes)
        out.close()

    }

    def stackedBarChart = {
        String workerType = params.workerType.toString()
        List<ActionRequestVO> actionRequestVOs = dashboardService.getActionRequests(workerType ?: null, params.viewType.toString())
        actionRequestVOs.each { ActionRequestVO requestVO ->
            requestVO.requestName = requestVO.requestName.minus("ACCESS ")
            requestVO.requestName = requestVO.requestName.minus("CONTRACTOR ")
        }
        def bytes = chartService.drawStackedBarChartForOutstandingRequests(actionRequestVOs, params.width.toInteger(), params.height.toInteger())
        response.setContentLength(bytes.size())
        OutputStream out = response.outputStream
        out.write(bytes)
        out.close()
    }
}

class PieRenderer {
    private Color[] color;

    public PieRenderer(Color[] color) {
        this.color = color
    }

    public void setColor(PiePlot plot, DefaultPieDataset dataset) {
        List<Comparable> keys = dataset.getKeys()
        int aInt

        for (int i = 0; i < keys.size(); i++) {
            aInt = i % this.color.length
            plot.setSectionPaint(keys.get(i), this.color[aInt])
        }
    }
}

static class CustomLabelGenerator implements PieSectionLabelGenerator {

    public String generateSectionLabel(final PieDataset dataset, final Comparable key) {
        String temp = null
        if (dataset != null) {
            temp = dataset.getValue(key).toString() + "%"
        }
        return temp
    }

    public AttributedString generateAttributedSectionLabel(final PieDataset dataset, final Comparable key) {}
}