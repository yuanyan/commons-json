commons.json is a high performance JSON library for java and it also makes you easy to use JSON in your project.

yuanyan.cao@gmail.com

###Features
* serialize & deserialize
* cache supported


###Goals
* high performance
* cleaner api
* lightwight
* all KISS

###High Performance

![](http://chart.apis.google.com/chart?cht=bhs&chco=FF0000|00FF00|0000FF,FFC6A5|DEF3BD|C6EFF7&chs=300x125&chd=s:FOE,elo&chxt=x,y&chxl=1:|commons.json|Gson|Jackson|0:||100||500||1000|&chd=t:50,40,30|30,35,30&nonsense=something_that_ends_with.png)

###Example
####Java Object to JSON:

	String json = Json.serialize(object);

####JSON to Java Object:

	Object object = Json.deserialize(string);
