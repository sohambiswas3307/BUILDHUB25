@echo off
echo Running BuildHub JavaFX Desktop Application...
java --module-path %USERPROFILE%\.m2\repository\org\openjfx\javafx-controls\21.0.1\javafx-controls-21.0.1.jar;%USERPROFILE%\.m2\repository\org\openjfx\javafx-fxml\21.0.1\javafx-fxml-21.0.1.jar --add-modules javafx.controls,javafx.fxml -cp target\classes;target\dependency\* com.buildhub.javafx.BuildHubFxApp
pause

