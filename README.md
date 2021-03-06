# Pitch-Charting-App

This is a Java applet to chart pitches, for use by the Pomona-Pitzer baseball team. Before this project, the pitch charting had been done by hand on paper and therefore the data was difficult to utilize in any kind of statistical modeling. 

Now, the data is available in CSV form and and an image of the chart is created as well. Once the user presses the *submit* button, the chart image and CSV of pitches are saved in the `chart_logs` directory in a subdirectory with the name of the pitcher and the date of submission. 

Here is a look at the interface:

![pitch charting applet](https://github.com/NolanJMcCafferty/Pitch-Charting-App/blob/master/pitchChart.png)

To use the pitch charting applet, clone this repository and navigate to the src directory. Then, using the command line, run ``javac PitchChart.java`` and ``java PitchChart``.
