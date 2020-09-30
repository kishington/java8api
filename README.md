# Formula 1 ranking app using Java 8 API
There are 2 log files start.log and end.log that contain race start and end data of the best lap for each racer of Formula 1 - Monaco 2018 Racing. Race start and 
end times are fictional, but the best lap times are real. Data contains only the first 20 minutes that refers to the first stage of the qualification (Q1).
The third file abbreviations.txt contains explanation of the abbreviations used in files start.log and end.log.
#### Q1 Rules
First 20 minutes all cars are racing together on the track trying to set the fastest lap. The slowest seven cars are eliminated, earning the bottom grid 
positions. Drivers are allowed to complete as many laps as they want during that short time period. Top 15 cars are going to the Q2 stage. If you are curious, 
you can learn more about the rules [here](https://www.thoughtco.com/developing-saga-of-formula1-qualifying-1347189).
#### Results
The app reads data from the log files, orders racers by lap time and prints a report showing the top 15 racers and the rest underline.
Example output:
<pre>
1. Daniel Ricciardo      | RED BULL RACING TAG HEUER     | 1:12.013
2. Sebastian Vettel      | FERRARI                       | 1:12.415
3. ...
------------------------------------------------------------------------
16. Brendon Hartley      | SCUDERIA TORO ROSSO HONDA     | 1:13.179
17. Marcus Ericsson      | SAUBER FERRARI                | 1:13.265
</pre>
