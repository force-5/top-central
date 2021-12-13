<%@ page import="com.force5solutions.care.*; org.codehaus.groovy.grails.commons.ApplicationHolder" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <!--<meta name="layout" content="main"/>-->
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>TOP By Force 5 : Profile</title>
</head>
<body>
Servlet Context Path : ${ApplicationHolder.application.parentContext.servletContext.getRealPath('reports')}
<g:jasperReport controller="jasperDemo" action="contractorProfileReport"
        jasper="contractorProfile" format="PDF" name="Contractor Profile" >
    <input type="hidden" name="contractorId" value="1" />
    <input type="hidden" name="SUBREPORT_DIR" value="${ApplicationHolder.application.parentContext.servletContext.getRealPath('reports')}/" />
</g:jasperReport>
<g:jasperReport controller="jasperDemo" action="contractorCertificationReport"
        jasper="contractorCertification" format="PDF" name="Contractor Certification" >
    <input type="hidden" name="id" value="2" />
</g:jasperReport>


<strong>Complete Profile of Contractor:-</strong>
<g:jasperReport controller="jasperDemo" action="completeProfileReport"
        jasper="completeProfile" format="PDF" name="Complete Profile" >
    <input type="hidden" name="id" value="2" />
    <input type="hidden" name="SUBREPORT_DIR" value="${ApplicationHolder.application.parentContext.servletContext.getRealPath('reports')}/" />
</g:jasperReport>

<strong>Contractor By Business Unit Requester :-</strong>
<g:jasperReport controller="jasperDemo" action="contractorByBurReport"
        jasper="contractorByBur" format="PDF" name="Contractor-By-BUR-Report" >
    <input type="hidden" name="id" value="2" />
    <input type="hidden" name="SUBREPORT_DIR" value="${ApplicationHolder.application.parentContext.servletContext.getRealPath('reports')}/" />
</g:jasperReport>

<strong>Contractor By BUR All :-</strong>
<g:jasperReport controller="jasperDemo" action="contractorByBurAllReport"
        jasper="contractorByBur" format="PDF" name="Contractor-By-BUR-Report-All" >
    <input type="hidden" name="SUBREPORT_DIR" value="${ApplicationHolder.application.parentContext.servletContext.getRealPath('reports')}/" />
</g:jasperReport>




<strong>Pie Chart Demo :-</strong>
<g:jasperReport controller="jasperDemo" action="pieChartReport"
        jasper="pieChart" format="PDF" name="Pie-Chart Report" >
</g:jasperReport>


<strong>Pie Chart Certifications Demo :-</strong>
<g:jasperReport controller="jasperDemo" action="pieChartCertificationReport"
        jasper="pieChartCertificationReport" format="PDF" name="Pie-Chart Certifications Report" >
</g:jasperReport>

<strong>Bar Graph Certifications Demo :-</strong>
<g:jasperReport controller="jasperDemo" action="barGraphReport"
        jasper="barGraph" format="PDF" name="Bar-Graph Certification Report" >
</g:jasperReport>

