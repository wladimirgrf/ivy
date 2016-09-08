<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" 						%>

<div class="box-1024 margin-auto">

	<div class="text padding-bottom-15">
		<div class="title">Project Ivi</div>
		
		<article class="padding-left-15">
			<p>
				Ivi provides a powerful tool for recognition of vulnerabilities in web sites.
				The tool analyzes the host seeking exploits. 
				For now the only pattern recognition are vulnerable to SQL Injection. 
				Soon new faults may be recognized.
			</p>
			
			<br>
			<br>
			
			<p><strong>Analyzed Properties</strong></p>
			
			<br>
			
			<div class="photo size420 right">
				<img alt="example" src="/public/img/banner-example1.jpg">
				<div>numbers marked in gold represent the <strong>analyzed properties</strong>.</div>
			</div>
			
			<p class="padding-bottom-7"><strong>1</strong> - Host</p>
			<p class="padding-bottom-7"><strong>2</strong> - Sampling link (of all urls found, this was the most promising for the test)</p>
			<p class="padding-bottom-7"><strong>3</strong> - Tag for search</p>
			<p class="padding-bottom-7"><strong>4</strong> - Last time this domain was analyzed</p>
			<p class="padding-bottom-7"><strong>5</strong> - Country where the domain remains hosted</p>
			
			<br>
			
			<p> 
				When the scan is completed the system marks the link tested with a color by identifying if there is vulnerability. 
				If the domain to be safe the system is green opposite case the displayed color will be red.
			</p>
		
			<br>
			
			<div class="photo size420 center">
				<img alt="example" src="/public/img/banner-example2.jpg">
				<div>this is an example of a vulnerable web site.</div>
			</div>
		</article>
	</div>
</div>
