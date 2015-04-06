* [System Overview](#system-overview)
* [Tutorial](#tutorial)
    * [How to get ChartAdvisor?](#how-to-get-chartadvisor)
    * [How to generate recommendations?](#how-to-generate-recommendations)
    * [How to add values to dictionary?](#how-to-add-values-to-dictionary)
* [Charts](#charts)
* [System Requirements](#system-requirements)

## System Overview
Chart Advisor is a standalone software that process an Input RDF Dataset and produce as result a ranked list of recommended visualization tools (charts) such as Pie, Column, Line charts (and [others](#charts)). The software's core algorithm is intended to be used by Linked Data Projects ([LinDa](http://linda-project.eu/)) and similar solutions.

## Tutorial
In order to start this tutorial first you must check the [System Requirements](#system-requirements) section and verify if your computer has all required programs to run ChartAdvisor. Once this process is done, follow the next sections to learn where to get ChartAdvisor and how to generate charts recommendations with this software. 

### How to get ChartAdvisor?
To get CharAdvisor go to the following link (here) on the GitHub account of the project and download `chartadvisor.zip` compressed file. Therefore, extract the executable file and resources folder in your preferred directory. 

![add](https://github.com/CristoLeiva/Algorithm_forCharts_Recommendation/blob/master/wiki_resources/user_manual/downloadprogram.gif)

### How to generate recommendations?
**Step 1:** Lets start running ChartAdvisor.jar file (JAVA Executable), remember that the folder `/resource` must be in the same directory as the `.jar` executable file. After running the file, ChartAdvisor's graphic interface will show up. 

![add](https://github.com/CristoLeiva/Algorithm_forCharts_Recommendation/blob/master/wiki_resources/user_manual/runprogram.gif)

**Step 2:** Proceed to click in button "Open" from the main screen or select the menu item "File/Open" in the MenuBar. A file selector window will appear, proceed to select the input .rdf file (Dataset). Remember that this software ONLY ACCEPTS .rdf file formats as input, any other format will be rejected and an error will be shown. Moreover, continue to open the file. Therefore, all properties included in selected input will appear o the list of checkbox properties (main screen). 

![add](https://github.com/CristoLeiva/Algorithm_forCharts_Recommendation/blob/master/wiki_resources/user_manual/openfile.gif)  

**Step 3:** Select the properties you want to consider for processing and generated the recommendation list of charts. NOTE: To ensure the correct functionality of the software and its recommendation algorithm, select no more than 4 properties and no less that 2. In case that the selection of properties is not satisfactory for a correct and accured list generation, a warning will be shown on screen.  

![add](https://github.com/CristoLeiva/Algorithm_forCharts_Recommendation/blob/master/wiki_resources/user_manual/selectproperties.gif) 

**Step 4:** After selecting the properties to process, proceed to select the output file format (rdf / txt / xml). Default format is `.rdf` is the recommended. Then, click in button "Generate" in the main screen or through the menu item File/Generate. Following to this process, a directory selection window will be opened where the user must indicate the name and location of the output file. 

![add](https://github.com/CristoLeiva/Algorithm_forCharts_Recommendation/blob/master/wiki_resources/user_manual/generate.gif) 

**Results** Check the ranked list of recommended charts to your selection of properties. This output will precise the chart name, rank position and accuracy of the recommendation. A maximum of 4 (four) charts will be recommended with this tool with a minimum of 1 (one) recommendation with valid input properties.   

### How to add values to dictionary?

The Dictionary is a very important element of CHartAdvisor, it is responsible of enhancing the quality of recommendations based on the number of property elements that it has. Refer to the [technical documentation](https://github.com/CristoLeiva/Algorithm_forCharts_Recommendation/wiki/Technical-Documentation) section is more information is needed. 

In order to add a new value to the Dictionary, proceed to click the menu item /Settings/Dictionary. A window will appear with a list of values on the right and an input section on the left. Introduce the values that are going to represent this new element and then press "add" button. Is not possible to add duplicate elements. 

![add](https://github.com/CristoLeiva/Algorithm_forCharts_Recommendation/blob/master/wiki_resources/user_manual/dictionary.gif) 
 
## Charts
This software generates Charts recommendations from different properties of a Dataset. But, what charts are actually considered by the algorithm to perform this selection.  The answer is Google charts (check [here](https://developers.google.com/chart/interactive/docs/gallery)), must popular 9 charts are considered in the algorithm as follow: 
* Column chart
* Bar chart
* Pie chart
* Donut chart
* Line chart
* Area chart
* Scatter chart
* Bubble chart
* Geo chart

More charts can be added to be considered by the algorithm, to do this please refer to the [technical documentation](https://github.com/CristoLeiva/Algorithm_forCharts_Recommendation/wiki/Technical-Documentation) section.

## System Requirements

There is only 1 (one) system requirement in order to run ChartAdvisor independent of the Operative System, the following software most be installed: 

* [Java SE 7 or newer version](https://java.com/en/download/). 

For **Linux OS**, is important to remember to give administrator permissions to the JAR executable file as follow:

1. Go to Terminal.
2. Go to ChartAdvisor directory. 
3. run command: sudo `chmod +x chartadvisor.jar`   
4. done!