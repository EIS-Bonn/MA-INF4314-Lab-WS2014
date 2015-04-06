* [Test Overview](#test-overview)
    * [Input Criteria](#input-criteria)
    * [Recommendation Methods](#recommendation-methods)
* [Test Results](#test-results)   
    * [Gold Standard](#gold-standard)
    * [Random Selection](#random-selection)
    * [Excel Recommendation](#gold-standard)
    * [ChartAdvisor Results](#chartadvisor-results)
    * [Analysis](#analysis) 

## Test Overview
In order to test the effectiveness of the chart recommendation algorithm under the hood of ChartAdvisor, different input selections were performed resulting in a variety of recommendation lists. Moreover, results were compared with recommendations from other tools such as [Microsoft Excel 2013 Chart Recommendation Tool](https://support.office.com/en-gb/article/Create-a-chart-387f3d53-c182-426b-89af-161322ef593f) and also considering the results of a random recommendation. Only charts mentioned in the [User Manual](https://github.com/CristoLeiva/Algorithm_forCharts_Recommendation/wiki/User-Manual) section of this documentations are considered in testing results. In the following sections more details are given:

### Input Criteria
To consider different test scenarios in ChartAdvisor, 5 (five) diverse input data (data-sets, properties datatype and levels of measurement) were used in the test.  

1. Input data: fertilityRates.rdf (download here), Selected properties: ID, Population, Region, FertilityRate, LifeExpectancy. 

   | ID | Population | Region | FertilityRate | LifeExpectancy |
   |--------|--------|--------| --------| --------|
   |DEU| 81902307 | Europe | 1.36 | 79.84 |
   |CAN| 33739900 | North America | 1.67 | 80.66 |
   |DNK| 5523095 | Europe | 1.84 | 78.6 |
   |...| ... | ... | ... | ... |

2. Input data: The second input data tested is a variation of the properties selected from previews file fertilityRates.rdf, in this case only the properties ID and Population were considered. 

3. Input data: geodata.rdf (download here), Selected properties: NameShort, PopulationYear, PopulationTotal. 

   | NameShort | PopulationYear | PopulationTotal | 
   |--------|--------|--------| 
   |Botswana| 2010 | 2007.0 |
   |Croatia| 2010 | 4403.0 | 
   |Ethiopia| 2010 | 82950.0 | 
   |...| ... | ... | 

4. Input data: Newspaper-Articles-Analysis.ttl (download here), Selected properties: label, nrPages. 

   | label | nrPages | 
   |--------|--------|
   |NPA1| 5 | 
   |NPA6| 12 | 
   |...| ... | 

5. Input data: Water-Quality-Analysis.ttl (download here), Selected properties: valuePre, month. 

   | valuePre | month | 
   |--------|--------|
   |0.117| 1 | 
   |0.191| 2 | 
   |-0.999| 7 | 
   |...| ... | 

### Recommendation Methods
In this testing process 3 (three) additional chart recommendation methods were used along with ChartAdvisor output. Description of methods: 

1. **Gold Standard**: These chart recommendations represent the baseline of our test analysis and is considered the most accured result, every other recommendation was compared with the Gold Standard. To create this recommendation each team member (ChartAdvisor developers) selected (base on personal criteria) a proper list of charts for each input data. The average result of all members selections is being taken as the Gold Standard. 

2. **Random Selection**: Under this method all charts all selected randomly, this is a very general standard procedure to test the effectiveness of our generated results.   

3. **Excel Recommendation**: Using [Microsoft Excel 2013 Chart Recommendation Tool](https://support.office.com/en-gb/article/Create-a-chart-387f3d53-c182-426b-89af-161322ef593f) all input data was tested. This tool provides many types of charts that are not included in ChartAdvisor algorithm, for this test only available charts were considered. 

![add](http://www.k2e.com/images/stories/articles/newsletter/2013_08/TIP_2_Figure_2.jpg) 

## Test Results
This section is about the resulting chart recommendations using previews methods and a comparative analysis of theses results with ChartAdvisor output. Here are the charts considered for this analysis ([charts](https://github.com/CristoLeiva/Algorithm_forCharts_Recommendation/wiki/User-Manual#charts)) and [here](https://github.com/CristoLeiva/Algorithm_forCharts_Recommendation/wiki/Testing-Results#input-criteria), the input data. 

### Gold Standard

![add](https://github.com/CristoLeiva/Algorithm_forCharts_Recommendation/blob/master/wiki_resources/test/goldresult.png)

### Random Selection

![add](https://github.com/CristoLeiva/Algorithm_forCharts_Recommendation/blob/master/wiki_resources/test/randomresult.png)

### Excel Recommendation

![add](https://github.com/CristoLeiva/Algorithm_forCharts_Recommendation/blob/master/wiki_resources/test/excelresult.png)

### ChartAdvisor Results

![add](https://github.com/CristoLeiva/Algorithm_forCharts_Recommendation/blob/master/wiki_resources/test/chartadvresult.png)

### Analysis
Given previously presented results, we concluded the following information:  

![add](https://github.com/CristoLeiva/Algorithm_forCharts_Recommendation/blob/master/wiki_resources/test/analysis.png)

As we can see, the accuracy of ChartAdvisor is very similar to MS Excel chart recommender, both with a 75% of accuracy. Meanwhile, random selection of charts is only 25% accurate meaning that the output of our tool is actually working and is throwing real recommendations. The following chart shows the performance of each tool for all 5 given data inputs:

![add](https://github.com/CristoLeiva/Algorithm_forCharts_Recommendation/blob/master/wiki_resources/test/analysischart.png) 