<%
    	def labels = ['First','Second','Third']
    	def colors = ['FF0000','00ff00','0000ff']
    	def values = [35,45,10,50]
    	def values2 = [[35,45,10],[3,987,2]]
    	def values5 = [[0,16.7,23.3,33.3,60,76.7,83.3,86.7,93.3,96.7,100],[30,45,20,50,15,80,60,70,40,55,80],[0,10,16.7,26.7,33.3],[50,10,30,55,60]]
    	def values3 = [97,12,454,12,5,32,78,4,99,89,98,77,7,77]
    	def values4 = [[97,12,454,12,5,32,78,4,99,89,98,77,7,77],[1,6,42,15,78,94,26,45,12,10,21,22,33,33]]
    	def values6 = [[-500,30,700,253],[2,-5,3,6]]
    %>
	<h2>Simple Data Examples</h2>
	<g:lineChart title='Sample Line Chart' titleAttrs="${['440000',30]}" colors="${colors}"
	 	axes="x,y" gridLines="10,10,1,0" type='xy' lineStyles="${[[3,6,3],[6,3,6],[6,6,7]]}" legend="${labels}" axesLabels="${[0:['Jan','Feb','Mar'],1:[0,10,30,50]]}" fill="${'bg,s,efefef'}" dataType='text' data='${values5}' />

	<g:barChart title='Sample Bar Chart' size="${[300,200]}" colors="FF0000|00ff00|0000ff" type="bvs"
		labels="${labels}" zeroLine="${'0.5'}" axes="x,y" axesLabels="${[0:['Jan','Feb','Mar','April','May'],1:[0,10,30,50,25]]}" fill="${'bg,s,efefef'}" dataType='simple' data='${values}' />

    <g:pieChart title='Sample Pie Chart' colors="${colors}"
		labels="${labels}" fill="${'bg,s,efefef'}" dataType='simple' data='${values}' />

	<g:vennDiagram title='Sample Venn Diagram' colors="${colors}"
		labels="${labels}" fill="${'bg,s,efefef'}" dataType='simple' data='${values}' />
	<g:map mapArea="africa" colors="${['ffffff','edf0d4','13390a']}" data="${[4,23,56]}" countries="${['MG','KE','TN']}" />
	<g:qr labels='Hello World!' size='${[200,200]}' />
	<h2>Text Data Examples</h2>
	<g:lineChart title='Sample Line Chart' colors="${colors}"
		axes="x,y" type='lc' shapeRangeFill="${[['c','FF0000',0,1.0,20.0],['a','990066',0,3.0,9.0],['R','220066',0,0.0,0.5]]}" axesLabels="${[0:['Jan','Feb','Mar','Apr','May','Jun']]}" fill="${'bg,s,efefef'}" dataType='text' data='${values3}' />
	<g:lineChart title='Sample SparkLine Chart' colors="${colors}"
		type='ls'  fill="${'bg,s,efefef'}" dataType='text' data='${values3}' />
	<g:barChart title='Sample Bar Chart' size="${[400,200]}" colors="${colors}" type="bvs"
		labels="${labels}" axes="x,y"  axesLabels="${[0:['Jan','Feb','Mar','Apr','May','Jun','Jul']]}" fill="${'bg,s,efefef'}" dataType='text' data='${values3}' />
	<g:pieChart title='Sample Pie Chart' colors="${colors}"
		labels="${labels}" fill="${'bg,s,efefef'}" dataType='text' data='${values3}' />

	<g:vennDiagram title='Sample Venn Diagram' colors="${colors}"
		labels="${labels}" fill="${'bg,s,efefef'}" dataType='text' data='${values3}' />
	<g:scatterPlot title='Sample ScatterPlot' colors="${colors}"
		labels="${labels}" fill="${'bg,s,efefef'}" dataType='text' data='${values4}' />
    <g:scatterPlot title='Negative ScatterPlot' colors="${colors}"
        labels="${labels}" fill="${'bg,s,efefef'}" dataType='text' data='${values6}'
        scale="${[-500,1000,-5,6]}" />
    <g:gmeter title='Sample Google-o-meter' labels='${["Hello"]}' dataType='text' data='${[70]}' />
    <g:radar title='Sample Radar Chart' colors="${colors}"
		axes="x,y" axesLabels="${[0:['Jan','Feb','Mar','Apr','May','Jun']]}" fill="${'bg,s,efefef'}" dataType='text' data='${values3}' />
	<h2>Extended Data Examples</h2>
	<g:lineChart title='Sample Line Chart' colors="${colors}"
		axes="x,y" type='lc' axesLabels="${[0:['Jan','Feb','Mar','Apr','May','Jun']]}" fill="${'bg,s,efefef'}" dataType='extended' data='${values3}' />

	<g:barChart title='Sample Bar Chart' size="${[400,200]}" colors="${colors}" type="bvs"
		labels="${labels}" axes="x,y" axesLabels="${[0:['Jan','Feb','Mar','Apr','May','Jun','Jul']]}" fill="${'bg,s,efefef'}" dataType='extended' data='${values3}' />
	<g:pieChart title='Sample Pie Chart' colors="${colors}"
		labels="${labels}" fill="${'bg,s,efefef'}" dataType='extended' data='${values3}' />

	<g:vennDiagram title='Sample Venn Diagram' colors="${colors}"
		labels="${labels}" fill="${'bg,s,efefef'}" dataType='extended' data='${values3}' />



</body>
</html>
