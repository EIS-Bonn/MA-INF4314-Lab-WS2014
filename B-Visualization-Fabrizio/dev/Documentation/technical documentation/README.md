* [The Algorithm](#1-the-algorithm)
* [Structure of the System](#2-structure-of-the-system)
    * [Description of Classes](#description-of-classes)
* [Data Structure](#3-data-structure)

## 1. The Algorithm
The algorithm is designed to suggest the best matching chart for the user input, the input is a set of selected properties from an RDF document. The algorithm will analyze the attributes and try to match as many attributes as possible to the set of charts that are configured (see, [Data Structure](#3-data-structure)).
The main steps of the algorithm are described below. In our case, the user is trying to visualize the population of countries ordered by years.

1. INPUT: List of properties

   | Country | Pop_count | Year |
   |--------|--------|--------|
   |Germany| 80000000| 2014 |
   |Italy| 60000000 | 2014 |
   |Germany| 56000000 | 1900 |
   |Italy| 32000000 | 1900 |

2. Categorize properties

   | Property | Data Type | Scale of Measurement |
   |--------|--------|--------|
   |Country| string| Categorical |
   |Pop_count| Integer | Quantitative |
   |Year| Integer | Ordinal |

3. Generate and Validate Allocations

   Year --> Pop_count .............................**Not Valid**, Not right-unique.


   Year --> Country ..................................**Not Valid**, Not right-unique.


   Year, country --> Pop_count ................**Valid**, right-unique and left-total.

4. Match Valid Allocations with Chats

   **Pie Chart:**

   Categorical ---> Quantitative (**Not a Match**)

   **Column Chart:**

   Categorical, Ordinal ----> Quantitative (**Match**)

5. Order Charts

   The matching charts are ordered based on the number of covered attributes.

6. Output the Result.

   The user can choose one of three options to generate the output. RDF format, XML Format, and a normal text file.

## 2. Structure of the System
The simplified class diagram below shows the structure of the system. It follows the Model, View, Controller design pattern.

![Chart Adviser Class Diagram ](http://i45.photobucket.com/albums/f91/renwar/classDiagram_zpsvluxd0zg.png)

The Controller, AllocationGenerator, and OutputManager are responsible for updating the state of the model (Property, Allocation, Chart, DataSets). They Controller is also responsible for connection with its associated MainView, to change the presentation (eg, List properties in the RDF file, scroll down).
 

### Description of Classes
**Property**

An object of type Property, stores the information about the property name (short and long names), the property type (Integer, float, string,...) and the property value.

**Allocation**

An allocation is something like (country, year ---> population), so every allocation has a list of properties representing its left side, and another list representing its right side.

**Chart**

All information about available charts is stored in an RDF document. Every chart has a list of patterns. Every pattern consists of an allocation, but the allocations for charts are a different in a way that they do not reflect the names of the properties, but rather the levels of measurement.
An example for a chart pattern is (Categorical, Ordinal ---> Quantitative).

**DataSets**

The Dataset Class handles all related to MODEL part of the Algorithm and is able to read the files, create models of the files, write the models in other formats and do SPARQL queries in the data contained in the file.

The data sources and management of the data is supported in the FRAMEWORK APACHE JENA. (https://jena.apache.org/)
which is a free and open source Java framework for building semantic web and Linked Data applications. The framework is composed of different APIs interacting together to process RDF data. 

**FUNCTIONS:**

**create_model(String filename):**

Returns a model based in the file that is located in the parameter Filename.
 
**load_model_file(Model model,String fileName,String format)**

Loads in a file the model in certain format (TURLTE,RDF/XML,RDF/JSON...).

**get_properties(Model model, int type_property)**

Returns a list of arrays of strings with all the properties of the file.
 
**sparql_query_property(Model model,String[] propertyArray)**

Returns a list of arrays of strings with the values of the properties selected. This values are returned by a SPARQL query.

**Combinations**

Combinations is a helper class, it is responsible for generating combinations for any given list of objects.

**AllocationGenerator**

The AllocationGenerator is responsible for generation of all different combinations (allocations) for the selected properties. It is also responsible for the validation of the generated allocations.

**FUNCTIONS:**

**generateAllocations**

Parameters: 

Property[] properties, is a list of objects of type Property.

Return Value: 

List<Allocation>, this list contains all possible valid allocations for the input properties.

Process: 

The process splits the input array into two arrays (left and right), the size of the the left array increases from one to properties size -1. The different combinations are calculated for the arrays, then a list of different allocations is generated using these combinations.
Every allocation is checked for right-unique and left-total properties to make sure it is a valid allocation.
The valid allocations are returned.

**isLeftTotal**

Parameters:

Allocation alloc, the allocation to be validated.

List<String[]> values, the different values for the attributes the allocation contains.

Return Value: 

True if it is left-total, False otherwise.

Process: 

The process checks if all values of the left side of the allocation, there is a non empty value in the right side.

**isRightUnique**

Parameters: 

Allocation alloc, the allocation to be validated.

List<String[]> values, the different values for the attributes the allocation contains.

Return Value: 

True if it is right-unique, False otherwise.

Process: 

The process checks if two lists of values are equal in their left side, they should be equal also in their right side of the allocation.

**findCharts**

Parameters: 

Allocation alloc, the allocation to be search for compliant chart.

Return Value: 

List<String[]> that contains the charts names ordered by their accuracy.

Process:

The process starts by finding the level of measurement for the properties contained in the allocation. This is done using the help of a dictionary file (3.Data Structure). Having the levels of measurement ready, a search along all available charts to match the current levels of measurement allocation. Available charts are stored in an RDF file that contains their visualization patterns (3. Data Structure)

## 3. Data Structure

**chart.rdf**

This file contains all the information about the visualization abilities of each supported chart. The file is in RDF format. For every chart, it lists all the allocations that the chart is capable of visualizing. These allocations are written using their level of measurement.
Below is a snapshot showing the representation of the Column Chart.

![Column Chart in chart.rdf](http://i45.photobucket.com/albums/f91/renwar/columnChart_zpsvnh2xnba.jpg)

The current supported charts are Column chart, Bar chart, Pie chart, Donut chart, Line chart, Area chart, Scatter chart, Bubble chart, and Geo chart.

**dictionary.rdf**

The dictionary is the way to determine the level of measurement for the selected properties. The user can add to it when desired. The file stores the attribute name along with its type and level of measurement.
Below is a snapshot showing the representation of the property year when treated as an integer.

![Property Year of Type Integer in dictionary.rdf](http://i45.photobucket.com/albums/f91/renwar/Screenshot%20from%202015-04-02%20023502_zpsunxsf35p.jpg)


